package com.project.memorybuzz.models;

public class BirthdayClass {
    public String birthdayName,birthdate;

public BirthdayClass(){

}
    public BirthdayClass(String birthdayName, String birthdate) {
        this.birthdayName = birthdayName;
        this.birthdate = birthdate;


    }

    public String getBirthdayName() {
        return birthdayName;
    }

    public void setBirthdayName(String birthdayName) {
        this.birthdayName = birthdayName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
