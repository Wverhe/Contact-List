package core;

/**
 * Created by agaspari on 6/7/2017.
 */
public class Person {
    private String firstName, lastName, email, number;
    public Person(String firstName, String lastName, String email, String number){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public String getEmail(){
        return email;
    }

    public String getNumber(){
        return number;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setNumber(String number){
        this.number = number;
    }
}
