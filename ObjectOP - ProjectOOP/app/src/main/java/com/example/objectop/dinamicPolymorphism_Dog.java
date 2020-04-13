package com.example.objectop;

//Dinamic Polymorphism
public class dinamicPolymorphism_Dog extends  dinamicPolymorphism_Animal {


    public void test() {
        super.Sign();
    }


    public void Sign() {
        System.out.println("Dog Class");
    }
}
