package com.example.objectop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bir Sınıftan birden fazla obje oluşturulmuyorsa buna SINGLETON Yapısı denir.


/*
        User user = new User(); //Obje
        user.name = "Halil";
        user.job = "Instructer";

        User newUser = new User(); // Obje
        newUser.name = "Ahmet";
        newUser.job = "Musician";
        */
//**************CONSTRUCTOR*****************************************************
        //Constructor = Bir sınıftan bir obje oluşturulduğunda ilk çağrılacak Methoddur.
            User user = new User("Halil", "Instructor");


//*************ENCAPSULATİON******************************************************
        //Encapsulation;
        // burda veriyi değiştirebiliriz ama okutmaya biliriz.
        //Veya değiştirmeyi izin vermeyiz okumasına izin veririz.
        //Hatta ikisini birden yaptımaya biliriz.
        //Bunları da Getter ve Setter methodları ile yaparız.

        musicians musicians = new musicians("James","Guitar", 50);
        //Getter işlemi yaptık ve ekrana yazdırdık.
        //Çıktı : 50
        System.out.println(musicians.getAge());

        //Setter işlemi yaptık ve veriyi değiştirdik.
        //Çıktı: 60
        musicians.setAge(60,"Halil");
        System.out.println(musicians.getAge());

//*************İNHERİTANCE**********************************************************************
        //İnheritans = Miras Alma
        SuperMusician  musician  = new SuperMusician("Lars", "Drums", 55);
        System.out.println(musician.Sing());
        System.out.println(musician.getAge()); // Çıktı 55

//************POLYMORPHİSM***********************************************************************

        //Polymorphism;
        // -- Static Polymorphism
        Mathmatics mathmatics = new Mathmatics();
        //Çıktı: 0 , 8 , 14
        System.out.println(mathmatics.sum());
        System.out.println(mathmatics.sum(5,3));
        System.out.println(mathmatics.sum(5,3,6));

        //Dynamic Polymorphism
        dinamicPolymorphism_Animal animal = new dinamicPolymorphism_Animal();
        animal.Sign(); // Çıktı: Animal Class

        dinamicPolymorphism_Dog dog = new dinamicPolymorphism_Dog();
        dog.test();//Çıktı: Animal Class
        dog.Sign();//Çıktı: Dog Class

//**************ABSTRACT********************************************************

        //Abstract class ın örneği;
        //User Absatract classı extend ettş ve user üzerinden information methoduna ulaştık.
        //Çıktı: I am people.
        User myUser = new User("Atil","Eğitmen");
        System.out.println(user.information());


//************İNTERFACE*************************************************************************

       //Interface
        Piano piano = new Piano();
        piano.brand = "Yamaha";
        piano.digital = true;
        piano.info();

    }
}
