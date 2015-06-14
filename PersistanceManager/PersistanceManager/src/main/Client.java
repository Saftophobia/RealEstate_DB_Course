package main;


import java.util.UUID;

/**
 * Created by saftophobia on 6/10/15.
 */
public class Client implements Runnable{

    private int page_id;
    private int client_id;
    private UUID transaction_id;

    public Client(int client_id){
        this.client_id = client_id;
        this.transaction_id = PersistanceManager.beginTransaction();
    }


    @Override
    public void run() {
        int random_iterations = (int) (Math.random() * 200);
        for (int i = 0 ; i < random_iterations; i++)
        {
            this.page_id = client_id*10 + (int)(Math.random()*10);
            PersistanceManager.write(this.transaction_id, this.page_id, "User " + client_id + " is saying Hi at " + System.currentTimeMillis(),true);
            try {
                Thread.sleep((long)(Math.random()*1000));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        PersistanceManager.commitPushDB(this.transaction_id);
    }


}
