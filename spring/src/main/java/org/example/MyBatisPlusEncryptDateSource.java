package org.example;

import com.baomidou.mybatisplus.core.toolkit.AES;

public class MyBatisPlusEncryptDateSource {
//    AES.generateRandomKey();
    private final static String secret = "123456789abcdefg";

    // jav -jar xxx --mpw.key==123456789abcdefg
    public static void main(String[] args) {
        encrypt();
        decrypt();
    }

    public static void decrypt() {
        String str = "e687145195ce6213";
        System.out.println(AES.decrypt(str, secret));
    }
    public static void encrypt() {
        String pwd = "123456";
        System.out.println(AES.encrypt(pwd, secret));
    }
}
