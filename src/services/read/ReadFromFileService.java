package services.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadFromFileService {
    private static ReadFromFileService instance = null;

    private ReadFromFileService(){}

    public static ReadFromFileService getInstance() {
        if(instance == null)
            instance = new ReadFromFileService();

        return instance;
    }

    public void read(Reader readFile, String fileName) {
        try {
            File csvFile = new File(fileName);
            if (!csvFile.createNewFile()) {
                String obj;
                BufferedReader fileReader = new BufferedReader(new FileReader(fileName));

                while((obj = fileReader.readLine()) != null) {
                    String[] attrs = obj.split(",");
                    readFile.read(attrs);
                }
            }
        }
        catch (IOException e) {
            System.out.println("Error while reading from csv file.");
        }
    }
}
