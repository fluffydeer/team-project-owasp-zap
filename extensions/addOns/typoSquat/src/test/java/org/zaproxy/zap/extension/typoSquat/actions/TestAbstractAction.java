package org.zaproxy.zap.extension.typoSquat.actions;

import org.junit.Test;
import org.parosproxy.paros.network.HttpMalformedHeaderException;

import java.io.IOException;

public abstract class TestAbstractAction {

    @Test
    public abstract void testExecute() throws IOException;
}
