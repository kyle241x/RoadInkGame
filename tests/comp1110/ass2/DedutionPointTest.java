package comp1110.ass2;

import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static sun.security.krb5.KrbException.errorMessage;

public class DedutionPointTest {
    @Test
    public void testSample(){
        String sample = "A3D61A3D53B0C52A0B52A2B63A4D41B0E60A0F61A3D31A3D23A2G30B0F34A3E32A1B01B2B10A1B21A0A63A4D01A1G" +
                "41B0G12S2D10A4C10B2A10A2B33A1A30S4E11A4E21A3C21A3C31S5F11";
        int panltypoint = RailroadInk.deductionPoint(sample);
        assertTrue(errorMessage(11), panltypoint == 11);
    }

    @Test
    public void exitDirectly(){
        String sample = "A1A30A0B30A5A11B1B20S4A23";
        int panltypoint = RailroadInk.deductionPoint(sample);
        assertTrue(errorMessage(11), panltypoint == 1);
    }

}
