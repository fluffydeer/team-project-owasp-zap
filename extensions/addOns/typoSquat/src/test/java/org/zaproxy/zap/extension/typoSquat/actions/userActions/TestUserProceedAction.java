package org.zaproxy.zap.extension.typoSquat.actions.userActions;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.parosproxy.paros.network.HttpMessage;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import org.zaproxy.zap.extension.typoSquat.session.SessionManager;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class TestUserProceedAction extends TestAbstractUserAction {
    private DomainHandler domainHandler;
    private UserProceedAction userProceedAction;
    private UUID original_uuid;
    private static final String DOMAIN = "google.com";
    private static final String ERROR_DOMAIN = "goggle.com";
    private static final String RESOURCE_FOLDER = "src/test/resources/";
    private static final String ORIGINAL_FOLDER = "fileHandler/originalTestFiles";
    private static final String TEMP_FOLDER = "fileHandler/tmp";

    @Before
    public void setUp(){
        original_uuid = UUID.randomUUID();
        try {
            FileUtils.copyDirectory(new File(RESOURCE_FOLDER + ORIGINAL_FOLDER),
                    new File(RESOURCE_FOLDER + TEMP_FOLDER));
            domainHandler = new DomainHandler(RESOURCE_FOLDER + TEMP_FOLDER);
        } catch (IOException e) {
            Assert.fail("Test setup failed");
        }
        userProceedAction = new UserProceedAction(original_uuid.toString(), DOMAIN, domainHandler);
    }

    @Test
    public void testExecute() {
        DomainHandler expectedDomainHandler = new DomainHandler(RESOURCE_FOLDER + TEMP_FOLDER);
        SessionManager sessionManager = new SessionManager();

        HttpMessage msg = new HttpMessage();

        boolean bool = userProceedAction.execute(msg, sessionManager);            //correct scenario, whitelists the domain
        expectedDomainHandler.addToWhiteList(DOMAIN);

        Assert.assertTrue(bool);
        Assert.assertEquals(expectedDomainHandler.getWhiteList(), domainHandler.getWhiteList());
        Assert.assertEquals(307, msg.getResponseHeader().getStatusCode());
        Assert.assertEquals("//" + DOMAIN, msg.getResponseHeader().getHeader("Location"));
    }
}