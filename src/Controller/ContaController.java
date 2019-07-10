package Controller;

import Funcoes.Dinheiro;
import ConexaoDB.Conexao;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.JOptionPane;
import tx.utilitarios.TXPasswordCheck;

public class ContaController {

    public final static String TRANSF_SAIDA = "transf-saida";

    public final static String TRANSF_ENTRADA = "transf-entrada";

    public final static String DEPOSITO = "deposito";

    public final static String SAQUE = "saque";

    public final static String TARIFA_COBRANCA_ANUAL = "tarifa";

    public final static String TABLE_TRANSACOES = "transacoes";

    public static boolean numeroSegurancaValido;

    public static boolean isNumeroSegurancaValido() {
        return numeroSegurancaValido;
    }

    public static void setNumeroSegurancaValido(boolean numeroSegurancaValido) {
        ContaController.numeroSegurancaValido = numeroSegurancaValido;
    }

    /**
     * <p style='font:16px arial;padding:0px;margin:0px;'>Faz o depósito.</p>
     *
     * @param numConta
     * @param valor
     * @param Data
     * @param contaTpp
     * @return
     * @throws SQLException
     */
    public int fazerDeposito(int numConta, BigDecimal valor, String Data, String contaTpp) throws SQLException {
        try {
            String sql = "INSERT INTO " + TABLE_TRANSACOES + " (conta_cliente_num_conta,"
                    + " conta_valor, conta_data,conta_tipo,conta_tpp) VALUES (?,?,?,'deposito',?);";
            PreparedStatement stm = Conexao.abrir().prepareStatement(sql);
            stm.setInt(1, numConta);
            stm.setBigDecimal(2, valor);
            stm.setString(3, Data);
            stm.setString(4, contaTpp);
            return stm.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     * <p style='font:16px arial;padding:0px;margin:0px;'>Faz
     * transferências.</p>
     *
     * @param numConta o número da conta para quem está fazendo a transferência
     * @param Valor O valor para tranferir
     * @param Data A data
     * @param Tipo Corrente ou poupança.
     * @param contaTpp Se é entrada, saída
     * @param numeroConta o número da conta de quem está fazendo a transferência
     * @param descricao
     * @return
     * @throws SQLException
     */
    public int efetuarTransferencia(int numConta, BigDecimal Valor, String Data, 
            String Tipo, String contaTpp, int numeroConta, String descricao) throws SQLException {
        try {
            String sql = "INSERT INTO " + TABLE_TRANSACOES + " (conta_cliente_num_conta,"
                    + " conta_valor, conta_data,conta_tipo,conta_tpp,"
                    + "conta_cliente_transferidor,conta_descricao) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement ps = Conexao.abrir().prepareStatement(sql);
            ps.setInt(1, numConta);
            ps.setBigDecimal(2, Valor);
            ps.setString(3, Data);
            ps.setString(4, Tipo);
            ps.setString(5, Tipo);
            ps.setInt(6, numeroConta);
            ps.setString(7, descricao);
            return ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     * Retorna o restante entre o deposito e o saque
     *
     * @param Conta <h3>ContaDao é o número da conta do cliente.</h3>
     * @return
     */
    public static BigDecimal consultarSaldo(int Conta) {

        BigDecimal deposito = somarValorPorTipoEConta(DEPOSITO, Conta);
        BigDecimal tranferenciaEntrada = somarValorPorTipoEConta(TRANSF_ENTRADA, Conta);
        BigDecimal saque = somarValorPorTipoEConta(SAQUE, Conta);
        BigDecimal tranferenciaSaida = somarValorPorTipoEConta(TRANSF_SAIDA, Conta);
        BigDecimal tarifas = somarValorPorTipoEConta(TARIFA_COBRANCA_ANUAL, Conta);
        BigDecimal resultado = deposito.add(tranferenciaEntrada).subtract(saque.add(tranferenciaSaida));
        return resultado;
        //return resultado;
    }

    /**
     * <h2>Soma o valor da coluna conta_valor da tabela ContaDao pelos filtros
     * TIPO (sacar, deposito e transferencia) e pelo código de um cliente
     * específico
     * </h2>
     *
     * @param tipo
     * @param conta
     * @return O valor da soma dos saques, depósitos e transferências.
     */
    public static BigDecimal somarValorPorTipoEConta(String tipo, int conta) {
        try {

            PreparedStatement stm = Conexao.abrir().prepareStatement(""
                    + "SELECT SUM(conta_valor) AS total FROM " + TABLE_TRANSACOES
                    + " WHERE conta_tipo = ? AND conta_cliente_num_conta = ?;");

            stm.setString(1, tipo);

            stm.setInt(2, conta);

            ResultSet result = stm.executeQuery();

            if (result.next()) {
                if (result.getBigDecimal("total").equals(BigDecimal.ZERO)) {
                    return BigDecimal.ZERO;
                }
                return result.getBigDecimal("total");
            }

        } catch (Exception e) {

        }
        return BigDecimal.ZERO;
    }

    /**
     *
     * @param transferenciaId o Id da tabela conta
     * @return
     */
    public static ResultSet getDadosTransferencia(int transferenciaId) {
        try {
            PreparedStatement stm = Conexao.abrir().prepareStatement("SELECT cdd_agencia, cli_numero_conta, "
                    + "cli_nome, conta_valor, conta_data, conta_descricao FROM " + TABLE_TRANSACOES + "  t1 JOIN clientes AS"
                    + " t2 ON t1.conta_cliente_transferidor = t2.cli_numero_conta\n"
                    + "JOIN cidades AS t3 ON t2.cli_agencia = t3.cdd_id\n"
                    + " WHERE t1.conta_id = ? AND t1.conta_tipo = 'transf-entrada';");

            stm.setInt(1, transferenciaId);

            ResultSet result = stm.executeQuery();

            return result;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     *
     * @param conta O número da conta.
     * @return int
     */
    public static int getNumeroDeSeguranca(int conta) {
        try {
            PreparedStatement stm = Conexao.abrir().prepareStatement(""
                    + "SELECT has_ordem AS t FROM tbl_hash WHERE "
                    + "has_usuario_id = ? ORDER BY RANDOM() LIMIT 1;");

            stm.setInt(1, conta);

            ResultSet result = stm.executeQuery();

            if (result.next()) {
                return Integer.parseInt(result.getString("t"));
            }

        } catch (Exception e) {

        }
        return -1;
    }

    /**
     *
     * @param ordemCodigo
     * @param conta
     * @param codigoSeguranca
     * @return
     */
    public static boolean verificarCodigoDeSeguranca(int ordemCodigo, int conta, String codigoSeguranca) {
        try {
            PreparedStatement stm = Conexao.abrir().prepareStatement("SELECT * From tbl_hash "
                    + "as t1 WHERE t1.has_ordem = ? And t1.has_usuario_id=?; ");

            stm.setInt(1, ordemCodigo);

            stm.setInt(2, conta);

            ResultSet result = stm.executeQuery();

            Conexao.fecharConexao();

            if (result.next()) {
                return TXPasswordCheck.verificarPassword(codigoSeguranca, result.getString(2));
            }

        } catch (Exception e) {

        }
        return false;
    }

    /**
     * Retorna o saldo entre o deposito e o saque
     *
     * @param Conta
     * @return
     */
    public BigDecimal calcularSaldo(int Conta) {
        try {
            return somarValorPorTipoEConta(DEPOSITO, Conta);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção Sbtr", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

  /**
   * <h2>Formata o número</h2>
   * @param numeroConta
   * @return 
   */
    public static String formatarNumero(int numeroConta) {
        BigDecimal resultado = consultarSaldo(numeroConta);
        return currencyFormat(resultado);
    }

    protected static String getLocalizedBigDecimalValue(BigDecimal input) {
        final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        numberFormat.setGroupingUsed(true);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setMinimumFractionDigits(2);
        return numberFormat.format(input);
    }

    /**
     *
     * @param valorMonetario
     * @return
     */
    public static String formatarCurreny(String valorMonetario) {
        BigDecimal resultado = new BigDecimal(valorMonetario);
        return Dinheiro.getNumberFormatedCurreny(resultado.toString());
    }

    /**
     * <p style="font:16px arial">Bom</p>
     *
     * @param n
     * @return
     */
    public static String currencyFormat(BigDecimal n) {
        return NumberFormat.getCurrencyInstance().format(n);
    }
    
    /**
     *
     * @param conta A conta onde será filtrada.
     * @param row A coluna que será retornada.
     * @return String - Dados da tabela clientes;
     */
    public static String getDadosConta(int conta, String row) {

        PreparedStatement stm;

        try {
            stm = Conexao.abrir().prepareStatement("SELECT * FROM clientes WHERE cli_numero_conta = ? LIMIT 1;");

            stm.setInt(1, conta);

            ResultSet result = stm.executeQuery();

            if (result.next()) {
                return result.getString(row);
            }
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * <h2>Serve para usar na Tabela de Extrato</h2>
     *
     * @param numConta
     * @return
     */
    public ResultSet lerTransacoes(String numConta) {

        try {

            Statement stm = Conexao.abrir().createStatement();

            ResultSet rs = stm.executeQuery("SELECT * FROM  " + TABLE_TRANSACOES + "  WHERE conta_cliente_num_conta = '"
                    + numConta + "' ORDER BY conta_id ASC;");

            return rs;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * <p style='font:16px arial;padding:0px;margin:0px;'>Busca o nome do
     * cliente pelo número da conta</p>
     *
     * @param agencia
     * @param conta
     * @return
     */
    public String getNomeCliente(int agencia, String conta) {
        try {
            try {

                PreparedStatement ps;

                ps = Conexao.abrir().prepareStatement("SELECT * FROM clientes AS t1 JOIN"
                        + " cidades as t2 ON t1.cli_agencia = t2.cdd_id WHERE "
                        + "t2.cdd_agencia = ? AND t1.cli_numero_conta = ? LIMIT 1");

                ps.setInt(1, agencia);

                ps.setInt(2, Integer.parseInt(conta));

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    return rs.getString("cli_nome");
                }
            } catch (SQLException e) {

            }
        } catch (Exception ex) {

        }
        return null;
    }

}
