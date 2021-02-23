package org.zaproxy.zap.extension.typoSquat;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.parosproxy.paros.network.HtmlParameter;
import org.parosproxy.paros.network.HttpMalformedHeaderException;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.NoTypoSquatAction;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.TypoSquatAction;
import org.zaproxy.zap.extension.typoSquat.actions.userActions.UserNoAction;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.zaproxy.zap.extension.typoSquat.rules.TypoSquatRules;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

import java.io.IOException;
import java.util.TreeSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.zaproxy.zap.extension.typoSquat.TestUtils.setupDirectory;

public class TestExtensionTypoSquat {

    private ExtensionTypoSquat extensionTypoSquat;
    private static final String buildFolder = "build/test";

    private TypoSquatRules typoSquatRules;

    private DomainHandler domainHandler;

    private SessionManager sessionManager;

    private HttpMessage httpMessage;

    private NoTypoSquatAction noTypoSquatAction;
    private TypoSquatAction typoSquatAction;

    private UserNoAction userNoAction;
    private TreeSet<HtmlParameter> parameterTreeSet;
    private HttpRequestHeader httpRequestHeader;

    @Before
    public void setup() throws Exception{
        setupDirectory(buildFolder);

        domainHandler = mock(DomainHandler.class);
        sessionManager = mock(SessionManager.class);
        typoSquatRules = mock(TypoSquatRules.class);
        httpMessage = mock(HttpMessage.class);
        userNoAction = mock(UserNoAction.class);

        noTypoSquatAction = mock(NoTypoSquatAction.class);
        typoSquatAction = mock(TypoSquatAction.class);
        extensionTypoSquat = new ExtensionTypoSquat(buildFolder);

        HtmlParameter htmlParameter = new HtmlParameter(HtmlParameter.Type.header,
                "ZAPROXY-typosquat-extension-uuid","test");
        parameterTreeSet = new TreeSet<>();

        parameterTreeSet.add(htmlParameter);

        httpRequestHeader = new HttpRequestHeader
                ("GET / HTTP/1.1\r\nHost: www.domain1.com\r\n");
    }

    @Test
    public void onHttpRequestSend_userAction(){

        when(httpMessage.getUrlParams()).thenReturn(parameterTreeSet);
        when(sessionManager.hasAction("test")).thenReturn(true);
        when(sessionManager.getAction("test")).thenReturn(userNoAction);

        when(userNoAction.execute(httpMessage,sessionManager)).thenReturn(true);

        when(httpMessage.getRequestHeader()).thenReturn(httpRequestHeader);

        assertTrue(extensionTypoSquat.onHttpRequestSend(httpMessage));

        verify(sessionManager, times(0)).hasAction("test");

    }

    @Test
    public void onHttpRequestSend_noUserAction(){

        when(httpMessage.getUrlParams()).thenReturn(new TreeSet<>());

        when(typoSquatRules.scan("theHOST")).thenReturn(noTypoSquatAction);

        when(httpMessage.getRequestHeader()).thenReturn(httpRequestHeader);

        assertFalse(extensionTypoSquat.onHttpRequestSend(httpMessage));

        verify(sessionManager, times(0)).hasAction("test");

    }

    @Test
    @Ignore
    public void onHttpRequestSend_noUserAction_E() throws Exception{

        when(httpMessage.getUrlParams()).thenReturn(new TreeSet<>());
        when(httpMessage.getRequestHeader()).thenReturn(httpRequestHeader);

        when(typoSquatRules.scan(org.mockito.Matchers.any())).thenReturn(typoSquatAction);

        when(typoSquatAction.execute(org.mockito.Matchers.any(), org.mockito.Matchers.any())).thenThrow(new IOException());

        assertTrue(extensionTypoSquat.onHttpRequestSend(httpMessage));
    }

    @Test
    public void testOnHttpResponseReceived() throws HttpMalformedHeaderException {
        //setup
        HttpMessage msg = new HttpMessage(new HttpRequestHeader("GET / HTTP/1.1\r\nHost: www.domian1.com\r\n"));
        //functionality to be tested
        boolean bool= extensionTypoSquat.onHttpResponseReceived(msg);
        //verification
        Assert.assertFalse(bool);
    }

    @Test
    public void testGetAuthor(){
        Assert.assertEquals("DSS - Group 3", extensionTypoSquat.getAuthor());
    }

    @Test
    public void testGetDescription(){
        Assert.assertEquals("Automatic prevention of TypoSquats", extensionTypoSquat.getDescription());
    }

    @Test
    public void testGetArrangeableListenerOrder(){
        Assert.assertEquals(0, extensionTypoSquat.getArrangeableListenerOrder());
    }


}
