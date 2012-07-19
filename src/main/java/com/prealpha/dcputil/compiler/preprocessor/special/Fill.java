package com.prealpha.dcputil.compiler.preprocessor.special;

import com.prealpha.dcputil.compiler.preprocessor.Rule;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Ty
 * Date: 7/18/12
 * Time: 3:10 PM
 */
public class Fill implements Rule {
    private static final Pattern pattern = Pattern.compile("\\#PAD (\\d+)");

    @Override
    public String process(String input) {
        Matcher matcher = pattern.matcher(input);

        int start = 0;
        while(matcher.find(start)){
            start = matcher.end();
            int num = Integer.parseInt(matcher.group(1));
            String set = "DAT ";
            for(int i=0;i<num;i++){
                set+=" "+0x0000;
            }
            input = matcher.replaceAll(set);

        }
        return input;
    }

    public static void main(String... args){
        Fill fill = new Fill();
        System.out.println(fill.process("#PAD 4"));
    }
}
