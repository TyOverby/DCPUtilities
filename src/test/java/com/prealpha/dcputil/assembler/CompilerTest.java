package com.prealpha.dcputil.assembler;

import com.prealpha.dcputil.assembler.parser.Parser;
import com.prealpha.dcputil.assembler.parser.ParserException;

import static com.prealpha.dcputil.util.PrintUtilities.dump;
import static junit.framework.Assert.assertEquals;

/**
 * User: Ty
 * Date: 7/17/12
 * Time: 5:59 AM
 */
public class CompilerTest {


    protected void assertEqual(char[] expected, char[] actual){
        if(expected.length!=actual.length){
            System.err.println("Expected length: "+expected.length+"  Actual length: "+actual.length);
            assert(false);
        }
        for(int i=0;i<expected.length;i++){
            if(expected[i]!=actual[i]){
                String toPrint = "Expected:";
                toPrint += "\n\t"+dump(expected).replace("\n","\n\t");
                toPrint += "\nActual:";
                toPrint += "\n\t"+dump(actual).replace("\n","\n\t");
                System.err.println(toPrint);
                assert(false);
            }
        }
    }

    protected char[] compile(String input){
        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        Assembler assembler = new Assembler();
        try {
            return assembler.assemble(parser.parse(lexer.lex(input)));
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return new char[0];
    }
}
