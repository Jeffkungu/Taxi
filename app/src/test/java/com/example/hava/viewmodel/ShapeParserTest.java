package com.example.hava.viewmodel;

import com.example.hava.parser.ShapeParser;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShapeParserTest {

    @Before
    public void setUp() throws Exception {
        ShapeParser shapeParser = new ShapeParser();
    }

    @Test
    public void addition_isCorrect() {
        int shapeParser;
        assertEquals(4, 2 + 2);
    }
}