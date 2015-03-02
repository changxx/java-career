package com.changxx.practice.hash;

import java.lang.reflect.Field;

/**
 * @author changxiangxiang
 * @date 2013年11月12日 下午11:51:43
 * @description
 */
public class HashCodeTest {

    /**
     * Returns a hash code for this string. The hash code for a
     * <code>String</code> object is computed as <blockquote>
     * 
     * <pre>
     * s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
     * </pre>
     * 
     * </blockquote> using <code>int</code> arithmetic, where <code>s[i]</code>
     * is the <i>i</i>th character of the string, <code>n</code> is the length
     * of the string, and <code>^</code> indicates exponentiation. (The hash
     * value of the empty string is zero.)
     * @return a hash code value for this object.
     */
    public static void main(String[] args) {
        String s = "s";
        try {
            Field f = s.getClass().getDeclaredField("count");
            f.setAccessible(true);
            try {
                System.out.println(f.get("count"));
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(s.hashCode());

        int hash = 0;
        int i = 0;
        int length = s.length();
        for (char c : s.toCharArray()) {
            hash = hash * 31 + c;
            // hash += c * Math.pow(31, --length);
        }
        System.out.println(hash);
    }
}
