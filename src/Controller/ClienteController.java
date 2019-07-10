package Controller;

import ConexaoDB.Conexao;
import Funcoes.Funcoes;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import tx.utilitarios.TXPasswordCheck;

public class ClienteController {


    /**
     * <p style='font:16px arial;padding:0px;margin:0px;'><b>Conta quantos
     * clientes da tabela</b><br>
 ClienteController pelos filtros Agencia e Conta</p>
     * <h2>Verifica se os dados colocado na primeira tela (Agência e
     * Conta)existem.</h2>
     *
     * @param Agencia
     * @param Conta
     * @return
     */
    public static int clienteExiste(JTextField Agencia, JTextField Conta) {
        try {
            String sql = "SELECT COUNT(*) AS total FROM clientes AS t1 JOIN cidades as t2 ON"
                    + " t1.cli_agencia = t2.cdd_id WHERE t2.cdd_agencia = ?"
                    + " AND t1.cli_numero_conta = ? LIMIT 1";

            PreparedStatement stm = Conexao.abrir().prepareStatement(sql);

            stm.setInt(1, Integer.parseInt(Agencia.getText().replaceAll("\\.", "")));

            stm.setInt(2, Integer.parseInt(Conta.getText().replaceAll("\\.", "")));

            ResultSet result = stm.executeQuery();

            if (result.next()) {

                String foundType = result.getString(1);

                return Integer.valueOf(foundType) > 0 ? 1 : 0;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Houve um erro. " + e.getMessage(), "Atenção COD.3", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     *
     * @param Agencia
     * @param Conta
     * @return
     */
    public static ResultSet isBloqueado(JTextField Agencia, JTextField Conta) {
        try {
            String sql = "SELECT * FROM clientes AS t1 JOIN cidades as t2 ON"
                    + " t1.cli_agencia = t2.cdd_id WHERE t2.cdd_agencia = ?"
                    + " AND t1.cli_numero_conta = ? LIMIT 1";

            PreparedStatement stm = Conexao.abrir().prepareStatement(sql);

            stm.setInt(1, Integer.parseInt(Agencia.getText()));

            stm.setInt(2, Integer.parseInt(Conta.getText()));

            ResultSet result = stm.executeQuery();

            return result;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Houve um erro. " + e.getMessage(), "Atenção COD.6", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * <p>
     * Grava o último login realizado com sucesso no banco de dados. Na próxima
     * vez que for entrar novamente, busca se nessa tabela o <br>
     * último registro e mostra no campo de Agência e Conta.
     * </p>
     *
     * @param numConta
     * @return
     */
    public static int gravarLogin(int numConta) {
        try {
            String sql = "INSERT INTO login_sessao_clientes (login_cliente_id, login_data) VALUES (?,?);";
            PreparedStatement preparedStatement = Conexao.abrir().prepareStatement(sql);
            preparedStatement.setInt(1, numConta);
            preparedStatement.setString(2, Funcoes.getDataFormat());
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção COD.4", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     * <p>
 Retorna o último login do ClienteController.</p>
     *
     * @return
     */
    public static String[] getUltimoLogin() {
        try {
            String sql = "SELECT login_cliente_id, cdd_agencia FROM login_sessao_clientes"
                    + " as t1 JOIN clientes AS t2 ON t1.login_cliente_id = "
                    + "t2.cli_numero_conta JOIN cidades AS t3 ON t3.cdd_id = "
                    + "t2.cli_agencia ORDER BY login_id DESC LIMIT 1";

            PreparedStatement stm = Conexao.abrir().prepareStatement(sql);

            ResultSet rs = stm.executeQuery();

            String r[] = new String[2];

            if (rs.next()) {
                r[0] = rs.getString("login_cliente_id");
                r[1] = rs.getString("cdd_agencia");
            } else {
                r[0] = "";
                r[1] = "";
            }
            return r;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção COD.5", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    
    /**
     * <p style='font:16px arial;padding:0px;margin:0px;'>Faz o fazerLogin do
 ClienteController.</p>
     * @param Agencia
     * @param Conta
     * @return
     */
    public boolean fazerLogin(JTextField Agencia, JTextField Conta) {

        if (Agencia.getText().equals("") || Conta.getText().equals("")) {

            JOptionPane.showMessageDialog(null, "Agência ou Conta Inválidos.", "Atenção!", JOptionPane.ERROR_MESSAGE);

        } else {
            //Se os dados estiverem corretos
            if (clienteExiste(Agencia, Conta) > 0) {
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Conta Inexistente.", "Atenção!", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

  
    /**
     * <p>
     * Busca o dados de cliente. Observação: usa-se a função JOIN do Mysql para
     * unir a tabela clientes com a <br>
     * tabela cidades.
     * </p>
     *
     * @param agencia O número da agência
     * @param conta O número da conta
     * @return ClienteController
     */
    public String getDadosCliente(int agencia, int conta) {
        try {

            PreparedStatement ps = Conexao.abrir().prepareStatement("SELECT * FROM clientes AS t1 JOIN"
                    + " cidades as t2 ON t1.cli_agencia = t2.cdd_id WHERE "
                    + "t2.cdd_agencia = ? AND t1.cli_numero_conta = ? LIMIT 1");

            ps.setInt(1, agencia);

            ps.setInt(2, conta);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Models.Cliente.setNumeroConta(rs.getInt("cli_numero_conta"));
                Models.Cliente.setNome(rs.getString("cli_nome"));
                Models.Cliente.setAgencia(rs.getString("cdd_agencia"));
                Models.Cliente.setTipo_conta(rs.getString("cli_tipo_conta"));
            }

            return null;

        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     * <h2>Verifica a senha na segunda tela de login.</h2>
     *
     * @param conta
     * @param senha
     * @return Retorna 1 se for encontrado o usuário.
     */
    public int verificarSenha(int conta, String senha) {
        try {

            PreparedStatement stm = Conexao.abrir().prepareStatement("SELECT * FROM clientes WHERE cli_numero_conta = ? LIMIT 1 ;");

            stm.setInt(1, conta);

            ResultSet result = stm.executeQuery();

            if (result != null) {

                result.next();

                String pass = result.getString("cli_senha");

                return TXPasswordCheck.verificarPassword(senha, pass) ? 1 : 0;

            }
            return -1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção COD.2", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    /**
     * <p style="font:16px arial">Bloqueia ou desbloqueia um cliente.</p>
     *
     * @param valor Zero ou um. 0;bloqueado; 1=desbloqueado
     * @param bloqueioMotivo Um número inteiro. -1=desbloqueado; 0= é o valor inicial quando é
     * adicionado ao sistema; 1=bloqueado porque excedeu o limite permitido por
     * login, que é 3 chances
     * @param numeroDaConta O número da conta do cliente;
     * @return
     */
    public static int bloquearDesbloquearCliente(int valor, int bloqueioMotivo, int numeroDaConta) {

        try {
            String sql = "UPDATE clientes SET cli_status = ?, cli_bloqueio_motivo =? WHERE cli_numero_conta = ?;";
            PreparedStatement ps = Conexao.abrir().prepareStatement(sql);

            ps.setInt(1, valor);

            ps.setInt(2, bloqueioMotivo);

            ps.setInt(3, numeroDaConta);

            return ps.executeUpdate();

        } catch (Exception ex) {
            Logger.getLogger(ClienteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
