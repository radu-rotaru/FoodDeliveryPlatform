package services.write;

import java.io.BufferedWriter;

public interface Writer<T> {
    void write(BufferedWriter bfWriter);
}
