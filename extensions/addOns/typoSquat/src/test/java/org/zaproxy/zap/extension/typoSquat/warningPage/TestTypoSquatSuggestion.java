package org.zaproxy.zap.extension.typoSquat.warningPage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class TestTypoSquatSuggestion {
    private TypoSquatSuggestion typoSquatSuggestion;
    private static final String DOMAIN = "google.com";

    @Before
    public void setUp(){
        typoSquatSuggestion = new TypoSquatSuggestion(DOMAIN);
    }

    @Test
    public void testGetDomain(){
        Assert.assertEquals(DOMAIN, typoSquatSuggestion.getDomain());
    }

    @Test
    public void testGetHTML() {
        String template = "domain: {DOMAIN} and uuid: {UUID}";

        Assert.assertEquals(
                "domain: google.com and uuid: " + typoSquatSuggestion.getUUID(),
                typoSquatSuggestion.getHTML(template)
        );
    }
}
