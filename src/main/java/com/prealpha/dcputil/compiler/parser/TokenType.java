package com.prealpha.dcputil.compiler.parser;

import com.prealpha.dcputil.compiler.lexer.Token;

import java.util.regex.Pattern;

/**
 * User: Ty
 * Date: 8/2/12
 * Time: 9:01 PM
 */
    public enum TokenType{


        OPERATION(Parser.operation),
        REGISTER(Parser.register),
        POINTER_REGISTER(Parser.pointerRegister),
        POINTER_REGISTER_PLUS_NEXT(Parser.pointerRegisterPlusNext),
        STACK_OPERATIONS(Parser.stackOperations),
        POINTER_NEXT_PLUS_REGISTER(Parser.pointerNextPlusRegister),
        LITERAL(Parser.literal),
        POINTER_NEXT(Parser.pointerNext),
        LABEL_REF(Parser.labelRef),
        POINTER_LABEL_REF(Parser.pointerLabelRef),

        UNKNOWN(Pattern.compile(""));



        private Pattern pattern;
        private TokenType(Pattern pattern){
            this.pattern = pattern;
        }
        public boolean matches(String input){
            return this.pattern.matcher(input).matches();
        }

        public static TokenType getType(Token token){
            for(TokenType t:TokenType.values()){
                if(t.matches(token.orig.trim())){
                    return t;
                }
            }
            return UNKNOWN;
        }

    }
