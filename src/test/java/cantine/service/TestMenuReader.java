package cantine.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import cantine.service.MenuReader;

public class TestMenuReader {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void testRead() throws Exception {
        new MenuReader().read();
    }

}
