package org.zaproxy.zap.extension.typoSquat.rules;

import org.apache.commons.text.diff.StringsComparator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CheckSimilarity implements Rules {
    private Long minLevelOfSimilarity;
    private Long maxLevelOfSimilarity;

    CheckSimilarity(){
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

    public boolean isSimilar(String hostName, String whiteDomain) {
        int mod;

        StringsComparator cmp = new StringsComparator(hostName, whiteDomain);
        mod = cmp.getScript().getModifications();

        return mod >= minLevelOfSimilarity.intValue() && mod <= maxLevelOfSimilarity.intValue();
    }
}
