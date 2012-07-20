package com.prealpha.dcputil.compiler.defaults;

import com.prealpha.dcputil.compiler.assembler.Assembler;
import com.prealpha.dcputil.compiler.lexer.Lexer;
import com.prealpha.dcputil.compiler.parser.Parser;
import com.prealpha.dcputil.compiler.parser.ParserException;
import com.prealpha.dcputil.compiler.preprocessor.PreProcessor;

/**
 * User: Ty
 * Date: 7/18/12
 * Time: 2:49 PM
 */
public class BasicAssembler {
    public PreProcessor preProcessor;
    public Lexer lexer;
    public Parser parser;
    public Assembler assembler;
    public BasicAssembler(){
        this.preProcessor = new PreProcessor();
        this.lexer = new Lexer();
        this.parser = new Parser();
        this.assembler = new Assembler();
    }

    public char[] assemble(String input) throws ParserException {
        return assembler.assemble(parser.parse(lexer.lex(preProcessor.process(input))));
    }


}
