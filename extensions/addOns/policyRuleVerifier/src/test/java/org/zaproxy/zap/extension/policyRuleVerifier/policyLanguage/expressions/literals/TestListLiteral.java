package org.zaproxy.zap.extension.policyRuleVerifier.policyLanguage.expressions.literals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestListLiteral {
    private ListLiteral listLiteral;
    private static final String name = null;

    @Before
    public void setUp(){
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("pear");
        listLiteral = new ListLiteral(list);
    }

    @Test
    public void testGetName(){
        Assert.assertEquals(name, listLiteral.getName());
    }

}