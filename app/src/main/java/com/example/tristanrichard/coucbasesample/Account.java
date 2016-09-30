package com.example.tristanrichard.coucbasesample;

/**
 * Created by tristanrichard on 30/09/2016.
 */

public class Account {

    private String firstName;
    private String lastName;
    private String education;
    private int age;
    private String country;

    public Account(String firstName, String lastName, String education, int age, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.education = education;
        this.age = age;
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
