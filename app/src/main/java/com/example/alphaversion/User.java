package com.example.alphaversion;


/**
 @author Ilai Shimoni ilaishimoni@gmail.com
 @version 1.0
 @since 3/11/22
 definition of User class which consists of an object with special attributes, builder etc...
 */

public class User {

    public String FullName, Age, Email, Password;

    public User(){

    }
    public User(String FullName,String Age, String Email, String Password){
        this.FullName = FullName;
        this.Age = Age;
        this.Email = Email;
        this.Password = Password;
    }
}
