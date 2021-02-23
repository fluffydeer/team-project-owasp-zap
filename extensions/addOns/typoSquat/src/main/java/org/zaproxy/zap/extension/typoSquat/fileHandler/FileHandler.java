package org.zaproxy.zap.extension.typoSquat.fileHandler;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashSet;

public class FileHandler {

    private static final Logger logger = Logger.getLogger(FileHandler.class);
    private String directoryLocation;

    public FileHandler(String path){
        this.directoryLocation = path;
        this.initializeFile(path);
    }

    public String getDirectoryLocation() { return this.directoryLocation; }

    /**
     * Initialize the first whitelist and an empty blacklist file if they don't already exist.
     * Returns true if the files had to be created, returns false if they already existed.
     */
    protected void initializeFile(String path) {
        File whiteFile = new File(path + File.separator, "whiteList.txt");
        File blackFile = new File(path + File.separator, "blackList.txt");
        if (!blackFile.exists()){
            createBlackList(blackFile);
        }
        if (!whiteFile.exists()){
            createWhiteList(whiteFile);
        }
    }

    /**
     * This methods reads the whitelist file in the resources folder and copies it to the whitelist file in the working
     * directory of the user.
     *
     * @param whiteFile
     */
    protected void createWhiteList(File whiteFile) {
        InputStream in = getClass().getResourceAsStream("/whiteList.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            BufferedWriter writer = new BufferedWriter( new FileWriter(whiteFile));
            transferTo(reader, writer);
            writer.close();
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void transferTo(BufferedReader reader, BufferedWriter writer) throws IOException {
        char[] buffer = new char[100];
        int nRead;
        while ((nRead = reader.read(buffer, 0, 100)) >= 0) {
            writer.write(buffer, 0, nRead);
        }
    }

    /**
     * This method creates a new blank blackList file.
     *
     * @param blackFile
     */
    protected void createBlackList(File blackFile) {
        try {
            blackFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error on reading path to whitelist file. Error: " + e.getMessage(), e);
        }
    }

    /**
     * This method adds the domain to a file
     * @param fileName
     * @param domain
     */
    protected void addToFile(String fileName, String domain) {
        File file = new File(directoryLocation + File.separator, fileName);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.newLine();
            writer.write(domain);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error on adding domain to typoSquatting file. Error: " + e.getMessage(), e);
        }
    }

    /**
     * This method reads the existing
     * @param fileName
     * @return
     */
    protected LinkedHashSet<String> readWhiteList(String fileName) {
        File file = new File(directoryLocation + File.separator, fileName);
        LinkedHashSet<String> whiteList = new LinkedHashSet<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                whiteList.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error on opening/reading typoSquatting file. Error: " + e.getMessage(), e);
        }
        return whiteList;
    }

    protected HashMap<String, String> readBlackList(String fileName) {
        File file = new File(directoryLocation + File.separator, fileName);
        HashMap<String, String> blackList = new HashMap<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (! line.trim().equals("")) {
                    String[] splitLine = line.split(", ");
                    blackList.put(splitLine[0].trim(), splitLine[1].trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error on opening/reading typoSquatting file. Error: " + e.getMessage(), e);
        }
        return blackList;
    }
}
