
package Models;

public class Cliente {
    private static int numeroConta;
    private static String nome;
    private static String agencia;
    private static String tipo_conta;
    private static String tipoTpp;
    /**
     * 
     * @return 
     */
    public static String getTipoTpp() {
        return tipoTpp;
    }
    /**
     * 
     * @param tipoTpp 
     */
    public static void setTipoTpp(String tipoTpp) {
        Cliente.tipoTpp = tipoTpp;
    }

    /**
     * 
     * @return 
     */
    public static int getNumeroConta() {
        return numeroConta;
    }
    /**
     * 
     * @param numeroConta 
     */
    public static void setNumeroConta(int numeroConta) {
        Cliente.numeroConta = numeroConta;
    }
    /**
     * 
     * @return 
     */
    public static String getNome() {
        return nome;
    }
    /**
     * 
     * @param nome 
     */
    public static void setNome(String nome) {
        Cliente.nome = nome;
    }
    /**
     * 
     * @return 
     */
    public static String getAgencia() {
        return agencia;
    }
    /**
     * 
     * @param agencia 
     */
    public static void setAgencia(String agencia) {
        Cliente.agencia = agencia;
    }
    /**
     * 
     * @return 
     */
    public static String getTipo_conta() {
        return tipo_conta;
    }
    /**
     * 
     * @param tipo_conta 
     */
    public static void setTipo_conta(String tipo_conta) {
        Cliente.tipo_conta = tipo_conta;
    }

    
}
