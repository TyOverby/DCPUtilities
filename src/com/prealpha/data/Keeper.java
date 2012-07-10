package com.prealpha.data;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 8:46 AM
 */
public class Keeper<K extends Pack> {
    private List<K> packList = new ArrayList<K>(50);

    public Pack get(String identifier){
        for(Pack p: packList){
            if(p.getIdentifier().toLowerCase().equals(identifier.toLowerCase())){
                return p;
            }
        }
        return null;
    }
    public Pack get(char code){
        for(Pack p: packList){
            if(p.getCode()==code){
                return p;
            }
        }
        return null;
    }
    
    public void add(K pack){
        packList.add(pack);
    }

}
