package org.zaproxy.zap.extension.typoSquat.fileHandler;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class DomainHandler {

    private static final String whiteListFileName = "whiteList.txt";
    private static final String blackListFileName = "blacklist.txt";

    private String directoryLocation;
    private FileHandler fileHandler;

    // LinkedHashSet ot be able to have fast lookup and fast iteration
    private LinkedHashSet<String> whiteList = new LinkedHashSet<>();
    private HashMap<String, String> blackList = new HashMap<>();


    public DomainHandler(String directoryLocation) {
        this.directoryLocation = directoryLocation;
        this.fileHandler = new FileHandler(this.directoryLocation);
        this.whiteList = fileHandler.readWhiteList(whiteListFileName);
        this.blackList = fileHandler.readBlackList(blackListFileName);
    }

    /**
     * This method returns true if a domain exists in the Whitelist, false otherwise.
     * @param domain
     * @return
     */
    public boolean isWhiteListed(String domain) {
        return whiteList.contains(domain);
    }

    /**
     * This method returns true if a domain exists in the Blacklist, false otherwise.
     * @param domain
     * @return
     */
    public boolean isBlackListed(String domain) {
        return blackList.containsKey(domain);
    }

    /**
     * The method adds the domain to the Whitelist.
     *
     * @param domain
     */
    public void addToWhiteList(String domain) {
        whiteList.add(domain);
        fileHandler.addToFile(whiteListFileName, domain);
    }

    /**
     * This method adds the domain and its corresponding redirect domain to the Blacklist.
     * @param domain
     * @param redirect
     */
    public void addToBlackList(String domain, String redirect) {
        blackList.put(domain, redirect);
        fileHandler.addToFile(blackListFileName, domain + ", " + redirect);
    }

    /**
     * This method returns the corresponding redirect domain from a blacklisted domain.
     * @param domain
     * @return
     */
    public String getRedirect(String domain) {
        return blackList.get(domain);
    }

    /**
     * This method returns the complete whitelist.
     * @return
     */
    public synchronized LinkedHashSet<String> getWhiteList() {
        return whiteList;
    }

}