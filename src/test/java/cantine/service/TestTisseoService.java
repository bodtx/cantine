package cantine.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestTisseoService {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void testRead() throws Exception {
        TisseoService tisseo = new TisseoService();

        tisseo.problemeTisseo("CER3100444", "AuriDij974");

    }

}
