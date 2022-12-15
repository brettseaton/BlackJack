package org.cis1200;

import org.cis1200.blackjack.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlackJackTest {
    @Test
    public void testTitle() {
        GUI gui = new GUI();
        assertEquals("Blackjack", gui.getTitle());
    }

    @Test
    public void testDealerCards() {
        GUI gui = new GUI();
        gui.dealerHitStay();
        assertEquals(false, gui.getDealerThinking());
    }


    @Test
    public void testRefresher() {
        // Create a new instance of the GUI class
        GUI gui = new GUI();

        // Set hit_stay_q, dealer_turn, and play_more_q to true
        gui.setHitStayQ(true);
        gui.setPlayMoreQ(true);
        gui.setDealerTurn(true);
        gui.setDealerThinking(false);
        gui.setPMaxTotal(30);
        gui.setPMinTotal(30);
        gui.setDMaxTotal(18);
        gui.setDMinTotal(18);


        // Call the refresher method
        gui.refresher();
        assertTrue(gui.getHitStayQ());
        assertFalse(gui.getDealerTurn());
    }

    @Test
    public void testRefresherEdgeCase() {
        // Create a new instance of the GUI class
        GUI gui = new GUI();

        // Set hit_stay_q, dealer_turn, and play_more_q to true
        gui.setHitStayQ(true);
        gui.setPlayMoreQ(true);
        gui.setDealerTurn(true);
        gui.setDealerThinking(false);
        gui.setPMaxTotal(30);
        gui.setPMinTotal(30);
        gui.setDMaxTotal(21);
        gui.setDMinTotal(21);


        // Call the refresher method
        gui.refresher();
        assertFalse(gui.getDealerTurn());
        assertTrue(gui.getPlayMoreQ());
    }

    @Test
    public void testRefresherZeroCase() {
        // Create a new instance of the GUI class
        GUI gui = new GUI();

        // Set hit_stay_q, dealer_turn, and play_more_q to true
        gui.setHitStayQ(true);
        gui.setPlayMoreQ(true);
        gui.setDealerTurn(true);
        gui.setDealerThinking(true);
        gui.setPMaxTotal(0);
        gui.setPMinTotal(0);
        gui.setDMaxTotal(0);
        gui.setDMinTotal(0);


        // Call the refresher method
        gui.refresher();
        assertTrue(gui.getHitStayQ());
        assertTrue(gui.getPlayMoreQ());
        assertTrue(gui.getDealerTurn());
        assertTrue(gui.getDealerThinking());
    }

    @Test
    public void testRefresherSingletonCase() {
        // Create a new instance of the GUI class
        GUI gui = new GUI();

        // Set hit_stay_q, dealer_turn, and play_more_q to true
        gui.setHitStayQ(false);
        gui.setPlayMoreQ(true);
        gui.setDealerTurn(true);
        gui.setDealerThinking(true);
        gui.setPMaxTotal(1);
        gui.setPMinTotal(1);
        gui.setDMaxTotal(21);
        gui.setDMinTotal(21);


        // Call the refresher method
        gui.refresher();
        assertFalse(gui.getHitStayQ());
        assertTrue(gui.getPlayMoreQ());
        assertTrue(gui.getDealerTurn());
        assertTrue(gui.getDealerThinking());
    }

    @Test
    public void testRefresherSingletonCaseDealer() {
        // Create a new instance of the GUI class
        GUI gui = new GUI();

        // Set hit_stay_q, dealer_turn, and play_more_q to true
        gui.setHitStayQ(false);
        gui.setPlayMoreQ(true);
        gui.setDealerTurn(true);
        gui.setDealerThinking(true);
        gui.setPMaxTotal(20);
        gui.setPMinTotal(20);
        gui.setDMaxTotal(1);
        gui.setDMinTotal(1);


        // Call the refresher method
        gui.refresher();
        assertFalse(gui.getHitStayQ());
        assertTrue(gui.getPlayMoreQ());
        assertTrue(gui.getDealerTurn());
        assertTrue(gui.getDealerThinking());
    }

    @Test
    public void testDealerHitStayMessage() {
        GUI gui = new GUI();
        gui.setDealerThinking(false);
        gui.setDMaxTotal(20);
        gui.setPMaxTotal(19);
        gui.dealerHitStay();
        assertFalse(gui.getDealerThinking());
        assertEquals(gui.getMessageHistory().get(0).getMessage(), "Dealer decided to stay! (total: " +
                (gui.getDMax()) + ")");
    }
    @Test
    public void testDealerDecidedToHitMessage() {
        GUI gui = new GUI();
        gui.setDealerThinking(false);
        gui.setDMaxTotal(12);
        gui.setPMaxTotal(19);
        gui.dealerHitStay();
        assertFalse(gui.getDealerThinking());
        assertEquals(gui.getMessageHistory().get(0).getMessage(), "Dealer decided to hit! (total: " +
                (gui.getDMax()) + ")");
    }
    @Test
    public void testDealerDecidedToHitMessageEdgeCase() {
        GUI gui = new GUI();
        gui.setDealerThinking(false);
        gui.setDMaxTotal(16);
        gui.setPMaxTotal(19);
        gui.dealerHitStay();
        assertFalse(gui.getDealerThinking());
        assertEquals(gui.getMessageHistory().get(0).getMessage(), "Dealer decided to hit! (total: " +
                (gui.getDMax()) + ")");
    }
    @Test
    public void testDealerDecidedToHitMessageWithMinimumDealer() {
        GUI gui = new GUI();
        gui.setDealerThinking(false);
        gui.setDMaxTotal(25);
        gui.setDMinTotal(15);
        gui.setPMaxTotal(19);
        gui.dealerHitStay();
        assertFalse(gui.getDealerThinking());
        assertEquals(gui.getMessageHistory().get(0).getMessage(), "Dealer decided to hit! (total: " +
                (gui.getDMin()) + ")");
    }

    @Test
    public void setWinnerTestDealerWins() {
        GUI gui = new GUI();
        gui.getMessageHistory().clear();
        gui.setDMaxTotal(19);
        gui.setPMaxTotal(17);
        gui.setWinner();
        assertEquals(gui.getMessageHistory().get(0).getMessage(), "Dealer wins!");
    }
    @Test
    public void setWinnerTestYouWin() {
        GUI gui = new GUI();
        gui.getMessageHistory().clear();
        gui.setDMaxTotal(18);
        gui.setPMaxTotal(19);
        gui.setWinner();
        assertEquals(gui.getMessageHistory().get(0).getMessage(), "You win!");
    }
    @Test
    public void setWinnerTestNoOneWins() {
        GUI gui = new GUI();
        gui.getMessageHistory().clear();
        gui.setDMaxTotal(25);
        gui.setPMaxTotal(25);
        gui.setDMinTotal(25);
        gui.setPMinTotal(25);
        gui.setWinner();
        assertEquals(gui.getMessageHistory().get(0).getMessage(), "Nobody wins!");
    }
    @Test
    public void setWinnerTestYouWinDealerBusts() {
        GUI gui = new GUI();
        gui.getMessageHistory().clear();
        gui.setDMaxTotal(25);
        gui.setPMaxTotal(12);
        gui.setDMinTotal(25);
        gui.setPMinTotal(12);
        gui.setWinner();
        assertEquals(gui.getMessageHistory().get(0).getMessage(), "You win!");
    }
    @Test
    public void testMessage() {
        Message message1 = new Message("abc", "def");
        Message message2 = new Message("123", "456");
        assertEquals(message1.getMessage(), "abc");
        assertEquals(message2.getMessage(), "123");
        assertEquals(message1.getWho(), "def");
        assertEquals(message2.getWho(), "456");
    }
    @Test
    public void testMessageWithEmptyMessage() {
        Message message1 = new Message("", "");
        Message message2 = new Message("", "");
        assertEquals(message1.getMessage(), "");
        assertEquals(message2.getMessage(), "");
        assertEquals(message1.getWho(), "");
        assertEquals(message2.getWho(), "");
    }



}





