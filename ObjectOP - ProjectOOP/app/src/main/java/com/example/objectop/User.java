package com.example.objectop;

public class User extends People_Abstrat{

    //Property
    String name;
    String job;

    //Constructor;
    // Bir sınıftan bir obje oluşturulduğunda ilk çağrılacak Methoddur.
    // Herbir obje oluşturulduğunda çağrılan bir method,
    public User(String nameInput, String jobInput) {
        this.name = nameInput;
        this.job = jobInput;

        System.out.println("User Created");

    //Encapsulation
        //
    }
}
