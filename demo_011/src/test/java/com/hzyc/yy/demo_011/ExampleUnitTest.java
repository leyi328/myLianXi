package com.hzyc.yy.demo_011;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);


        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("张三");
        stringBuffer.append("李四");
        stringBuffer.append("王五");

        System.out.print(stringBuffer.toString());

    }
}