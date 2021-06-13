package com.hicx.analytics.parse;

import com.hicx.analytics.model.FileExtension;
import org.apache.commons.io.FilenameUtils;

public class ApacheFileExtensionParser implements FileExtensionParse {

    @Override
    public FileExtension parse(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        for (FileExtension fe : FileExtension.values()) {
            if (fe.toString().equalsIgnoreCase(extension)) {
                return fe;
            }
        }
        return FileExtension.UNKNOWN;
    }
}
