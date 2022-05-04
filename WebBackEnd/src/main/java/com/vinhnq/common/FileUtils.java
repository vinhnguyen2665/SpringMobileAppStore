package com.vinhnq.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    private static final Logger logger = LogManager.getLogger(FileUtils.class);

    public static String readSqlFile(String filename) {
        ClassLoader classLoader = FileUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(filename).getFile());
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(file.getPath())), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void appendStringFile(String my_file_name, String string) throws IOException {
        try {
            Writer output;
            output = new BufferedWriter(new FileWriter(my_file_name, true));
            output.append(string + System.lineSeparator());
            output.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static void createDirectoryIfNotExists(String pathStr) {
        Path path = Paths.get(pathStr);
        boolean exists = Files.exists(path);
        try {
            if (!exists) {
                Files.createDirectories(path);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public static long getFileSize(Path filePath) {
        try {
            long bytes = Files.size(filePath);
            return bytes / 1024;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}

