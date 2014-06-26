package cantine.service;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
//        new MenuReader().read();
    	PasswordEncoder encoder = new BCryptPasswordEncoder();
    	System.out.println(encoder.encode("flavor"));
    }

}
