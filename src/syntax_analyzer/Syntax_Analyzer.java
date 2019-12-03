/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax_analyzer;
import lexical_analyzer.Node;
import lexical_analyzer.Tokenizer;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 *
 * @author Ameer Ayman
 */
public class Syntax_Analyzer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        FileInputStream fin = new FileInputStream("C:\\Users\\Ameer Ayman\\Documents\\NetBeansProjects\\Lexical_Analyzer\\tokens.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fin));
        //LineNumberReader reader = new LineNumberReader(new FileReader("C:\\Users\\Ameer Ayman\\Documents\\NetBeansProjects\\Lexical_Analyzer\\lineNum.txt"));

        FileInputStream fil = new FileInputStream("C:\\Users\\Ameer Ayman\\Documents\\NetBeansProjects\\Lexical_Analyzer\\lineNum.txt");
        BufferedReader b = new BufferedReader(new InputStreamReader(fil));
        
        FileInputStream fill = new FileInputStream("C:\\Users\\Ameer Ayman\\Documents\\NetBeansProjects\\Lexical_Analyzer\\types.txt");
        BufferedReader bb = new BufferedReader(new InputStreamReader(fill));
        Parser p=new Parser(br,b,bb);
        
        p.program();
        p.print();
    }

}
 /*
program example ( input , output );

var x, y, greater: integer ; var A, B, C: array[100..1] of float ;


function func1 (l , m : integer) : integer ;
var M, N : integer ;
var o, p, q, r, s, t: integer ;
begin 
Z [M * 6] = x + 5
end;


function func1 : integer ;
var M, N : integer ;
var o, p, q, r, s, t: integer ;
begin 
end;

function func1 : integer ;
begin 
end;
procedure pro1 (l : integer ; l : integer);
begin 
Z [M * 6] = x + 5 ; Z [M * 6] = x + 5
end;


procedure pro1 ;
begin 
{*Procedure_Statement*}
A (S + 8.5 , x >= y * 3)
end;
procedure pro1 ;
begin 
{*Procedure_Statement*}
A (S + not 8.5 , x >= y * 3)
end;
begin
end.

*/