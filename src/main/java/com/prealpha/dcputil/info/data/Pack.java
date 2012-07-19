package com.prealpha.dcputil.info.data;

import com.prealpha.dcputil.util.PrintUtilities;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 8:47 AM
 */
public abstract class Pack {
    public abstract String[] getIdentifiers();
    public abstract char getCode();
    public abstract int getCycles();
    public abstract Pack clone();

    public int lineNum = -1;

    @Override
    public boolean equals(Object other){
        if(other instanceof Pack){
            return(((Pack) other).getCode()==this.getCode());
        }
        return false;
    }

    public boolean is(String keyword){
        for(String s:getIdentifiers()){
            if(s.toUpperCase().equals(keyword.toUpperCase())){
                return true;
            }
        }
        return false;
    }

    public Pack setLineNum(int line){
        this.lineNum = line;
        return this;
    }

    @Override
    public String toString(){
        String start = "[";
        for(String s:getIdentifiers()){
            start+=s;
        }
        start +="] "+ PrintUtilities.convertHex(getCode());

        return start;
    }
}
