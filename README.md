# Build and Run
Two arguments are required at start up:
* directoryPath: the directory to be monitored 
* pollTimeoutInMilliSeconds: waits until specified time for file creation events 

Maven is the dependency manager. Please see pom.xml file for details.
* Junit and Mockito is used for unit test
* Apache commons-io lib is used for getting file's extension

## Design
The design was chosen for extensibility and single responsibility considering different file location and supported files. 
The parsing strategy makes it easy to add a new parsing algorithm according to its extension.
In case of different statistic results, abstractions could be created for the return type.

## In a nutshell
1. The app starts in FileAnalyticsApp, reads initial arguments, verifies if "processed" folder exists and creates one otherwise, then starts the monitor.
2. The monitor scans the chosen directory for existing file and process them. Then it listens and processes file events creation.
3. Files are processed according to its extension and the corresponding parsing strategy, returning the statistic result.

### Classes
* FileAnalyticsApp: starts the app with the parameterized configuration
* DirectoryMonitor: abstraction of a monitor in case of new monitors like non-local directory
* LocalDirectoryMonitor: watches registered directory for file created changes
* FileParser: selects the parsing strategy
* FileParseStrategy: parser abstraction
* TxtFileParser: text files parser 
* FileExtensionParse: abstraction for getting file extension
* ApacheFileExtensionParser: gets file extension with Apache lib
* FileStatistic: a value object to store parse results
* FileExtension: enumeration for supported file extensions

#### Possible improvements
* Use file properties for environment variables
* Externalize special characters constant
* Handle filename conflict
* Parameterize file encoding type
* Make LocalDirectoryMonitor more flexible for testing