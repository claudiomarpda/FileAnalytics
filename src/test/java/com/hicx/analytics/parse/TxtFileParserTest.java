package com.hicx.analytics.parse;

import com.hicx.analytics.model.FileStatistic;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TxtFileParserTest {

    private static final String PATH = "src/test/resources/";

    @Test
    void whenParseEmptyThenStatisticMatches() {
        FileStatistic fileStatistic = new TxtFileParser().parse(Path.of(PATH + "empty.txt"));
        assertEquals(0, fileStatistic.getWords());
        assertEquals(0, fileStatistic.getSpecialCharacters());
    }

    @Test
    void whenParseBlankThenStatisticMatches() {
        FileStatistic fileStatistic = new TxtFileParser().parse(Path.of(PATH + "blank.txt"));
        assertEquals(0, fileStatistic.getWords());
        assertEquals(0, fileStatistic.getSpecialCharacters());
    }

    @Test
    void whenParse3Words0SpecialThenStatisticMatches() {
        FileStatistic fileStatistic = new TxtFileParser().parse(Path.of(PATH + "3words0specialchars.txt"));
        assertEquals(3, fileStatistic.getWords());
        assertEquals(0, fileStatistic.getSpecialCharacters());
    }

    @Test
    void whenParse7Words5SpecialThenStatisticMatches() {
        FileStatistic fileStatistic = new TxtFileParser().parse(Path.of(PATH + "7words5specialchars.txt"));
        assertEquals(7, fileStatistic.getWords());
        assertEquals(5, fileStatistic.getSpecialCharacters());
    }

    @Test
    void whenParse52words6SpecialThenStatisticMatches() {
        FileStatistic fileStatistic = new TxtFileParser().parse(Path.of(PATH + "52words6specialchars.txt"));
        assertEquals(52, fileStatistic.getWords());
        assertEquals(6, fileStatistic.getSpecialCharacters());
    }

}