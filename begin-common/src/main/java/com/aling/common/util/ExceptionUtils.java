package com.aling.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author fugl
 * @version v1.0
 * @date 2019/5/21 8:39
 */
public class ExceptionUtils {
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(e);
    }


    public static String getStackTraceAsString(Exception e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
