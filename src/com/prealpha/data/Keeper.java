package com.prealpha.data;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 8:46 AM
 */
public class Keeper {
    private List<Pack> packList = new ArrayList<Pack>(50);

    public Pack get(String identifier){
        for(Pack p: packList){
            if(p.identifier.toLowerCase().equals(identifier)){
                return p;
            }
        }
        return null;
    }
    public Pack get(char code){
        for(Pack p: packList){
            if(p.code==code){
                return p;
            }
        }
        return null;
    }
    
    public void add(Pack pack){
        packList.add(pack);
    }

    public void add(String Identifier, char code, Pack.Type type){
        packList.add(new Pack(Identifier,code,type));
    }

}
