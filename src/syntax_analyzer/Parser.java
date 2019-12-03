/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syntax_analyzer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import lexical_analyzer.Tokenizer;
import lexical_analyzer.Tokenizer;

/**
 *
 * @author Ameer Ayman
 */
public class Parser extends Tokenizer {

    BufferedReader br;
    BufferedReader b;
    BufferedReader bb;
    //LineNumberReader reader;
    private String token;
    private String lineNumber;
    private String type;
    StringBuilder sb;

    public Parser(BufferedReader br, BufferedReader b, BufferedReader bb) {
        this.br = br;
        this.bb = bb;
        this.b = b;
    }

    public int readLineNumber(final LineNumberReader reader) throws IOException {
        int lineNum = 0;
        reader.readLine();
        lineNum = reader.getLineNumber();
        return lineNum;

    }

    public String readLineNumber1(final BufferedReader b) throws IOException {
        String next_line = "";
        if ((next_line = b.readLine()) == null) {
            return "at end line";
        } else {
            return next_line;
        }

    }

    public String readTypes(final BufferedReader bb) throws IOException {
        String next_line = "";

        next_line = bb.readLine();

        return next_line;

    }

    public static String lexical(final BufferedReader br) throws IOException {
        String next_line = "";

        next_line = br.readLine();

        return next_line;
    }

    public void print1() throws IOException {
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        System.out.println(token + " " + lineNumber + " " + type);
    }

    public void print() throws IOException {

        System.out.print(sb);
    }

    public void error(String ex) {
        System.err.println("ERROR!!:" + "Found ( " + token + " ) , " + "Expected ( " + ex + " ) , " + "at line Number ( " + lineNumber + " )");
    }

