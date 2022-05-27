package services.write;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFileService<T extends Writer> {
    private static WriteToFileService instance = null;

    private WriteToFileService() {}

    public static WriteToFileService getInstance() {
        if(instance == null)
            instance = new WriteToFileService();

        return instance;
    }

    public void write(T obj, String fileName) {
        try {
            File csvFile = new File(fileName);

            if(!csvFile.exists())
                csvFile.createNewFile();

            FileWriter fileWriter = new FileWriter(csvFile, true);
            BufferedWriter bfWriter = new BufferedWriter(fileWriter);
            obj.write(bfWriter);
            bfWriter.flush();
            bfWriter.close();
        }
        catch (IOException e) {
            System.out.println("Error while writing to file");
        }
    }
}
