package com.ysabynin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CykAlgorithm {
    private static HashMap<String, HashSet<String>> set = new HashMap<>();
    private static String[] variables;
    private static String[] terminals;
    private static int productions = 0;
    private static HashSet<String>[][] a;
    private static String s;
    private static File grammar;
    private static File strings;

    public void proccessGrammar(String grammarFileName, String inputsFileName) {
        grammar = new File(grammarFileName);
        strings = new File(inputsFileName);

        int nonTerminalsNumber;
        int terminalsNumber;

        try (Scanner gFile = new Scanner(grammar)) {
            nonTerminalsNumber = gFile.nextInt();
            terminalsNumber = gFile.nextInt();
            productions = gFile.nextInt();
            gFile.nextLine();

            variables = new String[nonTerminalsNumber];
            for (int i = 0; i < nonTerminalsNumber; i++) {
                variables[i] = gFile.next();
            }

            terminals = new String[terminalsNumber];
            for (int i = 0; i < terminalsNumber; i++) {
                terminals[i] = gFile.next();
            }

            System.out.println("\n<-----------------GRAMMAR----------------->");
            for (int i = 0; i < productions; i++) {
                String lhs = gFile.next();
                HashSet<String> s = new HashSet<>();
                String rhs = gFile.nextLine();
                rhs = rhs.replaceAll(" ", "");
                System.out.println(lhs+" -> "+rhs);
                if (set.containsKey(rhs))
                    set.get(rhs).add(lhs);
                else {
                    s.add(lhs);
                    set.put(rhs, s);
                }
            }
            System.out.println("<----------------------------------------->");
        } catch (FileNotFoundException e) {
            System.out.println("File with grammar cannot be found!");
        }
    }

    public void doCykAlgorithm() {
        try (Scanner sFile = new Scanner(strings)) {
            int numStrings = sFile.nextInt();
            sFile.nextLine();
            for (int n = 0; n < numStrings; n++) {
                s = sFile.nextLine();
                String[] string = new String[s.length()];
                a = new HashSet[s.length() + 1][s.length() + 1];

                for (int j = 0; j < s.length(); j++) {
                    string[j] = s.charAt(j) + "";
                }

                for (int i = 0; i < s.length() + 1; i++)
                    for (int j = 0; j < s.length() + 1; j++) {
                        a[i][j] = new HashSet(new HashSet<String>());
                    }

                for (int i = 0; i < s.length(); i++) {
                    String var = set.get(string[i]).toString();
                    var = var.replaceAll("[\\[\\]]", "");
                    a[i][1].add(var);
                }

                for (int j = 1; j <= s.length() + 1; j++) {
                    for (int i = 0; i <= s.length() - j; i++) {
                        for (int k = 0; k < j; k++) {
                            for (String t : a[i][k]) {
                                for (String v : a[i + k][j - k]) {

                                    String[] t1 = t.split(", ");
                                    String[] v1 = v.split(", ");
                                    for (String tTemp : t1) {
                                        for (String vTemp : v1) {
                                            String temp = tTemp + vTemp;
                                            if (set.containsKey(temp)) {
                                                String add = set.get(temp).toString();
                                                add = add.replaceAll("[\\[\\]]", "");
                                                if(add.contains(", ")){
                                                    String[] tok = add.split(", ");
                                                    for (String s1 : tok) {
                                                        a[i][j].add(s1);
                                                    }
                                                } else {
                                                    a[i][j].add(add);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                printMatrix();
            }

        } catch (FileNotFoundException e) {
            System.out.println("File with test strings cannot be found!");
        }
    }

    public void printMatrix() {
        System.out.println("\n<-----------------RESULT----------------->");
        System.out.println("Text:" + s);
        System.out.println("Matrix:");
        for (int i = s.length(); i >= 1; i--) {//columns
            for (int j = 0; j < s.length(); j++) {//rows
                if (a[j][i].isEmpty()) System.out.print("- ");
                else {
                    for (String s1 : a[j][i]) {
                        System.out.print(s1+",");
                    }
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
        System.out.println();
        writeResult();
        System.out.println("<----------------------------------------->");
    }

    private static void writeResult() {
        if (a[0][s.length()].contains("S")) {
            System.out.println(s + " - Yes");
        } else {
            System.out.println(s + " - No");
        }
    }
}