    public void program() throws IOException {
        sb = new StringBuilder("PROGRAM\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        if (!"program".equals(token)) {
            error("program");
        }
        sb.append("  program\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        //System.err.println(token+type);
        if (!"Identifier".equals(type)) {
            error("Identifier");
        }
        sb.append("  id\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        if (!"(".equals(token)) {
            error("(");
        }
        sb.append("  (\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        //System.err.println(token+type);
        String s = "  ";
        identifier_list(s);
        //System.err.println(token);
        if (!")".equals(token)) {
            error(")");
        }
        sb.append("  )\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        if (!";".equals(token)) {
            error(";");
        }
        sb.append("  ;\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        if ("var".equals(token)) {
            declarations();
        }
        if ("function".equals(token) || "procedure".equals(token)) {
            subProgram_declarations();
        }
        //System.err.println(token);

        compound_statment(s);
        if (!".".equals(token)) {
            error(".");
        }

    }

    public void identifier_list(String s) throws IOException {

        sb.append(s).append("IDENTIFIER_LIST\n");
        if (!"Identifier".equals(type)) {
            error("Identifier");
        }
        sb.append(s + s + "id\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        //System.err.println(token+type);
        identifier_list2(s);

    }

    public void identifier_list2(String s) throws IOException {

        //System.err.println(token+type);
        if (",".equals(token)) {
            sb.append(s + s + "IDENTIFIER_LIST 2\n");
            sb.append(s + s + s + ",\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            //System.err.println(token+type);
            if (!"Identifier".equals(type)) {
                error("Identifier");
                //System.err.println(token+type);
            }
            sb.append(s + s + s + "id\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            //System.err.println(token+type);
            s = "   ";
            identifier_list2(s);
            //System.err.println(token+type);
        } else if (!",".equals(token) && "Identifier".equals(type)) {
            error(",");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        } else
            ;
    }

    public void declarations() throws IOException {
        String s = "  ";
        declarations2(s);

    }

    public void declarations2(String s) throws IOException {

        if ("var".equals(token)) {
            sb.append(s + "DECLARATIONS\n");
            sb.append(s + s + "DECLARATIONS 2\n");
            sb.append(s + s + s + "var\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            s = "      ";
            identifier_list(s);
            if (!":".equals(token)) {
                error(":");
            }
            //System.err.println(token);
            sb.append(s + ":\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            type(s);

            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            //System.err.println(token);
            if (!";".equals(token)) {
                error(";");
            }
            sb.append(s + ";\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);

            declarations2(s);
        } else
            ;

    }

    public void type(String s) throws IOException {
        if ("integer".equals(token) || "float".equals(token)) {
            sb.append(s + "TYPE\n");
            sb.append(s + "  STANDARD_TYPE\n");
            sb.append(s + "   " + token + "\n");

        } else if ("array".equals(token)) {
            sb.append(s + "TYPE\n");
            sb.append(s + "  array\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if (!"[".equals(token)) {
                error("[");
            }
            sb.append(s + "  [\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if (!"Literal".equals(type)) {
                error("Literal");
            }
            sb.append(s + "  num\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if (!"..".equals(token)) {
                error("..");
            }
            sb.append(s + "  ..\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if (!"Literal".equals(type)) {
                error("Literal");
            }
            sb.append(s + "  num\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if (!"]".equals(token)) {
                error("]");
            }
            sb.append(s + "  ]\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if (!"of".equals(token)) {
                error("of");
            }
            sb.append(s + "  of\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if (!"DataType".equals(type)) {
                error("type integer or float");
            }
            sb.append(s + "  STANDARD_TYPE\n");
            sb.append(s + "   " + token + "\n");
        } else {
            error("type integer or float or array");
        }

    }

    public void subProgram_declarations() throws IOException {
        String s = "  ";
        subProgram_declarations2(s);
    }

    public void subProgram_declarations2(String s) throws IOException {
        subProgram_declaration(s);

        if (!";".equals(token)) {
            error(";");
        }
        sb.append(s + s + ";\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);

        if ("function".equals(token) || "procedure".equals(token)) {
            subProgram_declarations2(s + s);

        } else
            ;
    }

    public void subProgram_declaration(String s) throws IOException {

        subProgram_head(s);

        declarations();

        compound_statment(s + s + s + s);
    }

    public void subProgram_head(String s) throws IOException {
        if ("function".equals(token)) {
            sb.append(s + "SUBPROGRAM_DECLARATIONS\n");
            sb.append(s + s + "SUBPROGRAM_DECLARATIONS 2\n");
            sb.append(s + s + s + "SUBPROGRAM_DECLARATION\n");
            sb.append(s + s + s + s + "SUBPROGRAM_HEAD\n");
            sb.append(s + s + s + s + s + "function\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if (!"Identifier".equals(type)) {
                error("Identifier");
            }
            sb.append(s + s + s + s + s + "id\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            arguments(s);
            if (!":".equals(token)) {
                error(":");
            }
            sb.append(s + s + s + s + s + ":\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            //System.err.println(token);
            if (!"DataType".equals(type)) {
                error("type integer or float");
                sb.append(s + s + s + s + s + "STANDARD_TYPE\n");
                sb.append(s + s + s + s + s + " " + token + "\n");
            }
            //System.err.println(token+type+lineNumber);
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);

            if (!";".equals(token)) {
                error(";");
            }
            sb.append(s + s + s + s + s + ";\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);

        } else if ("procedure".equals(token)) {
            sb.append(s + "SUBPROGRAM_DECLARATIONS\n");
            sb.append(s + s + "SUBPROGRAM_DECLARATIONS 2\n");
            sb.append(s + s + s + "SUBPROGRAM_DECLARATION\n");
            sb.append(s + s + s + s + "SUBPROGRAM_HEAD\n");
            sb.append(s + s + s + s + s + "prcedure\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if (!"Identifier".equals(type)) {
                error("Identifier");
            }
            sb.append(s + s + s + s + s + "id\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            arguments(s);
            if (!";".equals(token)) {
                error(";");
            }
            sb.append(s + s + s + s + s + ";\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        } else {
            error("subProgram_head: Function or Procedure");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        }

    }

    public void arguments(String s) throws IOException {
        if ("(".equals(token)) {
            sb.append(s + s + s + s + s + "ARGUMENTS\n");
            sb.append(s + s + s + s + s + " (\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            parameter_list(s);
            if (!")".equals(token)) {
                error(")");
            }
            sb.append(s + s + s + s + s + " )\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        } else;

    }

    public void parameter_list(String s) throws IOException {
        sb.append(s + s + s + s + s + " PARAMETER_LIST\n");
        s = s + s + s + s + s + "  ";
        identifier_list(s);
        if (!":".equals(token)) {
            error(":");
        }
        sb.append(s + "  :\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        type(s);
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        parameter_list2(s);
    }

    public void parameter_list2(String s) throws IOException {
        if (";".equals(token)) {
            sb.append(s + "   PARAMETER_LIST 2\n");
            sb.append(s + "    ;\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            s = s + s + s + s + s + s + s + s;
            identifier_list(s);
            s = s + s + s + s + s + "    ";
            if (!":".equals(token)) {
                error(":");
            }
            sb.append(s + ":\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            type(s);
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);

            parameter_list2(s);

        } else if (!";".equals(token) && "Identifier".equals(type)) {
            error(";");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        } else;
    }

    public void compound_statment(String s) throws IOException {
        sb.append(s + "COMPOUND_STATMENT\n");
        if (!"begin".equals(token)) {
            error("begin");
        }
        sb.append(s + "  begin\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        optional_statment(s + "  ");
        if (!"end".equals(token)) {
            error("end");
        }
        sb.append(s + "  end\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
    }

    public void optional_statment(String s) throws IOException {

        if ("end".equals(token))
            ; else {
            sb.append(s + "OPTIONAL_STATMENT\n");
            statment_list(s);
        }
    }

    public void statment_list(String s) throws IOException {
        sb.append(s + "  STATMENT_LIST\n");
        statment(s);
        sb.append(s + "    STATMENT_LIST 2\n");
        statment_list2(s);
    }

    public void statment_list2(String s) throws IOException {
        if (";".equals(token)) {
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            statment(s);
            statment_list2(s + "     ");
        } else if (!";".equals(token) && ("Identifier".equals(type) || "if".equals(token) || "while".equals(token) || "begin".equals(token))) {
            error(";");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        } else;
    }

    public void statment(String s) throws IOException {
        if ("Identifier".equals(type)) {
            sb.append(s + "    STATMENT\n");

            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            if ("[".equals(token) || "=".equals(token)) {
                sb.append(s + "       VARIABLE\n"); //6 7
                sb.append(s + "        id\n"); //6 8

                if ("=".equals(token)) {
                    sb.append(s + "        =\n"); //6 8
                    token = lexical(br);
                    lineNumber = readLineNumber1(b);
                    type = readTypes(bb);
                    expression(s + s + "  ");
                    //System.err.println(token);
                } else if ("[".equals(token)) {
                    sb.append(s + "        [\n");// 6 8
                    token = lexical(br);
                    lineNumber = readLineNumber1(b);
                    type = readTypes(bb);
                    expression(s + s + "  "); //6 8
                    if (!"]".equals(token)) {
                        error("]");
                    }
                    sb.append(s + "        ]\n"); //6 8
                    token = lexical(br);
                    lineNumber = readLineNumber1(b);
                    type = readTypes(bb);
                    if (!"=".equals(token)) {
                        error("assign Operator");
                    }
                    token = lexical(br);
                    lineNumber = readLineNumber1(b);
                    type = readTypes(bb);
                    expression(s + s + "  ");//6 8
                }
//                variable();
//                token=lexical(br);
//                lineNumber=readLineNumber1(b);
//                type=readTypes(bb);
//                if(!"=".equals(token))
//                    error("assign Operator");
//                token=lexical(br);
//                lineNumber=readLineNumber1(b);
//                type=readTypes(bb);
//                expression();
            } else if ("(".equals(token) || ";".equals(token) || "end".equals(token)) {
                procedure_statment(s + s + " ");
            }
        } else if ("if".equals(token)) {
            sb.append(s + "       if\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            expression(s + s + " ");
            if (!"then".equals(token)) {
                error("then");
            }
            sb.append(s + "       then\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            s = s + s + " ";
            statment(s);
            if (!"else".equals(token)) {
                error("else");
            }
            sb.append(s + "else\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            statment(s);
        } else if ("while".equals(token)) {
            sb.append(s + "       while\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            expression(s + s + " ");
            if (!"do".equals(token)) {
                error("do");
            }
            sb.append(s + "       do\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            s = s + s + "  ";
            statment(s);
        } else if ("begin".equals(token)) {
            compound_statment(s + s + " ");
        } else {
            error("statment");
        }
    }

    public void procedure_statment(String s) throws IOException {
        sb.append(s + "PROCEDURE_STATMENT\n");
        procedure_statment2(s);
    }

    public void procedure_statment2(String s) throws IOException {
        sb.append(s + " PROCEDURE_STATMENT 2\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        expression_list(s + " "); //6 8
        if (!")".equals(token)) {
            error(")");
        }
        sb.append(s + " ) 2\n");
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);

    }

    public void variable(String s) throws IOException {
        if (!"Identifier".equals(type)) {
            error("Identifier");
        }
        token = lexical(br);
        lineNumber = readLineNumber1(b);
        type = readTypes(bb);
        variable2(s);
    }

    public void variable2(String s) throws IOException {
        if ("[".equals(token)) {
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            expression(s);
            if (!"]".equals(token)) {
                error("]");
            }
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        } else;
    }

    public void expression_list(String s) throws IOException {
        sb.append(s + "EXPRESSION_LIST\n");
        expression(s);
        sb.append(s + "   EXPRESSION_LIST 2\n");
        expression_list2(s + " ");
    }

    public void expression(String s) throws IOException {
        sb.append(s + " EXPRESSION\n"); //6 9
        simple_expression(s);
        sb.append(s + "    EXPRESSION 2\n");
        expression2(s + " ");

    }

    public void simple_expression(String s) throws IOException {
        sb.append(s + "  SIMPLE_EXPRESSION\n");//6 10
        term(s);
        sb.append(s + "    SIMPLE_EXPRESSION 2\n");
        simple_expression2(s + "   ");
    }

    public void term(String s) throws IOException {
        sb.append(s + "   TERM\n"); //6 11
        factor(s);
        sb.append(s + "     TERM 2\n");
        //System.err.println(token);
        term2(s);
    }

    public void factor(String s) throws IOException {
        sb.append(s + "    FACTOR\n");//6 12
        if ("Literal".equals(type)) {
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            sb.append(s + "     num\n");//6 13
        } else if ("(".equals(token)) {
            sb.append(s + "     (\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            expression(s + "     ");
            if (!")".equals(token)) {
                error(")");
            }
            sb.append(s + "     )\n");
        } else if ("not".equals(token)) {
            sb.append(s + "     not\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            factor(s + "     ");
        } else if ("Identifier".equals(type)) {
            sb.append(s + "     id\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            factor2(s + "     ");
        } else {
            error("factor");
        }
    }

    public void factor2(String s) throws IOException {
        if ("(".equals(token)) {
            sb.append(s + "     (\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            expression_list(s + "     ");
            if (!")".equals(token)) {
                error(")");
            }
            sb.append(s + "     )\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        } else;
    }

    public void term2(String s) throws IOException {
        if ("*".equals(token) || "/".equals(token)) {
            sb.append(s + "    " + token + "\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            factor(s + "    ");
            term2(s + "      ");
        } else if ((!"*".equals(token) || !"/".equals(token)) && ("Identifier".equals(type) || "Literal".equals(type) || "not".equals(token) || "(".equals(token))) {
            error("Multiplication or division Operator");
            //System.err.println(token);
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            //System.err.println(token);
        } else;
    }

    public void expression_list2(String s) throws IOException {
        if (",".equals(token)) {
            sb.append(s + "   ,\n");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            expression(s);
            expression_list2(s);
        } else if (!",".equals(token) && ("Identifier".equals(type) || "Literal".equals(type) || "not".equals(token) || "(".equals(token))) {
            error(",");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        } else;

    }

    public void expression2(String s) throws IOException {
        if ("<".equals(token) || ">".equals(token) || "=".equals(token) || ">=".equals(token) || "<=".equals(token) || "!=".equals(token)) {
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            simple_expression(s);
        } else;

    }

    public void simple_expression2(String s) throws IOException {
        if ("+".equals(token) || "-".equals(token)) {
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
            term(s + "   ");
            simple_expression2(s + "   ");
        } else if ((!"+".equals(token) || !"-".equals(token)) && ("Identifier".equals(type) || "Literal".equals(type) || "not".equals(token) || "(".equals(token))) {
            error("addition or subtraction Operator");
            token = lexical(br);
            lineNumber = readLineNumber1(b);
            type = readTypes(bb);
        } else;
    }

    public boolean standard_type() throws IOException {
        if (!"integer".equals(type) || !"float".equals(type)) {
            //error("integer or float");
            return false;
        } else {
            return true;
        }
    }
}
