package com.db.CommandLine;

import com.db.com.db.controllers.Controller;
import com.db.models.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;

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
            p("");
            p("7. Insert Person");
            p("8. Sign contract");
            p("9. List all Contracts");
            p("0. Press to Exit");

            String Output = readString();
            if (Output.equalsIgnoreCase("0")){
                break;
            }
            switch (Output) {
                case "1":
                    CreateHouse();
                    break;
                case "2":
                    UpdateHouse();
                    break;
                case "3":
                    DeleteEstate("House");
                    break;

                case "4":
                    CreateApartment();
                    break;

                case "5":
                    UpdateApartment();
                    break;

                case "6":
                    DeleteEstate("Apartment");
                    break;
                case "7":
                    CreatePerson();
                    break;
                case "8":
                    SignContract();
                    break;
                case "9":
                    ListAllContracts();
                    break;



            }
        }while(true);
    }



    private void SignContract() {
        p("What kind of Estates would you like to buy");
        p("1. House");
        p("2. Apartment");
        String input = readString();
        if(input.equalsIgnoreCase("1"))
        {
            SignPurchaseContract();
        }else if(input.equalsIgnoreCase("2")){

            SignTenancyContract();
        }else{
            p("please insert a proper Number");
        }
    }



    private void SignPurchaseContract(){
        try {
            p("These are the houses available");
            House.index();

            p("Please enter the House ID");
            int Estate_ID = Integer.parseInt(readString());

            p("Make Sure your potential customer is available and Registered");
            boolean empty = Person.index();
            if (empty) {
                p("At least one person should be registered");
            } else {
                p("Please enter the Customer ID");
                int Person_ID = Integer.parseInt(readString());
                PersonEstateRelation per = new PersonEstateRelation(Estate_ID, Person_ID);
                per.save();

                p("Contract Number:");
                int Contract_number = Integer.parseInt(readString());
                p("Place");
                String place = readString();
                p("installments");
                int  installments = Integer.parseInt(readString());
                p("interestRate");
                float interestRate = Float.parseFloat(readString());
                
                PurchaseContract purchaseContract = new PurchaseContract(Contract_number, new java.sql.Date(new java.util.Date().getTime()), place, per.getId(),installments,interestRate);
                purchaseContract.save();
                p("SUCCESS!");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            p("Something went wrong, please try again later!");
        }

    }

    private void SignTenancyContract(){
        try {
            p("These are the Apartment available");
            Apartment.index();

            p("Please enter the Apartment ID");
            int Estate_ID = Integer.parseInt(readString());

            p("Make Sure your potential customer is available and Registered");
            boolean empty = Person.index();
            if (empty) {
                p("At least one person should be registered");
            } else {
                p("Please enter the Customer ID");
                int Person_ID = Integer.parseInt(readString());
                PersonEstateRelation per = new PersonEstateRelation(Estate_ID, Person_ID);
                per.save();

                p("Contract Number:");
                int Contract_number = Integer.parseInt(readString());
                p("Place");
                String place = readString();
                Date start_date = new java.sql.Date(new java.util.Date().getTime());
                p("Duration");
                int  duration = Integer.parseInt(readString());
                p("Additional Costs: ");
                int  add_costs = Integer.parseInt(readString());

                TenancyContract tenancyContract = new TenancyContract(Contract_number, new java.sql.Date(new java.util.Date().getTime()), place, per.getId(),start_date,duration, add_costs);

                tenancyContract.save();
                p("SUCCESS!");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
            p("Something went wrong, please try again later!");
        }

    }

    private void ListAllContracts()
    {
        p("List of Purchase Contracts: ");
        PurchaseContract.index();
        p("List of Tenancy Contracts: ");
        TenancyContract.index();
    }


    public void CreatePerson(){

        try {
            p("Please enter the following data:");

            p("First Name:");
            String FirstName = readString();

            p("Name:");
            String Name = readString();

            p("Address:");
            String Address = readString();

            Person p = new Person(FirstName, Name, Address);
            p.save();
            p("Person Created Successfully! ---*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        }catch(Exception e)
        {
            p("Error has occured while saving the Person, please try again later!");

        }
    }


    public void UpdateHouse()
    {
        try {
            p("These are the housess available");
            House.index();

            p("Please select the ID of the house you would like to update");
            int id = Integer.parseInt(readString());

            House c = House.load(House.returnID(id));

            p("Please update the following data:");


            p("Current City: " + c.getCity());
            String input = readString();
            c.setCity(input.equalsIgnoreCase("") ? c.getCity() : input);


            p("Current postalCode: " + c.getPostalCode());
            input = readString();
            c.setPostalCode(input.equalsIgnoreCase("") ? c.getPostalCode() :
                    Integer.parseInt(input));

            p("Current street: " + c.getStreet());
            input = readString();
            c.setStreet(input.equalsIgnoreCase("") ? c.getStreet() : input);


            p("Current streetNumber: " + c.getStreetNumber());
            input = readString();
            c.setStreetNumber(input.equalsIgnoreCase("") ? c.getStreetNumber() : Integer.parseInt(input));


            p("Current Square Area: " + c.getSquareArea());
            input = readString();
            c.setSquareArea(input.equalsIgnoreCase("") ? c.getSquareArea() : Integer.parseInt(input));


            p("Current floors: " + c.getFloors());
            input = readString();
            c.setFloors(input.equalsIgnoreCase("") ? c.getFloors() : Integer.parseInt(input));


            p("Current price: " + c.getPrice());
            input = readString();
            c.setPrice(input.equalsIgnoreCase("") ? c.getPrice() : Integer.parseInt(input));


            p("Current garden?: " + c.isGarden());
            input = readString();
            c.setGarden(input.equalsIgnoreCase("") ? c.isGarden() : Boolean.parseBoolean(input));

            c.save();
            p("UPDATE IS FINISHED SUCCESSFULLY!");

        }catch(Exception e){
            e.printStackTrace();
            p("UPDATE FAILED, please try again later");
        }

    }

    public void UpdateApartment()
    {
        try {
            p("These are the housess available");
            Apartment.index();

            p("Please select the ID of the Apartment you would like to update");
            int id = Integer.parseInt(readString());

            Apartment c = Apartment.load(Apartment.returnID(id));

            p("Please update the following data:");


            p("Current City: " + c.getCity());
            String input = readString();
            c.setCity(input.equalsIgnoreCase("") ? c.getCity() : input);


            p("Current postalCode: " + c.getPostalCode());
            input = readString();
            c.setPostalCode(input.equalsIgnoreCase("") ? c.getPostalCode() :
                    Integer.parseInt(input));

            p("Current street: " + c.getStreet());
            input = readString();
            c.setStreet(input.equalsIgnoreCase("") ? c.getStreet() : input);


            p("Current streetNumber: " + c.getStreetNumber());
            input = readString();
            c.setStreetNumber(input.equalsIgnoreCase("") ? c.getStreetNumber() : Integer.parseInt(input));


            p("Current Square Area: " + c.getSquareArea());
            input = readString();
            c.setSquareArea(input.equalsIgnoreCase("") ? c.getSquareArea() : Integer.parseInt(input));


            p("Current floor: " + c.getFloor());
            input = readString();
            c.setFloor(input.equalsIgnoreCase("") ? c.getFloor() : Integer.parseInt(input));


            p("Current Rent: " + c.getRent());
            input = readString();
            c.setRent(input.equalsIgnoreCase("") ? c.getRent() : Integer.parseInt(input));

            p("Current rooms: " + c.getRooms());
            input = readString();
            c.setRooms(input.equalsIgnoreCase("") ? c.getRooms() : Integer.parseInt(input));


            p("Current balcony?: " + c.isBalcony());
            input = readString();
            c.setBalcony(input.equalsIgnoreCase("") ? c.isBalcony() : Boolean.parseBoolean(input));

            p("Current garden?: " + c.isKitchen());
            input = readString();
            c.setKitchen(input.equalsIgnoreCase("") ? c.isKitchen() : Boolean.parseBoolean(input));


            c.save();
            p("UPDATE IS FINISHED SUCCESSFULLY!");

        }catch(Exception e){
            e.printStackTrace();
            p("UPDATE FAILED, please try again later");
        }

    }
    public void DeleteEstate(String Estate){

        try {
            p("These are the " + Estate+"s available");
            if (Estate.equalsIgnoreCase("House")){House.index();}else{Apartment.index();};

            p("Please select the ID of the "+ Estate+" you would like to delete");
            int id = Integer.parseInt(readString());
            Controller.delete("Estate",id);
            p(Estate + " Deleted Successfully! ---*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
        }catch(Exception e)
        {
            p("Error has occured while Deleting the " + Estate + ", please try again later!");
        }
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

                this.getEstateAgent().CreateHouse(City, Integer.parseInt(postalCode), street, Integer.parseInt(streetNumber), Integer.parseInt(squareArea), Integer.parseInt(floors), Integer.parseInt(price), Boolean.parseBoolean(garden));
                p("Appartment Created Successfully! ---*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
            }catch(Exception e)
            {
                p("Error has occured while saving the house, please try again later!");

            }
    }

    public void CreateApartment(){

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

            p("floor:");
            String floor = readString();
            p("rent:");
            String rent = readString();
            p("rooms:");
            String rooms = readString();
            p("balcony?:");
            String balcony = readString();
            p("kitchen?:");
            String kitchen = readString();


            this.getEstateAgent().CreateApartment(City, Integer.parseInt(postalCode), street, Integer.parseInt(streetNumber), Integer.parseInt(squareArea),Integer.parseInt(floor), Integer.parseInt(rent),
                    Integer.parseInt(rooms), Boolean.parseBoolean(balcony),Boolean.parseBoolean(kitchen));
            p("House Created Successfully! ---*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-");
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
