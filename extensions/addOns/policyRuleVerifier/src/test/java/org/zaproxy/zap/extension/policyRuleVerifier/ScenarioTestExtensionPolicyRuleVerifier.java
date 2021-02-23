package org.zaproxy.zap.extension.policyRuleVerifier;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpHeader;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ScenarioTestExtensionPolicyRuleVerifier {

    private static final String resourceFolder = "src/test/resources/";
    private static final String jarpath = "test-jar.jar";
    private static final String jarpath2 = "test-jar-1.jar";
    private static final String DSLpath = "DSL-PolicyRules.txt";
    File DSLFile = null;
    File jarFile = null;
    File jarFile2 = null;
    private ExtensionPolicyRuleVerifier extension;

    @Before
    public void setup() {
        jarFile = new File(resourceFolder +  File.separator + jarpath);
        jarFile2 = new File(resourceFolder +  File.separator + jarpath2);
        DSLFile = new File(resourceFolder +  File.separator + DSLpath);
        extension = new ExtensionPolicyRuleVerifier();
    }

    @Test
    public void loadPoliciesAndScan_noReportResponseRules() throws IOException {
        extension.getScanner().initializeNewJARPolicy(jarFile);
        extension.getScanner().initializeNewJARPolicy(jarFile2);
        Assert.assertEquals(2, extension.getScanner().getPoliciesJAR().size());
        Assert.assertEquals(3, extension.getScanner().getPoliciesJAR().get(0).getRules().size());
        Assert.assertEquals(4, extension.getScanner().getPoliciesJAR().get(1).getRules().size());
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        msg.getResponseHeader().setHeader(HttpHeader.SET_COOKIE, "Set-Cookie: name=lol; Domain=droop.be; Secure; HttpOnly; samesite=strict");
        msg.getResponseHeader().setHeader(HttpHeader.SET_COOKIE, "Set-Cookie: name=hallo; Domain=lolzies.com; Secure; HttpOnly; samesite=strict");
        extension.getScanner().onHttpResponseReceive(msg);
        Assert.assertTrue(extension.getReport().getReportedRules().isEmpty());
    }

    @Test
    public void loadPoliciesAndScan_reportResponseRules() throws IOException {
        extension.getScanner().initializeNewJARPolicy(jarFile);
        extension.getScanner().initializeNewJARPolicy(jarFile2);
        Assert.assertEquals(2, extension.getScanner().getPoliciesJAR().size());
        Assert.assertEquals(3, extension.getScanner().getPoliciesJAR().get(0).getRules().size());
        Assert.assertEquals(4, extension.getScanner().getPoliciesJAR().get(1).getRules().size());
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        msg.getResponseHeader().setHeader(HttpHeader.SET_COOKIE, "Set-Cookie: name=lol; Domain=droop.be; Secure; HttpOnly; samesite=strict");
        msg.getResponseHeader().setHeader(HttpHeader.SET_COOKIE, "Set-Cookie: name=hallo; Domain=lolzies.com; Secure; samesite=strict");
        extension.getScanner().onHttpResponseReceive(msg);
        Assert.assertFalse(extension.getReport().getReportedRules().isEmpty());
        Assert.assertTrue(extension.getReport().getReportedRules().get(0).contains("Test Policy"));
        Assert.assertTrue(extension.getReport().getReportedRules().get(0).contains("Cookie"));
    }

    @Test
    public void loadPoliciesAndScan_ReportRequestRules() throws IOException {
        extension.getScanner().initializeNewJARPolicy(jarFile);
        extension.getScanner().initializeNewJARPolicy(jarFile2);
        Assert.assertEquals(2, extension.getScanner().getPoliciesJAR().size());
        Assert.assertEquals(3, extension.getScanner().getPoliciesJAR().get(0).getRules().size());
        Assert.assertEquals(4, extension.getScanner().getPoliciesJAR().get(1).getRules().size());
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        extension.getScanner().onHttpRequestSend(msg);
        Assert.assertFalse(extension.getReport().getReportedRules().isEmpty());
        Assert.assertTrue(extension.getReport().getReportedRules().get(0).contains("Test Policy"));
        Assert.assertTrue(extension.getReport().getReportedRules().get(0).contains("Traffic"));
    }

    @Test
    public void loadPoliciesAndScan_noReportRequestRules() throws IOException {
        extension.getScanner().initializeNewJARPolicy(jarFile);
        extension.getScanner().initializeNewJARPolicy(jarFile2);
        Assert.assertEquals(2, extension.getScanner().getPoliciesJAR().size());
        Assert.assertEquals(3, extension.getScanner().getPoliciesJAR().get(0).getRules().size());
        Assert.assertEquals(4, extension.getScanner().getPoliciesJAR().get(1).getRules().size());
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        msg.getRequestHeader().setSecure(true);
        msg.getResponseHeader().setHeader(HttpHeader.SET_COOKIE, "Set-Cookie: name=lol; Domain=droop.be; Secure; HttpOnly; samesite=strict");
        extension.getScanner().onHttpRequestSend(msg);
        Assert.assertTrue(extension.getReport().getReportedRules().isEmpty());
    }

    @Test
    public void loadDSLPolicyAndScan_noReportRules() throws IOException {
        extension.getScanner().initializeNewDSLPolicy(DSLFile);
        Assert.assertEquals(1, extension.getScanner().getPoliciesDSL().size());
        Assert.assertEquals(1, extension.getScanner().getAllPolicies().size());
        Assert.assertEquals("Example policy", extension.getScanner().getAllPolicies().get(0).getName());
        Assert.assertEquals(3, extension.getScanner().getAllPolicies().get(0).getRules().size());
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian.com\r\n"));
        msg.getRequestHeader().setSecure(true);
        extension.getScanner().onHttpResponseReceive(msg);
        extension.getScanner().onHttpRequestSend(msg);
        Assert.assertTrue(extension.getReport().getReportedRules().isEmpty());
    }

    @Test
    public void loadDSLPolicyAndScan_reportRules() throws IOException {
        extension.getScanner().initializeNewDSLPolicy(DSLFile);
        Assert.assertEquals(1, extension.getScanner().getPoliciesDSL().size());
        Assert.assertEquals(1, extension.getScanner().getAllPolicies().size());
        Assert.assertEquals("Example policy", extension.getScanner().getAllPolicies().get(0).getName());
        Assert.assertEquals(3, extension.getScanner().getAllPolicies().get(0).getRules().size());
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.google.com\r\n"));
        msg.getRequestHeader().setSecure(true);
        extension.getScanner().onHttpResponseReceive(msg);
        extension.getScanner().onHttpRequestSend(msg);
        Assert.assertTrue(extension.getReport().getReportedRules().get(0).contains("google"));
    }

    @Test
    public void loadDSLPolicyAndScan_noReportRulesOnRequest() throws IOException {
        extension.getScanner().initializeNewDSLPolicy(DSLFile);
        Assert.assertEquals(1, extension.getScanner().getPoliciesDSL().size());
        Assert.assertEquals(1, extension.getScanner().getAllPolicies().size());
        Assert.assertEquals("Example policy", extension.getScanner().getAllPolicies().get(0).getName());
        Assert.assertEquals(3, extension.getScanner().getAllPolicies().get(0).getRules().size());
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian.com\r\n"));
        extension.getScanner().onHttpRequestSend(msg);
        Assert.assertTrue(extension.getReport().getReportedRules().isEmpty());
    }

    @Test
    public void loadDSLAndJarPolicyAndScan_ReportRulesOnResponse() throws IOException {
        extension.getScanner().initializeNewDSLPolicy(DSLFile);
        extension.getScanner().initializeNewJARPolicy(jarFile);
        extension.getScanner().initializeNewJARPolicy(jarFile2);
        Assert.assertEquals(1, extension.getScanner().getPoliciesDSL().size());
        Assert.assertEquals(2, extension.getScanner().getPoliciesJAR().size());
        Assert.assertEquals(3, extension.getScanner().getAllPolicies().size());
        Assert.assertEquals("Example policy", extension.getScanner().getAllPolicies().get(2).getName());
        Assert.assertEquals("Test Policy", extension.getScanner().getAllPolicies().get(0).getName());
        Assert.assertEquals("Second Policy", extension.getScanner().getAllPolicies().get(1).getName());
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.google.com\r\n"));
        extension.getScanner().onHttpRequestSend(msg);
        extension.getScanner().onHttpResponseReceive(msg);
        Assert.assertEquals(3, extension.getReport().getReportedRules().size());
    }

}
