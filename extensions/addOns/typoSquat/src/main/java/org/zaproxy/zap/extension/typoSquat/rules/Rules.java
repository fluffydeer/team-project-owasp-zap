package org.zaproxy.zap.extension.typoSquat.rules;

public interface Rules {
    boolean isSimilar(String hostName, String whiteDomain);
}
