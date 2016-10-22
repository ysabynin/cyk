package com.ysabynin;

public class Main {
    public static void main(String[] args) {
        if(args.length == 2){
            CykAlgorithm cykAlgorithm = new CykAlgorithm();
            System.out.println("<----------------------------------NEW FILE------------------------------->");
            //first should come grammar file, after text file
            cykAlgorithm.proccessGrammar(args[0], args[1]);
            cykAlgorithm.doCykAlgorithm();
        } else System.out.println("Some of the arguments are missed");
    }
}
