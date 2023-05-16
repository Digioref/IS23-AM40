package it.polimi.ingsw.am40.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests the Player class
 */
public class PlayerTest {
    /**
     Tests the creation of more players
     */
    @Test
    public void addPlayer() {
        Player p1 = new Player("Giuseppe");
        assertEquals("Giuseppe", p1.getNickname());
        Player p2 = new Player("Francesco");
        assertNotEquals("Giuseppe", p2.getNickname());

        assertEquals(0, p1.getCurrentScore());
        assertEquals(0, p1.getFinalScore());
        assertEquals(0, p2.getCurrentScore());
        assertEquals(0, p2.getFinalScore());

    }
    /**
     Tests the feature next;
     */
    @Test
    public void assignNext() {
        Player p1 = new Player("Giuseppe");
        Player p2 = new Player("Giovanna");
        p1.setNext(p2);
        Player p3 = new Player("Francesco");
        p2.setNext(p3);
        Player p4 = new Player("Marta");
        p3.setNext(p4);
        p4.setNext(p1);

        Player p = p2.getNext();
        assertEquals("Francesco", p.getNickname());
        p = p4.getNext();
        assertEquals("Giuseppe", p.getNickname());
        p = p1.getNext();
        assertEquals("Giovanna", p.getNickname());
        p = p3.getNext();
        assertEquals("Marta", p.getNickname());
    }




}