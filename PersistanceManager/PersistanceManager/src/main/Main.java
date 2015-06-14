package main;

import java.rmi.server.UID;
import java.util.UUID;

/**
 * Created by saftophobia on 6/10/15.
 */
public class Main {
    public static void main(String[] args)
    {

        LogManager.readlog();

        Client c1 = new Client(1);
        Thread t1 = new Thread(c1);
        t1.start();

        Client c2 = new Client(2);
        Thread t2 = new Thread(c2);
        t2.start();




    }
}
