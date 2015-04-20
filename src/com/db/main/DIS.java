package com.db.main;

import com.db.models.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by saftophobia on 4/20/15.
 */
public class DIS {

    public static void main(String [] args){
        new EstateAgent("ahmed","holsteiner","iron","stor").save();

        EstateAgent s= EstateAgent.load(1);
        System.out.println(s.getName());
        s.setPassword("gro");
        s.save();


        new Contract(123, new java.sql.Date(new java.util.Date().getTime()),"iron").save();
        new House("cairo", 123,"holer",5,25,3,23,true).save();

        new Person("ahmed","elsafty","holsten").save();

        System.out.println(Model.delete("Person", 1));

    }
}
