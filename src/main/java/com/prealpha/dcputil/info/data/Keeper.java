package com.prealpha.dcputil.info.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 8:46 AM
 */
public class Keeper<K extends Pack> implements Iterable<K>{
    private boolean sealed = false;
    private List<K> packList = new ArrayList<K>(50);

    public K get(String identifier){
        for(K p: packList){
            for(String s:p.getIdentifiers()){
                if(s.toUpperCase().equals(identifier.toUpperCase())){
                    return p;
                }
            }

        }
        return null;
    }
    
    public K get(char code){
        for(K p: packList){
            if(p.getCode()==code){
                return p;
            }
        }
        return null;
    }
    
    public void add(K pack){
        if(!sealed){
            packList.add(pack);
        }
    }
    public void seal(){
        this.sealed = true;
    }

    @Override
    public Iterator<K> iterator() {
        return packList.iterator();
    }
}
