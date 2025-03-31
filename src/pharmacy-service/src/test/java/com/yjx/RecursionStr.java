package com.yjx;
//
///**
//public class RecursionStr {
//
//    public static void main(String[] args) {
//        System.out.println("输入abcde");
//        System.out.println("result ===>" + recursionStr("abcde", 0));
//        System.out.println((int)(Math.pow(-2, 3)));
//
//    }
//
//    public static StringBuilder recursionStr(String str, int pos) {
//        if (pos >= str.length()) {
//            return new StringBuilder("");
//        }
//        char alphabet = str.charAt(pos);
//        StringBuilder result = recursionStr(str, pos + 1);
//        result.append(alphabet);
//        return result;
//    }
//
//}

import org.junit.platform.commons.util.StringUtils;

public class RecursionStr {

    public static void main(String[] args) {
        System.out.println("reverse(1534236469) = " + reverse(-1456123));
    }

    public static int reverse(int x) {
        String xString = Integer.toString(x);
        String string = xString;
        int flag = 1;
        if (x < 0) {
            flag = -1;
            string = xString.substring(1);
        }
        try {
            return Integer.valueOf((new StringBuilder(string)).reverse().toString()) * flag;
        }catch (Exception e){
            return 0;
        }
    }
}