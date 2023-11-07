package com.example.demo;

import com.example.demo.PasswordException;
import com.example.demo.UserNameException;


public class Test {
    public String name = "admin";
    public String password = "admin";

    public void login(String name, String password) throws UserNameException {
        if(!this.name.equals(name)) {
            throw new UserNameException("您的用户名错误~");
        }
        if(!this.password.equals(password)) {
            throw new PasswordException();
        }

    }
    public static void main(String[] args){
        Test test = new Test();
        try {
            test.login("admin2", "admin");
        } catch (UserNameException e) {
            e.printStackTrace();
            System.out.println("用户名异常");
        } catch (PasswordException e)  {
            e.printStackTrace();
            System.out.println("密码异常");
        } finally {

        }
    }
}