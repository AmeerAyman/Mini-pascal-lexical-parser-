/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexical_analyzer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ameer Ayman
 */
public class Tokenizer extends Node {

    private ArrayList<String> obj;
    public ArrayList<String> finall;
    public ArrayList<String> lineNum;
    private String[] tokens;
    public String[] types;

    public void readFromFile_RemoveSingleComments_RemoveSpaces() {
        BufferedReader br = null;
        
        obj = new ArrayList<String>();
        try {
            br = new BufferedReader(new FileReader("C:\\Users\\Ameer Ayman\\Documents\\NetBeansProjects\\Lexical_Analyzer\\cases.txt"));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage() + "Errooooor:file not found");
            System.exit(0);
        }

        try {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().length() > 0) {

                    if (line.trim().startsWith("{") && line.trim().endsWith("}")) {

                        continue;
                    }
                    obj.add(line.replaceAll("\\s+", " "));
                }
            }
        } catch (IOException ex) {
            System.out.println("Errooor");
        }
    }

    public void removeMultiComments() {

        boolean flag1 = false, flag2 = false;
        int startIndex = 0;
        int endIndex = 0;
        String text;
        for (int i = 0; i < obj.size(); i++) {
            text = obj.get(i);
            for (int j = 0; j < text.length(); j++) {
                
                    if (text.charAt(j) == '{' && text.charAt(j + 1) == '*') {
                        startIndex = i;
                        flag1 = true;
                        //break;
                    } 
                    
                    else if (text.charAt(j) == '*' && text.charAt(j + 1) == '}') {
                        endIndex = i;
                        flag2 = true;
                       //break;
                    }else
                        break;
                
            }
        }
            if (flag1 == true && flag2 == true) {
                obj.subList(startIndex, endIndex + 1).clear();
            }
        
    }

    public void putSpacesBetweenWordsAndSymbols() {

        String tempText = "";
        String[] st = {"!", "@", "#", "$", "%", "&", "*", "(", ")", "-", "=", "+", "[", "]", "{", "}", "/", "|", ",", ":", ";", "<", ">", "?", ".."};
        for (int i = 0; i < obj.size(); i++) {

            tempText = obj.get(i);
            for (int j = 0; j < st.length; j++) {
                if (tempText.contains(st[j])) {
                    tempText = tempText.replaceAll("\\" + st[j], " " + "" + st[j] + " ");
                }
                if (tempText.contains("!  =")) {
                    tempText = tempText.replaceAll("!  =", "!=");
                }
                if (tempText.contains("<  =")) {
                    tempText = tempText.replaceAll("<  =", "<=");
                }
                if (tempText.contains(">  =")) {
                    tempText = tempText.replaceAll(">  =", ">=");
                }
                if (tempText.contains("=  =")) {
                    tempText = tempText.replaceAll("=  =", "==");
                }
                if (tempText.contains("end.")) {
                    tempText = tempText.replaceAll("end.", "end .");
                }

            }
            obj.set(i, tempText);
        }
    }

    public void tokenization() {

        int num;
        String numm;

        lineNum = new ArrayList();
        finall = new ArrayList();
        String st;
        tokens = new String[obj.size()];
        for (int i = 0; i < obj.size(); i++) {
            num = (i + 1);
            numm=String.valueOf(num);
            st = obj.get(i);
            st = st.replaceAll("\\s+", " ");
            st = st.trim();
            st = st.toLowerCase();
            tokens = st.split(" ");

            for (int j = 0; j < tokens.length; j++) {
                lineNum.add(numm);
            }
            finall.addAll(Arrays.asList(tokens));
        }
    }

    public boolean isDataType(String s) {

        boolean flag = false;
        if ("integer".equals(s) || "float".equals(s)) {
            flag = true;
        }
        return flag;
    }

    public boolean isOperator(String s) {

        String arr[] = {"!=", "+", "=", "-", ">=", "<=", "*", "/", "=="};
        boolean flag = false;
        for (int i = 0; i < arr.length; i++) {
            if (s == null ? arr[i] == null : s.equals(arr[i])) {
                flag = true;
            }
        }
        return flag;
    }

    public boolean isSymbol(String s) {

        String arr[] = {",", "..", ";", ".", "&", "@", "(", ")", "[", "]", "{", "}", ":"};
        boolean flag = false;
        for (int i = 0; i < arr.length; i++) {
            if (s == null ? arr[i] == null : s.equals(arr[i])) {
                flag = true;
            }
        }
        return flag;
    }

    public boolean isreservedWord(String s) {

        String arr[] = {"and", "array", "begin", "case", "const", "div", "do", "downto", "else", "end", "file",
            "for", "function", "goto", "if", "in", "label", "mod", "nil", "not", "of", "or", "packed", "procedure",
            "program", "record", "repeat", "set", "then", "to", "type", "until", "var", "while", "with"};
        boolean flag = false;
        for (int i = 0; i < arr.length; i++) {
            if (s == null ? arr[i] == null : s.equals(arr[i])) {
                flag = true;
            }
        }
        return flag;
    }

    public boolean isIdentifier(String s) {

        boolean flag;
        flag = machineForIdentifiers(s);
        return flag;
    }

    public boolean isLiteral(String s) {

        boolean flag;
        flag = machineForliterals(s);
        return flag;
    }

    public void specifyTypeOfEachToken() {

        types = new String[finall.size()];
        for (int i = 0; i < finall.size(); i++) {
            String stringForChecking = finall.get(i);
            if (isDataType(stringForChecking)) {
                types[i] = "DataType";
            } else if (isOperator(stringForChecking)) {
                types[i] = "operator";
            } else if (isSymbol(stringForChecking)) {
                types[i] = "Symbol";
            } else if (isreservedWord(stringForChecking)) {
                types[i] = "Reserved Word";
            } else if (isIdentifier(stringForChecking)) {
                types[i] = "Identifier";
            } else if (isLiteral(stringForChecking)) {
                types[i] = "Literal";
            } else {
                types[i] = "Unknown";
            }

        }
    }

    public void displayAllLinesOftheSourceCode() {

        for (int i = 0; i < obj.size(); i++) {
            System.out.println(obj.get(i));
        }
    }

    public void WriteInFileAndDisplayTableOfTokensWithLineNumberAndType() {

        File newFile = new File("C:\\Users\\Ameer Ayman\\Documents\\NetBeansProjects\\Lexical_Analyzer\\tokens.txt");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(newFile));
            bw.write("Token" + "\t\t" + "LineNumber" + "\t" + "Type");
            bw.newLine();
            bw.write("---------------------------------------------");
            bw.newLine();

            System.out.println("Token" + "\t\t" + "LineNumber" + "\t" + "Type");
            System.out.println("---------------------------------------------");
            for (int i = 0; i < finall.size(); i++) {
                bw.write(finall.get(i) + "\t\t" + lineNum.get(i) + "\t\t" + types[i]);
                bw.newLine();
                bw.flush();

                System.out.println(finall.get(i) + "\t\t" + lineNum.get(i) + "\t\t" + types[i]);

            }
            System.out.println("The tokens wrote in text file name token ");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Tokenizer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void WriteTokensInFile() {

        File newFile = new File("C:\\Users\\Ameer Ayman\\Documents\\NetBeansProjects\\Lexical_Analyzer\\tokens.txt");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(newFile));
            
            for (int i = 0; i < finall.size(); i++) {
                bw.write(finall.get(i));
                bw.newLine();
                bw.flush();

                System.out.println(finall.get(i));

            }
            System.out.println("The tokens wrote in text file name token ");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Tokenizer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
     public void WriteLineNumberInFile() {

        File newFile = new File("C:\\Users\\Ameer Ayman\\Documents\\NetBeansProjects\\Lexical_Analyzer\\lineNum.txt");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(newFile));
            
            for (int i = 0; i < finall.size(); i++) {
                bw.write(lineNum.get(i));
                bw.newLine();
                bw.flush();

               // System.out.println(lineNum.get(i));

            }
            System.out.println("The line numbers wrote in text file name lineNum ");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Tokenizer.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
        public void WriteTypesInFile() {

        File newFile = new File("C:\\Users\\Ameer Ayman\\Documents\\NetBeansProjects\\Lexical_Analyzer\\types.txt");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(newFile));
            
            for (int i = 0; i < finall.size(); i++) {
                bw.write(types[i]);
                bw.newLine();
                bw.flush();

                //System.out.println(types[i]);

            }
            System.out.println("The types wrote in text file name types ");
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(Tokenizer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
