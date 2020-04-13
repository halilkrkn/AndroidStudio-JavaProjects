package com.example.objectop;

public class Piano implements houseDecor_Interface,Instrument_Interface {
    String brand;
    boolean digital;

    @Override
    public void info() {
        System.out.println("override method");
    }
}
