package org.zaproxy.zap.extension.typoSquat.rules;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.BlackListedAction;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.WhiteListedAction;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class TestTypoSquatRules {

    @Mock
    private DomainHandler domainHandler;

    @InjectMocks
    private TypoSquatRules typoSquatRules;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void scanWhiteListed(){

        when(domainHandler.isWhiteListed("whiteListedDomain")).thenReturn(Boolean.TRUE);
        assertTrue(typoSquatRules.scan("whiteListedDomain") instanceof WhiteListedAction);

    }

    @Test
    public void scanBlackListed(){

        when(domainHandler.isWhiteListed("blackListedDomain")).thenReturn(Boolean.FALSE);
        when(domainHandler.isBlackListed("blackListedDomain")).thenReturn(Boolean.TRUE);
        assertTrue(typoSquatRules.scan("blackListedDomain") instanceof BlackListedAction);

    }

}

