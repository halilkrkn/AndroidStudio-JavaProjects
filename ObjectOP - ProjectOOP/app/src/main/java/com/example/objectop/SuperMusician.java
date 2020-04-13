package com.example.objectop;


//Inheritance
public class SuperMusician extends musicians {

    public SuperMusician(String name, String instrument, int age) {
        //Burada super() methodu extends ettiğimiz sınıfa referans veriyoruz.
        super(name, instrument, age);
    }

    public String Sing(){
        return "Azer Bülbül Zoruna Mı Gitti Gardaş";
    }

    }

