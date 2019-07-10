/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Taffarel <taffarel_deus@hotmail.com>
 */
public class Dinheiro extends BigDecimal {

    public Dinheiro(String val) {
        super(val);
    }

    /**
     *
     * @param str
     * @return
     */
    public static BigDecimal parseStringToBiDecimal(String str) {
        return new BigDecimal(str.replaceAll("\\.", "").replace(",", "."));
    }

    public static BigDecimal parseStringToDouble(String str) {
        return new BigDecimal(str.replaceAll("\\.", "").replace(",", ".")).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal parse(final String amount, final Locale locale) throws ParseException {
        final NumberFormat format = NumberFormat.getNumberInstance(locale);
        if (format instanceof DecimalFormat) {
            ((DecimalFormat) format).setParseBigDecimal(true);
        }
        return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]", ""));
    }

    /**
     *
     * @param numero1 Primeiro número
     * @param numero2 Segundo Número
     * @return -1=numericamente menor que, 0= igual a; ou 1 maior que val.
     */
    public static int compare(String numero1, String numero2) {
        return new BigDecimal(numero1).compareTo(new BigDecimal(numero2));
    }

    /**
     *
     * @param numero1
     * @param numero2
     * @return
     */
    public static BigDecimal somar(String numero1, String numero2) {
        return new BigDecimal(numero1).add(new BigDecimal(numero2));
    }

    public static BigDecimal somar(BigDecimal numero1, BigDecimal numero2) {
        return numero1.add(numero2);
    }

    public static BigDecimal dividir(String numero1, String numero2) {
        return new BigDecimal(numero1).divide(new BigDecimal(numero2), 3, RoundingMode.FLOOR);
    }

    /**
     *
     * @param numero1
     * @param numero2
     * @return
     */
    public static BigDecimal dividir(double numero1, double numero2) {
        return new BigDecimal(numero1).divide(new BigDecimal(numero2), 3, RoundingMode.FLOOR);
    }

    /**
     *
     * @param numero1
     * @param numero2
     * @return
     */
    public static BigDecimal dividir(int numero1, int numero2) {
        return new BigDecimal(numero1).divide(new BigDecimal(numero2), 3, RoundingMode.FLOOR);
    }

    /**
     *
     * @param numero1
     * @param numero2
     * @return
     */
    public static BigDecimal mutiplicar(int numero1, int numero2) {
        return new BigDecimal(numero1).multiply(new BigDecimal(numero2));
    }

    public static BigDecimal mutiplicar(String numero1, String numero2) {
        return new BigDecimal(numero1).multiply(new BigDecimal(numero2));
    }

    public static BigDecimal mutiplicar(double numero1, double numero2) {
        return new BigDecimal(numero1).multiply(new BigDecimal(numero2));
    }

    /**
     *
     * @param str
     * @return
     */
    public static String getNumberFormatedCurreny(String str) {
        Number number;
        try {
            number = NumberFormat.getInstance().parse(str);
            return NumberFormat.getCurrencyInstance().format(number);
        } catch (ParseException ex) {
            Logger.getLogger(Dinheiro.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

//  
//Testar
//public static void main(String[] args) throws ParseException {
//
//        String str = "1.000";
//
//        System.out.println(compare("5.00", "4.00"));
//
//        System.out.println(getNumberFormatedCurreny(str));
//        System.out.println(parseStringToBiDecimal(str));
//        //-----------------------------------
//        System.out.println(parseStringToBiDecimal(str).toString());
//        System.out.println(mutiplicar(parseStringToBiDecimal(str).toString(), parseStringToBiDecimal(str).toString()));
//    }
}
