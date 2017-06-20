package com.core;

/**
 * Created by agaspari on 6/7/2017.
 */
class Person {
    private String firstName, lastName, email, number;
    Person(String firstName, String lastName, String email, String number){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
    }

    String getFirstName(){
        return firstName;
    }

    String getLastName(){
        return lastName;
    }

    String getEmail(){
        return email;
    }

    String getNumber(){
        return number;
    }

    void setFirstName(String firstName){
        this.firstName = firstName;
    }

    void setLastName(String lastName){
        this.lastName = lastName;
    }

    void setEmail(String email){
        this.email = email;
    }

    void setNumber(String number){
        this.number = number;
    }
}
