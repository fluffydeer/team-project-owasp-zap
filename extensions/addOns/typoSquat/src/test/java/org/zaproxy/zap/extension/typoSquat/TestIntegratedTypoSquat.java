package org.zaproxy.zap.extension.typoSquat;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;

import java.util.TreeSet;

import static org.zaproxy.zap.extension.typoSquat.TestUtils.setupDirectory;

public class TestIntegratedTypoSquat {

    private static final String buildFolder = "build/test";
    ExtensionTypoSquat extension;
    DomainHandler domainHandler;

    @Before
    public void setupExtensionAdaptor() {
        setupDirectory(buildFolder);
        extension = new ExtensionTypoSquat(buildFolder);
        domainHandler = extension.getDomainHandler();
        domainHandler.addToBlackList("www.domain101.com", "redirect1");
    }

    @Test
    public void whiteListed_scenarioTest() throws HttpMalformedHeaderException {
        //setup
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domain1.com\r\n"));
        HttpMessage msg2 = Mockito.spy(msg);
        TreeSet parameterTreeSet = new TreeSet<>();
        Mockito.doReturn(parameterTreeSet).when(msg2).getUrlParams();
        //functionality to be tested
        boolean bool = extension.onHttpRequestSend(msg2);
        //verification
        Assert.assertFalse(bool);
        Assert.assertEquals(msg.getRequestHeader().getHostName(), "www.domain1.com");
        Assert.assertTrue(domainHandler.isWhiteListed("www.domain1.com"));
        Assert.assertFalse(domainHandler.isBlackListed("www.domain1.com"));
    }

    @Test
    public void noTypoSquat_scenarioTest() throws HttpMalformedHeaderException {
        //setup
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domainsafe10.be\r\n"));
        HttpMessage msg2 = Mockito.spy(msg);
        TreeSet parameterTreeSet = new TreeSet<>();
        Mockito.doReturn(parameterTreeSet).when(msg2).getUrlParams();

        //functionality to be tested
        boolean bool = extension.onHttpRequestSend(msg2);

        //verification
        Assert.assertFalse(bool);
        Assert.assertEquals("www.domainsafe10.be", msg.getRequestHeader().getHostName());
        Assert.assertTrue(domainHandler.isWhiteListed("www.domainsafe10.be"));
        Assert.assertFalse(domainHandler.isBlackListed("www.domainsafe10.be"));
    }

    @Test
    public void blackListed_scenarioTest() throws HttpMalformedHeaderException {
        //setup
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domain101.com\r\n"));
        HttpMessage msg2 = Mockito.spy(msg);
        TreeSet parameterTreeSet = new TreeSet<>();
        Mockito.doReturn(parameterTreeSet).when(msg2).getUrlParams();

        //functionality to be tested
        boolean bool = extension.onHttpRequestSend(msg2);

        //verification
        Assert.assertTrue(bool);
        Assert.assertEquals(307, msg.getResponseHeader().getStatusCode());
        Assert.assertEquals("//redirect1", msg.getResponseHeader().getHeader("Location"));
        Assert.assertFalse(domainHandler.isWhiteListed("www.domain101.com"));
        Assert.assertTrue(domainHandler.isBlackListed("www.domain101.com"));
    }

    @Test
    public void typoSquat_scenarioTest() throws HttpMalformedHeaderException {
        //setup
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));

        HttpMessage msg2 = Mockito.spy(msg);
        TreeSet parameterTreeSet = new TreeSet<>();
        Mockito.doReturn(parameterTreeSet).when(msg2).getUrlParams();

        //functionality to be tested
        boolean bool = extension.onHttpRequestSend(msg2);
        //verification
        Assert.assertTrue(bool);
        Assert.assertEquals(200, msg.getResponseHeader().getStatusCode());
        Assert.assertEquals("text/html; charset=utf-8", msg.getResponseHeader().getHeader("Content-Type"));
        Assert.assertFalse(domainHandler.isBlackListed("www.domian1.com"));
        Assert.assertFalse(domainHandler.isWhiteListed("www.domian1.com"));
    }
}
