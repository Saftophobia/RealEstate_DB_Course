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
        new Apartment("cairo",123,"hades",5,25,3,1,232,true,false).save();
        new Person("ahmed","elsafty","holsten").save();
        new TenancyContract(231, new java.sql.Date(new java.util.Date().getTime()),"alex",new java.sql.Date(new java.util.Date().getTime()), 23,900).save();
        new PurchaseContract(9001,new java.sql.Date(new java.util.Date().getTime()),"hamburg",12,5).save();
        new Estate("boor",25523,"wazaastreet",2,25).save();
        new EstateAgent("agency","holer","root","root").save();


        System.out.println(Model.delete("Person", 1));

    }
}
