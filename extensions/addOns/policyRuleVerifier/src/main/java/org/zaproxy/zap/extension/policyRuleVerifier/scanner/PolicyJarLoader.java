package org.zaproxy.zap.extension.policyRuleVerifier.scanner;

import org.reflections.Reflections;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.AbstractPolicy;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class PolicyJarLoader {

    public PolicyJarLoader() {
    }

    /**
     * Loads the policies specified in the jar and returns a list containing them.
     *
     * @param jarFile: the file where the jar file exists
     */
    public static List<AbstractPolicy> getPoliciesFromJar(File jarFile) throws MalformedURLException {
        return getPolicies(getLoader(jarFile.toURI().toURL()));
    }

    /**
     * Create a URLClassLoader that can load a jar.
     *
     * @param jarUrl: url where to find the jar.
     */
    private static URLClassLoader getLoader(URL jarUrl) {
        URL[] jarFile = {jarUrl};
        return new URLClassLoader(jarFile, PolicyJarLoader.class.getClassLoader());
    }

    /**
     * Creates the instances of the policies in the jar file and lists them.
     *
     * @param loader: The URLClassLoader object that loaded the jar file.
     */
    private static List<AbstractPolicy> getPolicies(URLClassLoader loader) {
        Reflections reflections = new Reflections(loader);
        Set<Class<? extends AbstractPolicy>> subTypes = reflections.getSubTypesOf(AbstractPolicy.class);

        return subTypes
                .stream()
                .filter(x -> x.getClassLoader().equals(loader))
                .map(x -> {
                    try {
                        return x.newInstance();
                    } catch (InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(toList());
    }
}
