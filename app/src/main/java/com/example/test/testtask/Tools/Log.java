package com.example.test.testtask.Tools;

public class Log {
    final private static String ERROR_TAG = "myLog_error";
    final private static String DEBUG_TAG = "myLog_debug";

    public static void d(String text){
        android.util.Log.d(DEBUG_TAG, text);
    }

    public static void e(String text){
        android.util.Log.d(ERROR_TAG, text);
    }

    public static void e(String text, Throwable e){
        android.util.Log.d(ERROR_TAG, text, e);
    }

    public static void e(Throwable e){
        android.util.Log.d(ERROR_TAG, "", e);
    }
}
