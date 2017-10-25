package br.edu.uea.estsiv031.chatuea.helper;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by rsouza on 16/10/17.
 */

public class RSA {

    private BigInteger p, q, n, m, d, e = new BigInteger("3");
    static final int bitlen = 2048;

    public RSA(){}

    public void gerarChaves(){

        SecureRandom r = new SecureRandom();

        setP(new BigInteger(bitlen / 2, 100, r));
        setQ(new BigInteger(bitlen / 2, 100, r));

        setN(getP(),getQ());

        setM(getP().subtract(BigInteger.ONE), getQ().subtract(BigInteger.ONE));

        setE(getM());

        setD(getE(),getM());

    }

    public BigInteger getP() {
        return p;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public BigInteger getQ() {
        return q;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public BigInteger getN() {
        return n;
    }

    public void setN(BigInteger p, BigInteger q) {
        this.n = p.multiply(q);;
    }

    public BigInteger getM() {
        return m;
    }

    public void setM(BigInteger p, BigInteger q) {
        this.m = (p.subtract(BigInteger.ONE))
                .multiply(q.subtract(BigInteger.ONE));
    }

    public BigInteger getD() {
        return d;
    }

    public void setD(BigInteger e, BigInteger m) {
        this.d = e.modInverse(m);
    }

    public BigInteger getE() {
        return e;
    }

    public void setE(BigInteger m) {
        this.e = new BigInteger("3");
        while(m.gcd(getE()).intValue() > 1)
            this.e = getE().add(new BigInteger("2"));
    }

    public String encriptar(String e, String n, String msg){

        return new BigInteger(msg.getBytes()).modPow(new BigInteger(e), new BigInteger(n)).toString();

    }

    public String decriptar(String d, String n, String msg){

        return new String(new BigInteger(msg).modPow(new BigInteger(d), new BigInteger(n)).toByteArray());

    }

}
