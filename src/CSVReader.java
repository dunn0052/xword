import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    public static List<String>  CSVList(String path) {
        String csvFile = path;
        String line = "";
        List<String> words = new ArrayList<>();//dynamic array for ease
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	br.readLine();//skip first line
            while ((line = br.readLine()) != null) {
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
}