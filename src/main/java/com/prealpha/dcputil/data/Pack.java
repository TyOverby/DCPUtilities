package com.prealpha.dcputil.data;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 8:47 AM
 */
public abstract class Pack {
    public abstract String getIdentifier();
    public abstract char getCode();
    public abstract int getCycles();

    @Override
    public boolean equals(Object other){
        if(other instanceof Pack){
            return(((Pack)other).getIdentifier().equals(this.getIdentifier()));
        }
        return false;
    }
}
