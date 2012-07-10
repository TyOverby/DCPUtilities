package com.prealpha.data;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 8:47 AM
 */
public abstract class Pack {
    public enum Type{
        OPCODE,
        VALUE,
        LITERAL,
    }

    public final String identifier;
    public final char code;
    public final Type type;
    public final char value;
    
    public Pack(String identifier, char code, Type type){
        this.identifier = identifier.toLowerCase();
        this.code = code;
        this.type = type;
        
        this.value = 0;
    }
    
    public Pack(String identifier,char code, char value){
        this.identifier = identifier;
        this.code = code;
        this.type = Type.LITERAL;

        this.value = value;

    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Pack){
            return(((Pack)other).identifier.equals(this.identifier));
        }
        return false;
    }
}
