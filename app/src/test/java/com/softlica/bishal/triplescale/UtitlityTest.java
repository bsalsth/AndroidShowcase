package com.softlica.bishal.triplescale;

import com.softlica.bishal.triplescale.utility.U;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtitlityTest {
    @Test
    public void getAge() throws Exception {
        int age = U.getAge("2000-07-14 07:29:45");
        assertEquals(17, age);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}