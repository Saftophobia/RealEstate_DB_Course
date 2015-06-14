package main;

import db.DBConnectionManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Collection;
import java.util.Scanner;
import java.util.UUID;

/**
 * Created by saftophobia on 6/10/15.
 */
public class LogManager  {

    public synchronized  static void writelog(int page_id, UUID transaction_id, String transaction_type, String data){
        File logFile = new File("files/" + transaction_id.toString() + ".txt");

        try{
            if(!logFile.exists()){
                logFile.createNewFile();
            }
            FileWriter fw = new FileWriter(logFile,true); //true for append

            fw.write(page_id + "," + transaction_type + "," + data + "\n");
            fw.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static synchronized void Redo(File file) throws FileNotFoundException {
        Scanner sn = new Scanner(file);

        while (sn.hasNext()) {
            String line = sn.nextLine();
            if(line.contains("newtransaction")){
                continue;
            }
            if(line.contains("COMMIT")){
                PersistanceManager.commitPushDB(java.util.UUID.fromString(
                        file.getName().substring(0,file.getName().length()-4)));
                break;
            }
            if(line.contains("WRITE")){
                String page_id = line.split(",")[0];
                String data = line.split(",")[2];
                PersistanceManager.write(java.util.UUID.fromString(
                        file.getName().substring(0,file.getName().length()-4)), Integer.parseInt(page_id),data,false);

            }

        }
        sn.close();

    }




    public static synchronized void readlog(){
        try {
            File dir = new File("files/");
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    //Scanner scanner = new Scanner(child);
                    if (FileUtils.readFileToString(child).contains("COMMIT") &&
                            !FileUtils.readFileToString(child).contains("PUSHED")) {
                        System.out.println("NEW FILE!");
                        Redo(child);
                    }else{
                        child.delete();
                    }
                }
            } else {
                System.out.println("NOT A DIRECTORY!");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static synchronized void deletelog(UUID transaction_id) {
        try {
            File logFile = new File("files/" + transaction_id.toString() + ".txt");
            System.out.println("File Deleted!");
            logFile.delete();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}