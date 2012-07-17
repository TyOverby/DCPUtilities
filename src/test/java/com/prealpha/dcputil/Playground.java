package com.prealpha.dcputil;

import com.prealpha.dcputil.assembler.Builder;
import com.prealpha.dcputil.assembler.CompilerTest;
import com.prealpha.dcputil.data.Pack;
import com.prealpha.dcputil.info.Operator;
import com.prealpha.dcputil.info.Value;
import org.junit.Test;

import static com.prealpha.dcputil.util.PrintUtilities.*;

/**
 * User: Ty
 * Date: 7/9/12
 * Time: 5:22 AM
 */
public class Playground extends CompilerTest{
    @Test
    public void test(){
        String input = ":test\nSET PC test";
        System.out.println(dump(compile(input)));
    }
}
