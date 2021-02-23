package org.zaproxy.zap.extension.typoSquat.rules;
import org.apache.commons.text.diff.EditScript;
import org.apache.commons.text.diff.StringsComparator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class TestCheckSimilarity {

    @InjectMocks
    private CheckSimilarity checkSimilarity;

    @Before
    public void setup(){
        initMocks(this);
    }

    @Test
    public void isSimilar(){
       assertTrue(checkSimilarity.isSimilar("google.com", "googe.com"));
    }

    @Test
    public void isNotSimilar(){
        assertFalse(checkSimilarity.isSimilar("google.com", "domain.com"));
    }

    @Test
    public void testSimilarity() {

        int mod = 0;

        //1 missing character//mod=1
        StringsComparator cmp2 = new StringsComparator("google.com", "googe.com");
        EditScript<Character> script2 = cmp2.getScript();
        mod = script2.getModifications();

        assertEquals(1, mod);

        //1 extra character//mod=1
        StringsComparator cmp3 = new StringsComparator("google.com", "gooogle.com");
        EditScript<Character> script3 = cmp3.getScript();
        mod = script3.getModifications();

        assertEquals(1, mod);

        //1 replaced character//mod = 2
        StringsComparator cmp1 = new StringsComparator("google.com", "goggle.com");
        EditScript<Character> script1 = cmp1.getScript();
        mod = script1.getModifications();

        assertEquals(2, mod);

        //2 adjacent swapped characters
        StringsComparator cmp4 = new StringsComparator("google.com", "gogole.com");
        EditScript<Character> script4 = cmp4.getScript();
        mod = script4.getModifications();

        assertEquals(2, mod);

        //2 adjacent swapped characters
        StringsComparator cmp5 = new StringsComparator("google.com", "googleee.com");
        EditScript<Character> script5 = cmp5.getScript();
        mod = script5.getModifications();

        assertEquals(2, mod);

    }
}
