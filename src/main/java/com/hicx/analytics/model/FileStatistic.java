package com.hicx.analytics.model;

public class FileStatistic {

    private final int words;
    private final int specialCharacters;

    public FileStatistic(int words, int specialCharacters) {
        this.words = words;
        this.specialCharacters = specialCharacters;
    }

    public int getWords() {
        return words;
    }

    public int getSpecialCharacters() {
        return specialCharacters;
    }

    @Override
    public String toString() {
        return "FileStatistic{" +
                "words=" + words +
                ", specialCharacters=" + specialCharacters +
                '}';
    }
}
