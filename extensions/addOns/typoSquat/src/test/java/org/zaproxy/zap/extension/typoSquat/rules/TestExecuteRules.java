package org.zaproxy.zap.extension.typoSquat.rules;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.NoTypoSquatAction;
import org.zaproxy.zap.extension.typoSquat.actions.requestActions.TypoSquatAction;
import org.zaproxy.zap.extension.typoSquat.fileHandler.DomainHandler;

import java.util.LinkedHashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestExecuteRules {

    @InjectMocks
    private ExecuteRules executeRules;

    @Mock
    private DomainHandler domainHandler;

    @Mock
    private RulesAbstract rulesAbstract;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void checkRules(){
        CheckSimilarity checkSimilarity = new CheckSimilarity();
        Set<Rules> rules = new LinkedHashSet<>();
        rules.add(checkSimilarity);
        LinkedHashSet<String> whiteList = new LinkedHashSet<>();
        whiteList.add("www.google.com");
        when(domainHandler.getWhiteList()).thenReturn(whiteList);

        when(rulesAbstract.getRules()).thenReturn(rules);

        assertTrue(executeRules.checkRules("www.ggogle.com",domainHandler) instanceof TypoSquatAction);
    }

    @Test
    public void checkRulesNoTypoSquat(){
        CheckSimilarity checkSimilarity = new CheckSimilarity();
        Set<Rules> rules = new LinkedHashSet<>();
        rules.add(checkSimilarity);
        LinkedHashSet<String> whiteList = new LinkedHashSet<>();
        whiteList.add("www.google.com");
        when(domainHandler.getWhiteList()).thenReturn(whiteList);

        when(rulesAbstract.getRules()).thenReturn(rules);

        assertTrue(executeRules.checkRules("www.gggggogle.com",domainHandler) instanceof NoTypoSquatAction);
    }

}
