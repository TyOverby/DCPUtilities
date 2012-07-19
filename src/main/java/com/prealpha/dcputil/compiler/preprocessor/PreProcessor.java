package com.prealpha.dcputil.compiler.preprocessor;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Ty
 * Date: 7/18/12
 * Time: 2:24 AM
 */
public class PreProcessor {
    private List<Rule> rulesList = new ArrayList<Rule>();

    public void addRule(Rule rule){
        this.rulesList.add(rule);
    }
    public void removeRule(Rule rule){
        this.rulesList.remove(rule);
    }

    public String process(String input){
        for(Rule rule:rulesList){
            input = rule.process(input);
        }
        return input;
    }
}
