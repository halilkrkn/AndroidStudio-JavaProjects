package com.example.objectop;

//Encapsulation
//Getter ve Setter Methodlarını yazıyoruz.
public class musicians {

   private String name;
   private String instrument;
   private int age;

    public musicians(String name, String  instrument, int age){
        this.name = name;
        this.instrument = instrument;
        this.age = age;
    }

    //Getter  Veriyi okumamıza yarıyor.
    public String getName() {
        return name;
    }

    public String getInstrument() {
        return instrument;
    }

    public int getAge() {
        return age;
    }

    //Setter Veriyi değiştirmemize yarıyor.
    public void setName(String name) {
        this.name = name;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void setAge(int age, String password) {
        if (password == "Halil") {
            this.age = age;
        }


    }
}
