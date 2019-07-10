package ConexaoDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Faz as conexões com o banco de dados.
 */
public class Conexao {

    /**
     * O Usuário do banco
     */
    private static final String USUARIO = "postgres";
    /**
     * A senha do banco
     */
    private static final String SENHA = "chkdsk";
    /**
     * bancoifto_db=O Banco de dados
     */
    private static final String URL = "jdbc:postgresql://localhost:5432/Banco_Analise_2";
    /**
     * O Drive do Mysql
     */
    private static final String DRIVER = "org.postgresql.Driver";

    public Conexao() {
    } //Possibilita instancias

    /**
     * <h2>Abre a conexão com o banco.</h2>
     *
     * @return
     * @throws Exception
     */
    public static Connection abrir() throws Exception {
        try {
            
            // Registrar o driver
            Class.forName(DRIVER);
            // Capturar a conexão
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            // Retorna a conexao aberta
            return conn;
        } catch (SQLException e) {
            switch (e.getSQLState()) {
                case "28000":
                    JOptionPane.showMessageDialog(null, "A senha do banco está incorreta.\n" + e.getMessage(),
                            "Atenção", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                case "08S01":
                    JOptionPane.showMessageDialog(null, "O servidor de banco de dados SQL não está ligado.\n" + e.getMessage().replaceAll("\\.", "\n"),
                            "Atenção", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Houve um erro.\n" + e.getMessage(), 
                            "Atenção", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        }
        return null;
    }

    /**
     * Fecha a Conexão
     *
     * @throws Exception
     */
    public static void fecharConexao() throws Exception {
        abrir().close();
    }
}
