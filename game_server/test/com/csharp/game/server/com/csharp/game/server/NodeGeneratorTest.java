package com.csharp.game.server;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NodeGeneratorTest
{
    NodeGenerator nodeGenerator;
    NodeGenerator nodeGenerator2;
    NodeGenerator nodeGenerator3;

    @Before
    public void setUp() throws Exception
    {
        nodeGenerator = new NodeGenerator();
        nodeGenerator2 = new NodeGenerator(2);
        nodeGenerator3 = new NodeGenerator(3);
    }

    @Test
    public void generateNode() throws Exception
    {
        char[] nodes = nodeGenerator.generateNode();
        assertEquals("Test nodeGenerator with no paramater constructor", 4, nodes.length);
        checkArrayForEmptySlots(nodes);
        nodes = nodeGenerator2.generateNode();
        assertEquals("Test nodeGenerator with 2 as paramater constructor", 2, nodes.length);
        checkArrayForEmptySlots(nodes);
        nodes = nodeGenerator3.generateNode();
        assertEquals("Test nodeGenerator with 3 as paramater constructor", 3, nodes.length);
        checkArrayForEmptySlots(nodes);
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateNodeFail1()
    {
        nodeGenerator = new NodeGenerator(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateNodeFail2()
    {
        nodeGenerator = new NodeGenerator(0);
    }

    private void checkArrayForEmptySlots(char[] nodes)
    {
        for(char node : nodes)
        {
            assertNotEquals("Check for empty array slots", null, node);
        }
    }
}