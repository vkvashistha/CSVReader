package in.geofriend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class CSVReader {
    public static DataFrame readCSV(String filePath) throws IOException {
        return new DataFrame(readString(filePath), ',');
    }

    public static DataFrame readTSV(String filePath) throws IOException {
        return new DataFrame(readString(filePath), '\t');
    }

    private static String readString(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Objects.requireNonNull(CSVReader.class.getClassLoader()).getResourceAsStream(filename))));
        String line = null;
        StringBuilder builder = new StringBuilder();
        while((line = reader.readLine()) != null) {
            builder.append(line).append("\n");
        }
        reader.close();
        return builder.toString();
    }
}
