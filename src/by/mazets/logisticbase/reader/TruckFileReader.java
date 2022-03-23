package by.mazets.logisticbase.reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import by.mazets.logisticbase.exception.LogisticBaseException;


public class TruckFileReader {
    public static Logger logger = LogManager.getLogger();

    public List<String> readFromFile(String filename) throws LogisticBaseException {
        Path path = Paths.get(filename);
        if (!Files.exists(path) && Files.isDirectory(path) && !Files.isReadable(path)) {
            logger.error("file " + filename + " not read");
            throw new LogisticBaseException("file " + filename + " not read");
        }
        List<String> result;
        try (Stream<String> streamLines = Files.lines(path)) {
            result = streamLines.collect(Collectors.toList());
        } catch (IOException e) {
            logger.error("file " + filename + " read error");
            throw new LogisticBaseException("file " + filename + " read error", e);
        }
        logger.info("read data from file: " + filename);
        return result;
    }
}