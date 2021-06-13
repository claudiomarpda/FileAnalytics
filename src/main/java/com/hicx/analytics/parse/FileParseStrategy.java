package com.hicx.analytics.parse;

import com.hicx.analytics.model.FileStatistic;

import java.nio.file.Path;

public interface FileParseStrategy {

    String SPECIAL_CHARACTERS = "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";

    FileStatistic parse(Path path);

}
