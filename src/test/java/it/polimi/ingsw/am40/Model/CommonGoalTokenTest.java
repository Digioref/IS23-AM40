package it.polimi.ingsw.am40.Model;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalTokenTest {

    CommonGoalToken commonGoalToken2 = new CommonGoalToken(2);
    CommonGoalToken commonGoalToken4 = new CommonGoalToken(4);

    /**
     * Testing the update of the score for both 2 players games and 4 players games
     */
    void testUpdateScore()
    {
        assertEquals(commonGoalToken2.getScore(), 8);
        assertEquals(commonGoalToken4.getScore(), 8);

        commonGoalToken2.updateScore();
        commonGoalToken4.updateScore();

        assertEquals(commonGoalToken2.getScore(), 4);
        assertEquals(commonGoalToken4.getScore(), 6);

    }

    void testSetScore()
    {
     assertEquals(commonGoalToken2.getScore(), 8);
     commonGoalToken2.setScore(4);
     assertEquals(commonGoalToken2.getScore(), 4);
    }


    void testGetNumPlayer(){
        assertEquals(commonGoalToken2.getNumPlayer(), 2);
        assertEquals(commonGoalToken4.getNumPlayer(), 4);
    }


}