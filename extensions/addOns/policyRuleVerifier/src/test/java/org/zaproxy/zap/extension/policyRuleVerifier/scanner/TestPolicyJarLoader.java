package org.zaproxy.zap.extension.policyRuleVerifier.scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zaproxy.zap.extension.policyRuleVerifier.policies.AbstractPolicy;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

public class TestPolicyJarLoader {

    private static final String resourceFolder = "src/test/resources/";
    private static final String jarpath = "test-jar.jar";
    File jarFile = null;

    @Before
    public void setup() throws MalformedURLException {
        jarFile = new File(resourceFolder +  File.separator + jarpath);
    }

    @Test
    public void testGetPoliciesFromJar() throws MalformedURLException {
        List<AbstractPolicy> policies = PolicyJarLoader.getPoliciesFromJar(jarFile);
        Assert.assertEquals(1, policies.size());
        Assert.assertEquals("Test Policy", policies.get(0).getName());
        policies.get(0).setRules();
        Assert.assertEquals(3, policies.get(0).getRules().size());
    }

    @Test
    public void constructor() {
        PolicyJarLoader loader = new PolicyJarLoader();
        Assert.assertNotNull(loader);
    }

}
