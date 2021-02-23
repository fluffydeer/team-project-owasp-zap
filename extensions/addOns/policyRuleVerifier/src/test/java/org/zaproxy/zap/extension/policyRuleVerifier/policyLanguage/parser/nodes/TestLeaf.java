package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.parser.nodes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestLeaf {

    private Leaf leaf;

    @Before
    public void setup() {
        leaf = new Leaf("MATCHES");
    }

    @Test
    public void testToString() {
        Assert.assertEquals("MATCHES", leaf.toString());
    }

    @Test
    public void testTotalSize() {
        Assert.assertEquals(1, leaf.totalSize());
    }

    @Test
    public void testEquals() {
        Leaf same = new Leaf("MATCHES");
        Assert.assertTrue(same.equals(leaf));
        Leaf other = new Leaf("IN");
        Assert.assertFalse(other.equals(leaf));
    }
}
