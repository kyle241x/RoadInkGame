package comp1110.ass2;

import org.junit.Test;

import static comp1110.ass2.RailroadInk.areNeighbours;
import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

public class AreNeighboursTest {

    @Test
    public void testNeighbour() {
        assertTrue("C2 and D2 are neighbour",areNeighbours("C2","D2"));
        assertTrue("D3 and D4 are neighbour",areNeighbours("D3","D4"));
        assertTrue("D3 and E3 are neighbour",areNeighbours("D3","E3"));
        assertTrue("G5 and G6 are neighbour",areNeighbours("G5","G6"));
    }

    @Test
    public void testSlash() {
        assertFalse("C2 and D3 are not neighbour",areNeighbours("C2","D3"));
        assertFalse("A0 and B1 are not neighbour",areNeighbours("A0","B1"));
        assertFalse("F5 and G6 are not neighbour",areNeighbours("F5","G6"));
        assertFalse("G0 and F1 are not neighbour",areNeighbours("G0","F1"));
    }

    @Test
    public void testNotNeighbourbour() {
        assertFalse("C2 and E2 are not neighbour", areNeighbours("C2", "E2"));
        assertFalse("C2 and C4 are not neighbour", areNeighbours("C2", "C4"));
        assertFalse("E2 and B5 are not neighbour", areNeighbours("E2", "B2"));
        assertFalse("A2 and A4 are not neighbour", areNeighbours("A2", "A4"));
    }

    @Test
    public void testInvalidInput(){
        assertFalse("Empty is invalid input",areNeighbours("C2", ""));
        assertFalse("B2E25 is invalid input",areNeighbours("E2", "B2E25"));
        assertFalse("S is invalid input",areNeighbours("S", "E2"));
        assertFalse("A1A11 is invalid input",areNeighbours("A1A11", "A2"));
    }

//    @Test
//    public void testOutOfBoard(){
//        assertFalse("A7 is OutOfBoard",areNeighbours("A6", "A7"));
//        assertFalse("H0 is OutOfBoard",areNeighbours("G0", "H0"));
//        assertFalse("G7 and G8 are both OutOfBoard",areNeighbours("G7", "G8"));
//    }
}