package org.zaproxy.zap.extension.policyRuleVerifier.scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.Policy;

import java.io.File;

import static org.mockito.Mockito.when;

public class TestPolicyDSLLoader {
    private static final String resourceFolder = "src/test/resources/";
    private static final String DSLFile = "test-dsl-1.txt";
    private File file;

    @Before
    public void setup() {
        file = new File(resourceFolder +  File.separator + DSLFile);
    }

    @Test
    public void testConstructor() {
        PolicyDSLLoader loader = new PolicyDSLLoader();
        Assert.assertNotNull(loader);
    }

    @Test
    public void testGetPolicyFromDSL(){
        Policy actualPolicy = PolicyDSLLoader.getPolicyFromDSL(file);
        String content = "P:name of this cool policy;R:name of first rule:    NOT RES BODY IS Error    AND    (    REQ HEAD Status IS 200    OR            REQ HEAD Status IS 302    );R:name of second rule:    NOT RES BODY IS Error    AND    (    REQ HEAD Status IS 200    OR    REQ HEAD Status IS 302    );";
        Assert.assertEquals(new Policy("name of this cool policy", "").getName(), actualPolicy.getName());
        Assert.assertNotEquals(new Policy("invalid name", "").getName(), actualPolicy.getName());
        Assert.assertNotEquals(new Policy("", "invalid content").getPolicyContent(), actualPolicy.getPolicyContent());
    }

    @Test
    public void testReadFile(){
        String fileContent = PolicyDSLLoader.readFile(file);
        Assert.assertFalse(fileContent.isEmpty());
        Assert.assertTrue(fileContent.contains("P:"));
        Assert.assertTrue(fileContent.contains("R:"));
    }

    @Test(expected = Exception.class)
    public void testThrowingException(){
        File file = new File("/invalid.txt");
        PolicyDSLLoader loader = Mockito.mock(PolicyDSLLoader.class);
        when(loader.readFile(file)).thenThrow(new Exception());

        PolicyDSLLoader.readFile(file);
    }
}