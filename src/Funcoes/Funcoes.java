package Funcoes;

import ConexaoDB.Conexao;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;


public class Funcoes {

    private static int tid;

    private static boolean telaSaqueAberta = false;

    public static int getTid() {
        return tid;
    }

    public static void setTid(int tid) {
        Funcoes.tid = tid;
    }

    public static boolean isTelaSaqueAberta() {
        return telaSaqueAberta;
    }

    public static void setTelaSaqueAberta(boolean telaSaqueAberta) {
        Funcoes.telaSaqueAberta = telaSaqueAberta;
    }

    public static int getId() {
        return tid;
    }

    public static void setId(int id) {
        tid = id;
    }

    public static void centralizarContainer(Component pai, Component filho) {
        // obter dimensões do pai
        int larguraPai = pai.getWidth();
        int alturaPai = pai.getHeight();
        // obter dimensões do filho
        int larguraFilho = filho.getWidth();
        int alturaFilho = filho.getWidth();
        // calcular novas coordenadas do filho  
        int novoX = (larguraPai - larguraFilho);
        int novoY = (alturaPai - alturaFilho);
        // centralizar filho
        filho.getParent().setLayout(new GridBagLayout());
        filho.setSize(new Dimension(novoX, novoY));
        filho.repaint();
    }

    /**
     * <p>
     * Retorna uma string formatada com a data <br> e insere em colunas onde há
     * a data:
     * <b>Exemplo</b>: 05/03/2018 às 12:45
     * </p>
     *
     * @return
     */
    public static String getDataFormat() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy 'ÀS' HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String data = String.valueOf(dateFormat.format(cal.getTime()));
        return data;
    }

    /**
     * <p>
     * Busca o tipo da conta por id</p>
     *
     * @param tipoDaContaId int - O Id da conta cnt_id;
     * @return String - Busca o tipo da conta
     */
    public static String getTipoConta(int tipoDaContaId) {
        try {
            PreparedStatement stm = Conexao.abrir().prepareStatement("SELECT * FROM tipo_de_contas WHERE cnt_id = ?;");

            stm.setInt(1, tipoDaContaId);

            ResultSet result = stm.executeQuery();

            if (result.next()) {
                String foundType = result.getString("cnt_tipo");

                return foundType;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Houve um erro. " + e.getMessage(), "Atenção", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     *
     * @param s
     * @return
     */
    public static boolean isZero(String s) {
        return Double.valueOf(s).equals(0.0);
    }

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    /**
     * <p style="font:16px arial">Formata um número.</p>
     *
     * @param numero o número para ser formatado
     * @return
     */
    public static String formatarNumeroAsCurrency(Object numero) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(numero);
    }

    /**
     *
     * @param jFormatTextFied
     */
    public static void permitirNumeroAsCurrencyLocal(JFormattedTextField jFormatTextFied) {
        DecimalFormat dFormat = new DecimalFormat("#,###,###.00");
        NumberFormatter formatar = new NumberFormatter(dFormat);
        formatar.setFormat(dFormat);
        formatar.setAllowsInvalid(false);
        formatar.setOverwriteMode(false);
        jFormatTextFied.setFormatterFactory(new DefaultFormatterFactory(formatar));
    }

    public static String decimalFormat(BigDecimal Preis) {
        String res = "0.00";
        if (Preis != null) {
            NumberFormat nf = NumberFormat.getInstance();
            if (nf instanceof DecimalFormat) {
                ((DecimalFormat) nf).applyPattern("###0.00");

            }
            res = nf.format(Preis);
        }

        return res;
    }


}
