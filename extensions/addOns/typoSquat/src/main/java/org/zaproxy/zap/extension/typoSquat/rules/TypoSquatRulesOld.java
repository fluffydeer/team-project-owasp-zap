package org.zaproxy.zap.extension.typoSquat.rules;

import org.zaproxy.zap.extension.typoSquat.actions.requestActions.*;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.apache.commons.text.diff.StringsComparator;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Deprecated
public class TypoSquatRulesOld {

    private Long minLevelOfSimilarity;
    private Long maxLevelOfSimilarity;

    private DomainHandler domainHandler;

    public TypoSquatRulesOld(DomainHandler domainHandler) {
        this.domainHandler = domainHandler;

        initSimilarityLevels();
    }

    private void initSimilarityLevels() {
        InputStream in = getClass().getClassLoader().getResourceAsStream("typosquat.properties");

        Properties appProps = new Properties();

        try {
            appProps.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        minLevelOfSimilarity = Long.parseLong(appProps.getProperty("typo.squat.level.of.similarity.min"));
        maxLevelOfSimilarity = Long.parseLong(appProps.getProperty("typo.squat.level.of.similarity.max"));
    }

    /**
     * implementation of the rules behind AbstractRequestActions
     *
     * @param hostName The hostname of the request
     * @return AbstractRequestAction    The action to execute
     */
    public synchronized AbstractRequestAction scan(String hostName) {
        //the happiest path ever - user didn't made any mistakes and we have the domain in our list(example: google)
        if (domainHandler.isWhiteListed(hostName)) {
            //in this case is still needed the List of domains? - is just the one user inserted
            return new WhiteListedAction();

            //still happy path, the domain is black listed, how we make the difference from the previous one?
        } else if (domainHandler.isBlackListed(hostName)) {
            //in this case there are no matching domains - we return the static HTML page
            return new BlackListedAction(domainHandler.getRedirect(hostName));

        } else { //if we couldn't find it in any list - check typo squat rules
            //scanTypoSquatRules(hostName, domainHandler.getWhiteList());
            LinkedHashSet<String> matchingDomains = getMatchingDomains(hostName);

            if (!matchingDomains.isEmpty()) {
                return new TypoSquatAction(matchingDomains, domainHandler);
            } else {
                return new NoTypoSquatAction(hostName, domainHandler);
            }
        }
    }

    private LinkedHashSet<String> getMatchingDomains(String hostName) {
        LinkedHashSet<String> matchedDomains = new LinkedHashSet<>();
        int mod;

        for (String whiteDomain : domainHandler.getWhiteList()) {

            StringsComparator cmp = new StringsComparator(hostName, whiteDomain);
            mod = cmp.getScript().getModifications();

            //level of similarity
            if (mod >= minLevelOfSimilarity.intValue() && mod <= maxLevelOfSimilarity.intValue()) {
                matchedDomains.add(whiteDomain);
            }
        }

        return matchedDomains;
    }
}
