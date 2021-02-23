package org.zaproxy.zap.extension.policyRuleVerifier.scanner;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.policyRuleVerifier.ExtensionPolicyRuleVerifier;
import org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.Policy;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;

public class TestPolicyRuleScanner {

    private static final String resourceFolder = "src/test/resources/";
    private static final String jarfile = "test-jar.jar";
    private static final String jarfile2 = "test-jar-1.jar";
    private static final String DSLFile = "test-dsl-1.txt";
    private static final String content = "P:name of this cool policy;\r\nR:name of first rule:\r\n    NOT RES BODY IS Error\r\n    AND\r\n    (\r\n    REQ HEAD Status IS 200\r\n    OR\r\n            REQ HEAD Status IS 302\r\n    );\r\nR:name of second rule:\r\n    NOT RES BODY IS Error\r\n    AND\r\n    (\r\n    REQ HEAD Status IS 200\r\n    OR\r\n    REQ HEAD Status IS 302\r\n    );";
    private static final String policyName = "name of this cool policy";
    private File fileDSL;
    private File jarUrl = null;
    private File jarUrl2 = null;
    private PolicyRuleScanner scanner;

    @Before
    public void setUp() throws MalformedURLException {
        scanner = new PolicyRuleScanner(new ExtensionPolicyRuleVerifier());
        jarUrl = new File(resourceFolder +  File.separator + jarfile);
        jarUrl2 = new File(resourceFolder +  File.separator + jarfile2);
        fileDSL = new File(resourceFolder +  File.separator + DSLFile);
    }

    @Test
    public void testInitializeNewJARPolicy() throws MalformedURLException {
        scanner.initializeNewJARPolicy(jarUrl);
        Assert.assertEquals("Test Policy", scanner.getPoliciesJAR().get(0).getName());
        Assert.assertEquals(1, scanner.getPoliciesJAR().size());
    }

    @Test
    public void testCheckRequestRules_report() throws HttpMalformedHeaderException, MalformedURLException {
        scanner.initializeNewJARPolicy(jarUrl);
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        Boolean bool = scanner.onHttpRequestSend(msg);
        Assert.assertEquals(bool, true);
    }

    @Test
    public void testCheckRequestRules_noReport() throws IOException {
        scanner.initializeNewJARPolicy(jarUrl);
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        msg.getRequestHeader().setSecure(true);
        Boolean bool = scanner.onHttpRequestSend(msg);
        Assert.assertEquals(bool, true);
    }

    @Test
    public void testCheckResponseRules_noReport() throws HttpMalformedHeaderException, MalformedURLException {
        scanner.initializeNewJARPolicy(jarUrl);
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        msg.getResponseHeader().setHeader(HttpHeader.SET_COOKIE, "Set-Cookie: name=lol; Domain=droop.be; Secure; HttpOnly; samesite=strict");
        Boolean bool = scanner.onHttpResponseReceive(msg);
        Assert.assertEquals(bool, true);
    }

    @Test
    public void testCheckResponseRules_Report() throws IOException {
        scanner.initializeNewJARPolicy(jarUrl);
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        Boolean bool = scanner.onHttpResponseReceive(msg);
        Assert.assertEquals(bool, true);
    }

    @Test
    public void testInitializeMultiplePolicies() throws MalformedURLException {
        scanner.initializeNewJARPolicy(jarUrl);
        scanner.initializeNewJARPolicy(jarUrl2);
        Assert.assertEquals("Test Policy", scanner.getPoliciesJAR().get(0).getName());
        Assert.assertEquals("Second Policy", scanner.getPoliciesJAR().get(1).getName());
        Assert.assertEquals(2, scanner.getPoliciesJAR().size());
    }

    @Test
    public void testListPolicyJARNames() throws MalformedURLException {
        scanner.initializeNewJARPolicy(jarUrl);
        scanner.initializeNewJARPolicy(jarUrl2);
        List<String> policyNames = scanner.getPolicyJARNames();
        Assert.assertEquals(2, policyNames.size());
        Assert.assertEquals("Test Policy", policyNames.get(0));
        Assert.assertEquals("Second Policy", policyNames.get(1));
    }

    @Test
    public void testGetListenerOrder() {
        Assert.assertEquals(0, scanner.getArrangeableListenerOrder());
    }

    @Test
    public void testGetPolicyDSLNames(){
        scanner.initializeNewDSLPolicy(fileDSL);
        HashMap<String, String> DSLNames = scanner.getPolicyDSLNames();
        Assert.assertTrue(DSLNames.containsKey(policyName));
        Assert.assertNotNull(DSLNames.get(policyName));
    }

    @Test
    public void testInitializeNewDSLPolicy(){
        Policy policy = new Policy(policyName, content);

        scanner.initializeNewDSLPolicy(fileDSL);
        Policy actualPolicy = scanner.getPoliciesDSL().get(0);

        Assert.assertEquals(policy.getName(), actualPolicy.getName());
        Assert.assertEquals(policy.getPolicyContent(), policy.getPolicyContent());
    }
}
