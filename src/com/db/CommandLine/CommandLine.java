package com.db.CommandLine;

import com.db.models.Estate;
import com.db.models.EstateAgent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by saftophobia on 4/21/15.
 */
public class CommandLine {
    EstateAgent estateAgent;

    public CommandLine() {}


    public void p(String s){
        System.out.println(s);
    }




    public void SignIn(){
        do{
        p("Please insert your username");
        String username = readString();

        p("Please insert your password");
        String password = readString();

        //check username and password;
        try{
            EstateAgent estateAgent = EstateAgent.load(username, password);
            this.setEstateAgent(estateAgent);
            p("Logged in Success!");
            break;
        }catch(Exception e){
            p("Please try again");
        }}while(true);

        MainMenuSignedIn();

    }




    public void MainMenuSignedOut(){
        p("Welcome to our System!");
        p("Please sign in!");

        SignIn();
        p(readString());

    }

    public void MainMenuSignedIn(){
        do {
            p("Please select one of the following Options:");
            p("1. Create House");
            p("2. Update House");
            p("3. Delete House");
            p("");
            p("4. Create Apartment");
            p("5. Update Apartment");
            p("6. Delete Apartment");

            p("0. Press to Exit");

            String Output = readString();
            if (Output.equalsIgnoreCase("0")){
                break;
            }
            switch (Output) {
                case "1":
                    CreateHouse();
                    break;
            }
        }while(true);


    }

    public void CreateHouse(){

            try {
                p("Please enter the following data:");

                p("City:");
                String City = readString();

                p("postalCode:");
                String postalCode = readString();

                p("street:");
                String street = readString();

                p("streetNumber:");
                String streetNumber = readString();

                p("Square Area:");
                String squareArea = readString();

                p("floors:");
                String floors = readString();

                p("price:");
                String price = readString();

                p("garden?:");
                String garden = readString();

                this.getEstateAgent().CreateHouse(City,Integer.parseInt(postalCode),street,Integer.parseInt(streetNumber),Integer.parseInt(squareArea),Integer.parseInt(floors),Integer.parseInt(price),Boolean.parseBoolean(garden));
            }catch(Exception e)
            {
                p("Error has occured while saving the house, please try again later!");

            }




    }


    public String readString() {
        String ret = null;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

        try {
            ret = stdin.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }


    public EstateAgent getEstateAgent() {
        return estateAgent;
    }

    public void setEstateAgent(EstateAgent estateAgent) {
        this.estateAgent = estateAgent;
    }
}
