package com.example.demo.common;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * 关于密码的工具类，进行加密的时候才会用到
 */
public class PasswordUtils {
    // 1.加盐并生成密码
    public static String encrypt(String password) {
        // 生成 32 位盐值
        String salt = UUID.randomUUID().toString().replace("-", "");

        // 生成加盐之后的密码
        String saltPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());

        // 生成最终密码（保存到数据库的密码）【约定格式：32 位盐值 + $ + 32位加盐之后的密码】
        String finalPassword = salt + "$" + saltPassword;
        return finalPassword;
    }

    // 2.生成加盐的密码（方法1的重载）
    public static String encrypt(String password, String salt) {
        // 1.生成一个加盐之后的密码
        String saltPassword = DigestUtils.md5DigestAsHex((salt + password).getBytes());
        // 2.生成最终的密码
        String finalPassword = salt + "$" + saltPassword;
        return finalPassword;
    }

    // 3.验证密码

    /**
     *
     * @param: 用户输入的明文密码
     * @param: 数据库中存储的密码
     * @return: 两个密码是否相同
     */
    public static boolean check(String inputPassword, String finalPassword) {
        if (StringUtils.hasLength(inputPassword) && StringUtils.hasLength(finalPassword) && finalPassword.length() == 65) {
            // 1.得到盐值
            String salt = finalPassword.split("\\$")[0];

            // 2.使用之前加密的步骤，将明文密码和已经得到的盐值进行加密，生成最终的密码
            String confirmPassword = encrypt(inputPassword, salt);

            // 3.对比两个密码是否相同
            return confirmPassword.equals(finalPassword);
        }
        return false;
    }


    // 测试加密算法
    public static void main(String[] args) {
        String password = "12345";
        String finalPassword = encrypt(password);
        System.out.println("第一次加密：" + encrypt(finalPassword));
        // 对比
        String inputPassword = "1234";
        System.out.println("对比 " + inputPassword + " 是否等于 " + password + " -> " + check(inputPassword, finalPassword));

        String inputPassword2 = "12345";
        System.out.println("对比 " + inputPassword2 + " 是否等于 " + password+ " -> " + check(inputPassword2, finalPassword));

    }
}
