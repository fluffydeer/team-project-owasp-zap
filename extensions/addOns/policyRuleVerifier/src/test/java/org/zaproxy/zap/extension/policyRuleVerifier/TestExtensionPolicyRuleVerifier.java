package org.zaproxy.zap.extension.policyRuleVerifier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;


public class TestExtensionPolicyRuleVerifier {

    private ExtensionPolicyRuleVerifier extension;
    private static final String resourceFolder = "src/test/resources/";
    private static final String jarPath = "test-jar.jar";
    private static final String DSLpath = "DSL-PolicyRules.txt";
    File jarFile = null;
    File DSLfile = null;

    @Before
    public void setUp() throws MalformedURLException {
        extension = new ExtensionPolicyRuleVerifier();
        jarFile = new File(resourceFolder +  File.separator + jarPath);
        DSLfile = new File(resourceFolder + File.separator + DSLpath);
    }

    @Test
    public void testGetName() {
        Assert.assertEquals("Policy Rule Verifier", extension.getName());
    }

    @Test
    public void testGetAuthor() {
        Assert.assertEquals("OSS-Group03", extension.getAuthor());
    }

    @Test
    public void testInitNewJARPolicy() throws MalformedURLException {
        extension.initPolicy(jarFile);
        Assert.assertEquals(1, extension.getScanner().getPoliciesJAR().size());
    }

    @Test
    public void testInitNewDSLPolicy() throws MalformedURLException {
        extension.initPolicy(DSLfile);
        Assert.assertEquals(1, extension.getScanner().getPoliciesDSL().size());
    }

    @Test
    public void testListPolicyNames() throws MalformedURLException {
        extension.initPolicy(jarFile);
        List<String> policyNames = extension.listPolicyJARNames();
        Assert.assertEquals(1, policyNames.size());
        Assert.assertEquals("Test Policy", policyNames.get(0));
    }

    @Test
    public void testListDSLPolicyNames() throws MalformedURLException {
        extension.initPolicy(DSLfile);
        HashMap<String,String> policyNames = extension.listPolicyDSLNames();
        Assert.assertEquals(1, policyNames.size());
        Assert.assertTrue(policyNames.containsKey("Example policy"));
    }

}
