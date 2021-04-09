package util;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Log4j
public class Input {
    public static String[] fileToArray(String filename) {
        BufferedReader reader = null;
        List<String> textFromFile = new ArrayList<String>();

        try {
            log.info("Reading .csv file");
            reader = new BufferedReader(new FileReader(filename));
            while (reader.ready()) {
                textFromFile.add(reader.readLine());
            }
            log.info("File read with success");
        } catch (Exception e) {
            log.error("The system cannot access the file specified", e);
        }

        return textFromFile.toArray(new String[0]);
    }
}