package Models;

import java.math.BigDecimal;

public class Conta {

    private static int id;

    private static int numConta;

    private static double valores;

    private static String data;

    private static String tipo;

    private static int transacaoId;

    private static int agencia;

    private static int numeroConta;

    private static BigDecimal valor;

    private static String nome;

    private static boolean existe;

    public static BigDecimal getValor() {
        return valor;
    }

    public static void setValor(BigDecimal valor) {
        Conta.valor = valor;
    }
    private static String descricao;

    public static String getDescricao() {
        return descricao;
    }

    public static void setDescricao(String descricao) {
        Conta.descricao = descricao;
    }

    public static String getData() {
        return data;
    }

    public static void setData(String data) {
        Conta.data = data;
    }

    public static boolean isExiste() {
        return existe;
    }

    public static void setExiste(boolean existe) {
        Conta.existe = existe;
    }

    public static int getTransacaoId() {
        return transacaoId;
    }

    public static void setTransacaoId(int transacaoId) {
        Conta.transacaoId = transacaoId;
    }

    public static int getAgencia() {
        return agencia;
    }

    public static void setAgencia(int agencia) {
        Conta.agencia = agencia;
    }

    public static int getNumeroConta() {
        return numeroConta;
    }

    public static void setNumeroConta(int numeroConta) {
        Conta.numeroConta = numeroConta;
    }
    
    public static String getNome() {
        return nome;
    }

    public static void setNome(String nome) {
        Conta.nome = nome;
    }
    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Conta.id = id;
    }

    public static int getNumConta() {
        return numConta;
    }

    public static void setNumConta(int numConta) {
        Conta.numConta = numConta;
    }

    public static double getValores() {
        return valores;
    }

    public static void setValores(double valores) {
        Conta.valores = valores;
    }

    public static String getTipo() {
        return tipo;
    }

    public static void setTipo(String tipo) {
        Conta.tipo = tipo;
    }

}
