package com.hicx.analytics;

import com.hicx.analytics.monitor.DirectoryMonitor;
import com.hicx.analytics.monitor.LocalDirectoryMonitor;
import com.hicx.analytics.parse.ApacheFileExtensionParser;
import com.hicx.analytics.parse.FileParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FileAnalyticsApp {

    private static final Logger LOGGER = LogManager.getLogManager().getLogger(FileAnalyticsApp.class.getName());
    private static final String PROCESSED = "processed";

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("The following arguments are necessary: directoryPath pollTimeoutInMilliSeconds");
            System.exit(0);
        }

        Path directoryPath = Paths.get(args[0]);
        createProcessedDirectory(directoryPath);
        long pollTimeoutInMilliSeconds = Long.parseLong(args[1]);

        try {
            DirectoryMonitor directoryMonitor = new LocalDirectoryMonitor(
                    directoryPath,
                    pollTimeoutInMilliSeconds,
                    TimeUnit.MILLISECONDS,
                    PROCESSED,
                    new FileParser(new ApacheFileExtensionParser()));
            directoryMonitor.start();

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private static void createProcessedDirectory(Path directoryPath) throws IOException {
        Path processedPath = Path.of(directoryPath + File.separator + PROCESSED);
        if (!Files.exists(processedPath)) {
            Path processedPathResult = Files.createDirectory(processedPath);
            if (!Files.exists(processedPathResult)) {
                LOGGER.severe("Unable to create directory for processed files in " + directoryPath);
                System.exit(0);
            }
        }
    }

}
