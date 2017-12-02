package com.spykins.locationtracker;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UtilTest {
    Util mUtil;

    @Before
    public void setUp() {
        mUtil = new Util();
    }


    @Test
    public void isValidDouble() throws Exception {
        assertTrue(!mUtil.isValidDouble(""));
        assertTrue(!mUtil.isValidDouble("0.234A"));
        assertTrue(mUtil.isValidDouble("1"));
        assertTrue(!mUtil.isValidDouble("1)"));
    }

}