package com.prealpha.dcputil.assembler;

import com.prealpha.dcputil.data.Pack;
import com.prealpha.dcputil.info.Operator;
import com.prealpha.dcputil.info.Value;

import java.util.List;

/**
 * User: Ty
 * Date: 7/17/12
 * Time: 7:31 AM
 */
public class AssemblerWarning {
    private static final String[] math = {"ADD","SUB","MUL","MLI","DIV","DVI","MOD","MDI"};
    private static final String[] ifchecks = {"IFB", "IFC", "IFE", "IFN", "IFG", "IFA", "IFL", "IFU"};
    public final String message;
    public final String suggestion;
    public final int lineNum;

    public AssemblerWarning(String message, String suggestion, int lineNum){
        this.message = message;
        this.lineNum = lineNum;
        this.suggestion = suggestion;
    }

    public static void checkWarningBasic(Operator.OperatorPack op, Value.ValuePack b, Value.ValuePack a, int lineNum, List<AssemblerWarning> wl){
        // Check all "SET" operators
        if(isit(op,math)){
            if(b.is("EX")){
                warn(wl, "EX is set", lineNum);
            }
            if(b.is("literal")||b.is("next")){
                warn(wl,"Setting a literal does nothing","Remove line",lineNum);
            }
        }

        if(isit(op,ifchecks)){
            if((b.is("literal")||b.is("next"))&&(a.is("literal")||a.is("next"))){
                warn(wl,"Checking equality with two literals is a waste", "Remove line", lineNum);
            }
        }

        // Check specifics
    }
    public static void checkWarningValues(Operator.OperatorPack op, Character b, Character a, int lineNum, List<AssemblerWarning> wl){
        if(a!=null){
            if(isit(op,"ADD","SUB")){
                if(a==0){
                    warn(wl,"Adding or subtracting by 0 does nothing.","Remove line",lineNum);
                }
            }
            if(isit(op,"")){
                if(a==0){
                    warn(wl,"Multiplying or dividing by 0 results in 0","Replace with\"SET something 0\"",lineNum);
                }
            }
            if(op.is("MOD")||op.is("MDI")){
                if(a==0){
                    warn(wl,"Doing modulus 0 results in 0","Replace with\"SET something 0",lineNum);
                }
            }
        }
    }
    private static boolean isit(Pack pack, String... options){
        for(String s:options){
            if(pack.is(s)){
                return true;
            }
        }
        return false;
    }
    private static void warn(List<AssemblerWarning> warningList, String message, String suggestion, int lineNum){
        warningList.add(new AssemblerWarning(message,suggestion,lineNum));
    }
    private static void warn(List<AssemblerWarning> warningList, String message, int lineNum){
        warningList.add(new AssemblerWarning(message,null,lineNum));
    }
}
