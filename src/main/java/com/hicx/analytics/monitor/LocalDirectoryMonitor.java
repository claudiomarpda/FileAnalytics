package com.hicx.analytics.monitor;

import com.hicx.analytics.model.FileStatistic;
import com.hicx.analytics.parse.FileParser;
import com.hicx.analytics.parse.ParseException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

public class LocalDirectoryMonitor implements DirectoryMonitor {

    private static final Logger LOGGER = Logger.getLogger(LocalDirectoryMonitor.class.getName());

    private final long pollTimeOut;
    private final TimeUnit pollTimeUnit;
    private final Path directoryPath;
    private final String processedDirectoryName;
    private final WatchService watcher;
    private final FileParser fileParser;

    public LocalDirectoryMonitor(Path directoryPath,
                                 long pollTimeout,
                                 TimeUnit pollTimeUnit,
                                 String processedDirectoryName,
                                 FileParser fileParser) throws IOException {
        this.directoryPath = directoryPath;
        this.processedDirectoryName = processedDirectoryName;
        this.watcher = FileSystems.getDefault().newWatchService();
        this.pollTimeOut = pollTimeout;
        this.pollTimeUnit = pollTimeUnit;
        this.fileParser = fileParser;
    }

    public void start() {
        LOGGER.info("Started");
        scanExistingFiles();
        monitorCreatedFiles();
    }

    private void scanExistingFiles() {
        try (Stream<Path> stream = Files.list(directoryPath)) {
            stream.filter(path -> !Files.isDirectory(path))
                    .forEach(this::processFile);
        } catch (IOException e) {
            LOGGER.severe("Failed to scan existing files in path " + directoryPath);
        }
    }

    private void monitorCreatedFiles() {
        try {
            directoryPath.register(watcher, ENTRY_CREATE);
        } catch (IOException e) {
            LOGGER.severe("Failed to register watcher for path " + directoryPath);
        }

        WatchKey key = null;
        while (true) {
            try {
                key = watcher.poll(pollTimeOut, pollTimeUnit);
            } catch (InterruptedException e) {
                LOGGER.warning("Polling for files interrupted");
            }

            if (key == null) {
                continue;
            }

            for (WatchEvent<?> event : key.pollEvents()) {
                Path filePath = Path.of(directoryPath + File.separator + event.context());
                processFile(filePath);
            }
            key.reset();
        }
    }

    private void processFile(Path path) {
        Path processedPath = Path.of(path.getParent() + File.separator + processedDirectoryName + File.separator + path.getFileName());
        try {
            FileStatistic fileStatistic = fileParser.parse(path);
            LOGGER.info(fileStatistic.toString());
            Files.move(path, processedPath);
        } catch (ParseException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        } catch (IOException e) {
            String message = String.format("Unable to move file from %s to %s", path, processedPath);
            throw new ParseException(message, e);
        }
    }

}
