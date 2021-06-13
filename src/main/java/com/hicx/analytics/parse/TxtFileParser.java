package com.hicx.analytics.parse;

import com.hicx.analytics.model.FileStatistic;

import java.io.*;
import java.nio.file.Path;

public class TxtFileParser implements FileParseStrategy {

    @Override
    public FileStatistic parse(Path path) {
        int words = 0;
        int specialCharacters = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toString()))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] wordArray = line.split(" ");
                for (String word : wordArray) {
                    if (!word.isBlank()) {
                        words++;
                    }
                    for (char c : word.toCharArray()) {
                        if (SPECIAL_CHARACTERS.contains(String.valueOf(c))) {
                            specialCharacters++;
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new ParseException("Unable to read file " + path, e);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FileStatistic(words, specialCharacters);
    }

}
