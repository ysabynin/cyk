package com.ysabynin;

public class Main {
    public static void main(String[] args) {
        CykAlgorithm cykAlgorithm = new CykAlgorithm();
        cykAlgorithm.proccessGrammar("grammar.txt", "test.txt");
        cykAlgorithm.doCykAlgorithm();
    }
}
