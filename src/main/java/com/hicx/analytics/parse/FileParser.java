package com.hicx.analytics.parse;

import com.hicx.analytics.model.FileExtension;
import com.hicx.analytics.model.FileStatistic;

import java.nio.file.Path;

public class FileParser {

    private final FileExtensionParse fileExtensionParse;

    public FileParser(FileExtensionParse fileExtensionParse) {
        this.fileExtensionParse = fileExtensionParse;
    }

    public FileStatistic parse(Path path) {
        FileExtension fileExtension = fileExtensionParse.parse(path.getFileName().toString());

        FileParseStrategy fileParserStrategy;
        if (FileExtension.TXT.equals(fileExtension)) {
            fileParserStrategy = new TxtFileParser();
        } else {
            throw new ParseException("Unsupported file extension for file " + path);
        }

        return fileParserStrategy.parse(path);
    }
}
