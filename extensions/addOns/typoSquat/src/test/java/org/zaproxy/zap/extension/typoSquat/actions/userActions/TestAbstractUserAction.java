package org.zaproxy.zap.extension.typoSquat.actions.userActions;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class TestAbstractUserAction {
    private UserNoAction userResponseNoAction;
    private UUID no_action_uuid;
    private static final String DOMAIN = "google.com";
    private static final String ERROR_DOMAIN = "goggle.com";

    @Test
    public void testExecute(){}

    @Test
    public void testIsValid(){
        no_action_uuid = UUID.randomUUID();
        userResponseNoAction = new UserNoAction(no_action_uuid.toString(), DOMAIN);

        Assert.assertTrue(userResponseNoAction.isValid(no_action_uuid.toString(), DOMAIN));             //if returns true all ok
        Assert.assertFalse(userResponseNoAction.isValid(UUID.randomUUID().toString(), DOMAIN));         //if returns false all ok
        Assert.assertFalse(userResponseNoAction.isValid(no_action_uuid.toString(), ERROR_DOMAIN));       //if returns false all ok
    }
}