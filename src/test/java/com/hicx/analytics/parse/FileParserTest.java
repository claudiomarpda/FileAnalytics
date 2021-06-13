package com.hicx.analytics.parse;

import com.hicx.analytics.model.FileExtension;
import com.hicx.analytics.model.FileStatistic;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class FileParserTest {

    private final FileExtensionParse fileExtensionParse = mock(FileExtensionParse.class);
    private final FileParser fileParser = new FileParser(fileExtensionParse);

    @Test
    void whenParseTxtExtensionThenRunTxtStrategy() {
        when(fileExtensionParse.parse(anyString())).thenReturn(FileExtension.TXT);
        FileStatistic fileStatistic = fileParser.parse(Path.of(("src/test/resources/3words3specialchars.txt")));
        assertNotNull(fileStatistic);
    }


    @Test
    void whenParseUnknownFileExtensionThenThrowParseException() {
        when(fileExtensionParse.parse(anyString())).thenReturn(FileExtension.UNKNOWN);
        assertThrows(ParseException.class, () ->
            fileParser.parse(Path.of(("src/test/resources/3words3specialchars.unknown"))));
    }

}