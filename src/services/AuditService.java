package services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.sql.Timestamp;

public class AuditService {

    private static AuditService instance = null;

    private AuditService() {}

    public static AuditService getInstance() {
        if(instance == null)
            instance = new AuditService();

        return instance;
    }

    public void write(String action) {
        try {
            File file = new File("audit.csv");

            if(!file.exists())
                file.createNewFile();

            FileWriter fileWr = new FileWriter(file, true);
            BufferedWriter bufferedWr = new BufferedWriter(fileWr);

            Timestamp time = new Timestamp(System.currentTimeMillis());

            bufferedWr.write(action + "," + time.toString() + "\n");
            bufferedWr.flush();
            bufferedWr.close();
        }
        catch (IOException e) {
            System.out.println("Error while writing to file");
        }
    }

}
