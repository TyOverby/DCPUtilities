package com.prealpha.dcputil.compiler.assembler;

import com.prealpha.dcputil.compiler.assembler.Assembler;
import com.prealpha.dcputil.compiler.lexer.Lexer;
import com.prealpha.dcputil.compiler.parser.Parser;
import com.prealpha.dcputil.compiler.parser.ParserException;

import static com.prealpha.dcputil.util.PrintUtilities.dump;
import static junit.framework.Assert.assertEquals;

/**
 * User: Ty
 * Date: 7/17/12
 * Time: 5:59 AM
 */
abstract public class CompilerTest {


    protected void assertEqual(char[] expected, char[] actual){
        if(expected.length!=actual.length){
            System.err.println("Expected length: "+expected.length+"  Actual length: "+actual.length);
            printError(expected,actual);
            assert(false);
        }
        for(int i=0;i<expected.length;i++){
            if(expected[i]!=actual[i]){
                printError(expected,actual);
                assert(false);
            }
        }
    }

    private void printError(char[] expected, char[] actual){
        String toPrint = "Expected:";
        toPrint += "\n\t"+dump(expected).replace("\n","\n\t");
        toPrint += "\nActual:";
        toPrint += "\n\t"+dump(actual).replace("\n","\n\t");
        System.err.println(toPrint);
    }


    private static final Lexer lexer = new Lexer();
    private static final Parser parser = new Parser();
    private static final Assembler assembler = new Assembler();
    protected char[] compile(String input){

        try {
            return assembler.assemble(parser.parse(lexer.lex(input)));
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return new char[0];
    }
}
