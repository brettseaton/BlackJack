package org.cis1200.blackjack;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GUI extends JFrame {

    //randomizer for new cards
    Random rand = new Random();

    //temporary integer used for used status
    int tempVar;

    //boolean that indicates whether the dealer is thinking or not, used to add suspense
    boolean dHitter = false;

    //list of cards
    ArrayList<Card> Cards = new ArrayList<>();

    //list of messages, message history over time
    ArrayList<Message> messageHistory = new ArrayList<>();

    //fonts used
    Font fontCard = new Font("Times New Roman", Font.PLAIN, 40);
    Font fontQuest = new Font("Times New Roman", Font.BOLD, 40);
    Font fontButton = new Font("Times New Roman", Font.PLAIN, 25);
    Font fontLog = new Font("Times New Roman", Font.ITALIC, 30);

    //Log message colors
    Color cDealer = Color.red;
    Color cPlayer = new Color(31,61,249);

    //strings used
    String questHitStay = "Hit or Stay?";
    String questPlayMore = "Play more?";

    //colors used
    Color colorBackground = new Color(30,125,15);
    Color colorButton = new Color(225,193,2);

    //buttons used
    JButton hitButton = new JButton();
    JButton stayButton = new JButton();
    JButton bYes = new JButton();
    JButton bNo = new JButton();

    //resolution adjustments
    int sW = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    int sH = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    //window resolution
    int aW = 1300;
    int aH = 800;

    //card grid position and dimensions
    int xVal = 50;
    int yVal = 50;
    int width = 900;
    int gridH = 400;

    //card spacing and dimensions
    int spcBetwCards = 10;
    int roundingOfCards = 10;
    int tCardW = (int) width/6;
    int tCardH = (int) gridH/2;
    int cardW = tCardW - spcBetwCards*2;
    int cardH = tCardH - spcBetwCards*2;

    //booleans about phases
    boolean hit_stay_q = true;
    boolean dealer_turn = false;
    boolean play_more_q = false;

    //player and dealer card array
    ArrayList<Card> pCards = new ArrayList<Card>();
    ArrayList<Card> dCards = new ArrayList<Card>();

    //player and dealer totals
    int pMinTotal = 0;
    int pMaxTotal = 0;
    int dMinTotal = 0;
    int dMaxTotal = 0;

    //polygons for diamond shapes
    int[] polyX = new int[4];
    int[] polyY = new int[4];

    public GUI() {
        this.setTitle("Blackjack");
        this.setBounds((sW-aW-6)/2, (sH-aH-29)/2, aW+6, aH+29);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        Board board = new Board();
        this.setContentPane(board);
        board.setLayout(null);

        Move move = new Move();
        this.addMouseMotionListener(move);

        Click click = new Click();
        this.addMouseListener(click);

        //button stuff

        ActHit actHit = new ActHit();
        hitButton.addActionListener(actHit);
        hitButton.setBounds(1000, 200, 100, 50);
        hitButton.setBackground(colorButton);
        hitButton.setFont(fontButton);
        hitButton.setText("HIT");
        board.add(hitButton);

        ActStay actStay = new ActStay();
        stayButton.addActionListener(actStay);
        stayButton.setBounds(1150, 200, 100, 50);
        stayButton.setBackground(colorButton);
        stayButton.setFont(fontButton);
        stayButton.setText("STAY");
        board.add(stayButton);

        ActYes actYes = new ActYes();
        bYes.addActionListener(actYes);
        bYes.setBounds(1000, 600, 100, 50);
        bYes.setBackground(colorButton);
        bYes.setFont(fontButton);
        bYes.setText("YES");
        board.add(bYes);

        ActNo actNo = new ActNo();
        bNo.addActionListener(actNo);
        bNo.setBounds(1150, 600, 100, 50);
        bNo.setBackground(colorButton);
        bNo.setFont(fontButton);
        bNo.setText("NO");
        board.add(bNo);

        //creating all cards

        String temp_str = "starting_temp_str_name";
        for (int i = 0; i < 52; i++) {
            if (i % 4 == 0) {
                temp_str = "Spades";
            } else if (i % 4 == 1) {
                temp_str = "Hearts";
            } else if (i % 4 == 2) {
                temp_str = "Diamonds";
            } else{
                temp_str = "Clubs";
            }
            Cards.add(new Card((i/4) + 1, temp_str, i));
        }


        //selecting initial cards for player and dealer

        tempVar = rand.nextInt(52);
        pCards.add(Cards.get(tempVar));
        Cards.get(tempVar).setUsed();

        tempVar = rand.nextInt(52);
        while (Cards.get(tempVar).used) {
            tempVar = rand.nextInt(52);
        }
        dCards.add(Cards.get(tempVar));
        Cards.get(tempVar).setUsed();

        tempVar = rand.nextInt(52);
        while (Cards.get(tempVar).used) {
            tempVar = rand.nextInt(52);
        }
        pCards.add(Cards.get(tempVar));
        Cards.get(tempVar).setUsed();


        tempVar = rand.nextInt(52);
        while (Cards.get(tempVar).used) {
            tempVar = rand.nextInt(52);
        }
        dCards.add(Cards.get(tempVar));
        Cards.get(tempVar).setUsed();
    }
    public boolean getDealerThinking(){
        return this.dHitter;
    }
    public void setDealerThinking(boolean x){
        dHitter = x;
    }
    public ArrayList<Card> getDealerCards(){
        return this.dCards;
    }
    public ArrayList<Card> getPlayerCards(){
        return this.pCards;
    }
    public int getPMin(){
        return this.pMinTotal;
    }
    public void setPMinTotal(int x){
        this.pMinTotal = x;
    }
    public int getPMax(){
        return this.pMaxTotal;
    }
    public void setPMaxTotal(int x){
        this.pMaxTotal = x;
    }
    public int getDMin(){
        return this.dMinTotal;
    }
    public void setDMinTotal(int x){
        this.dMinTotal = x;
    }
    public int getDMax(){
        return this.dMaxTotal;
    }
    public void setDMaxTotal(int x){
        this.dMaxTotal = x;
    }
    public int getDWins(){
        return RunBlackJack.dWins;
    }
    public void setDWins(int x){
        RunBlackJack.dWins = x;
    }
    public int getPWins(){
        return RunBlackJack.pWins;
    }
    public void setPWins(int x){
        RunBlackJack.pWins = x;
    }
    public boolean getHitStayQ(){
        return this.hit_stay_q;
    }
    public void setHitStayQ(boolean stayQ){
        this.hit_stay_q = stayQ;
    }
    public void setDealerTurn(boolean stayQ){
        this.dealer_turn = stayQ;
    }
    public void setPlayMoreQ(boolean stayQ){
        this.play_more_q = stayQ;
    }

    public boolean getDealerTurn(){
        return this.dealer_turn;
    }
    public boolean getPlayMoreQ(){
        return this.play_more_q;
    }
    public ArrayList<Message> getMessageHistory(){
        return this.messageHistory;
    }
    public void totalsChecker() {

        int acesCount;

        //calculation of player's totals
        pMinTotal = 0;
        pMaxTotal = 0;
        acesCount = 0;

        for (Card c : pCards) {
            pMinTotal += c.value;
            pMaxTotal += c.value;
            if (c.name == "Ace")
                acesCount++;

        }

        if (acesCount > 0)
            pMaxTotal += 10;

        dMinTotal = 0;
        dMaxTotal = 0;
        acesCount = 0;

        for (Card c : dCards) {
            dMinTotal += c.value;
            dMaxTotal += c.value;
            if (c.name == "Ace")
                acesCount++;

        }

        if (acesCount > 0)
            dMaxTotal += 10;
    }

    public void setWinner() {
        int pPoints = 0;
        int dPoints = 0;

        if (pMaxTotal > 21) {
            pPoints = pMinTotal;
        } else {
            pPoints = pMaxTotal;
        }

        if (dMaxTotal > 21) {
            dPoints = dMinTotal;
        } else {
            dPoints = dMaxTotal;
        }

        if (pPoints > 21 && dPoints > 21) {
            messageHistory.add(new Message("Nobody wins!", "Dealer"));
        } else if (dPoints > 21) {
            messageHistory.add(new Message("You win!", "Player"));
            RunBlackJack.pWins++;
        } else if (pPoints > 21) {
            messageHistory.add(new Message("Dealer wins!", "Dealer"));
            RunBlackJack.dWins++;
        } else if (pPoints > dPoints) {
            messageHistory.add(new Message("You win!", "Player"));
            RunBlackJack.pWins++;
        } else {
            messageHistory.add(new Message("Dealer wins!", "Dealer"));
            RunBlackJack.dWins++;
        }

    }


    public void dealerHitStay() {
        dHitter = true;

        int dAvailable = 0;
        if (dMaxTotal > 21) {
            dAvailable = dMinTotal;
        } else {
            dAvailable = dMaxTotal;
        }

        int pAvailable = 0;
        if (pMaxTotal > 21) {
            pAvailable = pMinTotal;
        } else {
            pAvailable = pMaxTotal;
        }

        repaint();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if ((dAvailable < pAvailable && pAvailable <= 21) || dAvailable < 16) {
            int tempMax = 0;
            if (dMaxTotal <= 21) {
                tempMax = dMaxTotal;
            } else {
                tempMax = dMinTotal;
            }
            String mess = ("Dealer decided to hit! (total: " + Integer.toString(tempMax) + ")");
            messageHistory.add(new Message(mess, "Dealer"));
            tempVar = rand.nextInt(52);
            while (Cards.get(tempVar).used) {
                tempVar = rand.nextInt(52);
            }
            dCards.add(Cards.get(tempVar));
            Cards.get(tempVar).setUsed();
        } else {
            int tempMax = 0;
            if (dMaxTotal <= 21) {
                tempMax = dMaxTotal;
            } else {
                tempMax = dMinTotal;
            }
            String mess = ("Dealer decided to stay! (total: " + Integer.toString(tempMax) + ")");
            messageHistory.add(new Message(mess, "Dealer"));
            setWinner();
            dealer_turn = false;
            play_more_q = true;
        }
        dHitter = false;
    }

    public void refresher() {

        if (hit_stay_q) {
            hitButton.setVisible(true);
            stayButton.setVisible(true);
        } else {
            hitButton.setVisible(false);
            stayButton.setVisible(false);
        }

        if (dealer_turn) {
            if (!dHitter)
                dealerHitStay();
        }

        if (play_more_q) {
            bYes.setVisible(true);
            bNo.setVisible(true);
        } else {
            bYes.setVisible(false);
            bNo.setVisible(false);
        }

        totalsChecker();

        if ((pMaxTotal == 21 || pMinTotal >= 21) && hit_stay_q) {
            int tempMax = 0;
            if (pMaxTotal <= 21) {
                tempMax = pMaxTotal;
            } else {
                tempMax = pMinTotal;
            }
            String mess = ("Auto pass! (total: " + Integer.toString(tempMax) + ")");
            messageHistory.add(new Message(mess, "Player"));
            hit_stay_q = false;
            dealer_turn = true;
        }

        if ((dMaxTotal == 21 || dMinTotal >= 21) && dealer_turn) {
            int tempMax = 0;
            if (dMaxTotal <= 21) {
                tempMax = dMaxTotal;
            } else {
                tempMax = dMinTotal;
            }
            String mess = ("Dealer auto pass! (total: " + Integer.toString(tempMax) + ")");
            messageHistory.add(new Message(mess, "Dealer"));
            setWinner();
            dealer_turn = false;
            play_more_q = true;
        }

        repaint();
    }

    public class Board extends JPanel {

        public void paintComponent(Graphics g) {
            //background
            g.setColor(colorBackground);
            g.fillRect(0, 0, aW, aH);

            //questions
            if (hit_stay_q) {
                g.setColor(Color.black);
                g.setFont(fontQuest);
                g.drawString(questHitStay, xVal+width+60, yVal+90);
                g.drawString("Total:", xVal+width+60, yVal+290);
                if (pMinTotal == pMaxTotal) {
                    g.drawString(Integer.toString(pMaxTotal), xVal+width+60, yVal+350);
                } else if (pMaxTotal <= 21) {
                    g.drawString(Integer.toString(pMinTotal) + " or " + Integer.toString(pMaxTotal),
                            xVal+width+60, yVal+350);
                } else {
                    g.drawString(Integer.toString(pMinTotal), xVal+width+60, yVal+350);
                }
            } else if (play_more_q) {
                g.setColor(Color.black);
                g.setFont(fontQuest);
                g.drawString(questPlayMore, xVal+width+70, yVal+490);
            }

            g.setColor(Color.black);
            g.fillRect(xVal, yVal+gridH+50, width, 500);

            //messageHistory
            g.setFont(fontLog);
            int logIndex = 0;
            for (Message L : messageHistory) {
                if (L.getWho().equalsIgnoreCase("Dealer")) {
                    g.setColor(cDealer);
                } else {
                    g.setColor(cPlayer);
                }
                g.drawString(L.getMessage(), xVal+20, yVal+480+logIndex*35);
                logIndex++;
            }

            //score
            g.setColor(Color.BLACK);
            g.setFont(fontQuest);
            String score = ("Score: " + Integer.toString(RunBlackJack.pWins) + " - " +
                    Integer.toString(RunBlackJack.dWins));
            g.drawString(score, xVal+width+70, yVal+gridH+300);

            //player cards
            int index = 0;
            for (Card c : pCards) {
                g.setColor(Color.white);
                g.fillRect(xVal+spcBetwCards+tCardW*index+roundingOfCards, yVal+spcBetwCards, cardW-roundingOfCards*2, cardH);
                g.fillRect(xVal+spcBetwCards+tCardW*index, yVal+spcBetwCards+roundingOfCards, cardW, cardH-roundingOfCards*2);
                g.fillOval(xVal+spcBetwCards+tCardW*index, yVal+spcBetwCards, roundingOfCards*2, roundingOfCards*2);
                g.fillOval(xVal+spcBetwCards+tCardW*index, yVal+spcBetwCards+cardH-roundingOfCards*2, roundingOfCards*2,
                        roundingOfCards*2);
                g.fillOval(xVal+spcBetwCards+tCardW*index+cardW-roundingOfCards*2, yVal+spcBetwCards, roundingOfCards*2,
                        roundingOfCards*2);
                g.fillOval(xVal+spcBetwCards+tCardW*index+cardW-roundingOfCards*2, yVal+spcBetwCards+cardH-roundingOfCards*2,
                        roundingOfCards*2, roundingOfCards*2);

                g.setFont(fontCard);
                if (c.shape.equalsIgnoreCase("Hearts") || c.shape.equalsIgnoreCase("Diamonds"))
                {
                    g.setColor(Color.red);
                } else {
                    g.setColor(Color.black);
                }

                g.drawString(c.symbol, xVal+spcBetwCards+tCardW*index+roundingOfCards, yVal+spcBetwCards+cardH-roundingOfCards);

                if (c.shape.equalsIgnoreCase("Hearts")) {
                    g.fillOval(xVal+tCardW*index+42, yVal+70, 35, 35);
                    g.fillOval(xVal+tCardW*index+73, yVal+70, 35, 35);
                    g.fillArc(xVal+tCardW*index+30, yVal+90, 90, 90, 51, 78);
                } else if (c.shape.equalsIgnoreCase("Diamonds")) {
                    polyX[0] = xVal+tCardW*index+75;
                    polyX[1] = xVal+tCardW*index+50;
                    polyX[2] = xVal+tCardW*index+75;
                    polyX[3] = xVal+tCardW*index+100;
                    polyY[0] = yVal+60;
                    polyY[1] = yVal+100;
                    polyY[2] = yVal+140;
                    polyY[3] = yVal+100;
                    g.fillPolygon(polyX, polyY, 4);
                } else if (c.shape.equalsIgnoreCase("Spades")) {
                    g.fillOval(xVal+tCardW*index+42, yVal+90, 35, 35);
                    g.fillOval(xVal+tCardW*index+73, yVal+90, 35, 35);
                    g.fillArc(xVal+tCardW*index+30, yVal+15, 90, 90, 51+180, 78);
                    g.fillRect(xVal+tCardW*index+70, yVal+100, 10, 40);
                } else {
                    g.fillOval(xVal+tCardW*index+40, yVal+90, 35, 35);
                    g.fillOval(xVal+tCardW*index+75, yVal+90, 35, 35);
                    g.fillOval(xVal+tCardW*index+58, yVal+62, 35, 35);
                    g.fillRect(xVal+tCardW*index+70, yVal+75, 10, 70);
                }

                //-------------------------
                index++;
            }

            if (dealer_turn || play_more_q) {
                //dealer cards
                index = 0;
                for (Card c : dCards) {
                    g.setColor(Color.white);
                    g.fillRect(xVal+spcBetwCards+tCardW*index+roundingOfCards, yVal+spcBetwCards+200, cardW-roundingOfCards*2,
                            cardH);
                    g.fillRect(xVal+spcBetwCards+tCardW*index, yVal+spcBetwCards+roundingOfCards+200, cardW,
                            cardH-roundingOfCards*2);
                    g.fillOval(xVal+spcBetwCards+tCardW*index, yVal+spcBetwCards+200, roundingOfCards*2,
                            roundingOfCards*2);
                    g.fillOval(xVal+spcBetwCards+tCardW*index, yVal+spcBetwCards+cardH-roundingOfCards*2+200, roundingOfCards*2,
                            roundingOfCards*2);
                    g.fillOval(xVal+spcBetwCards+tCardW*index+cardW-roundingOfCards*2, yVal+spcBetwCards+200, roundingOfCards*2,
                            roundingOfCards*2);
                    g.fillOval(xVal+spcBetwCards+tCardW*index+cardW-roundingOfCards*2, yVal+spcBetwCards+cardH-roundingOfCards*2+200,
                            roundingOfCards*2, roundingOfCards*2);

                    g.setFont(fontCard);
                    if (c.shape.equalsIgnoreCase("Hearts") ||
                            c.shape.equalsIgnoreCase("Diamonds")) {
                        g.setColor(Color.red);
                    } else {
                        g.setColor(Color.black);
                    }

                    g.drawString(c.symbol, xVal+spcBetwCards+tCardW*index+roundingOfCards, yVal+spcBetwCards+cardH-roundingOfCards+200);

                    if (c.shape.equalsIgnoreCase("Hearts")) {
                        g.fillOval(xVal+tCardW*index+42, yVal+70+200, 35, 35);
                        g.fillOval(xVal+tCardW*index+73, yVal+70+200, 35, 35);
                        g.fillArc(xVal+tCardW*index+30, yVal+90+200, 90, 90, 51,
                                78);
                    } else if (c.shape.equalsIgnoreCase("Diamonds")) {
                        polyX[0] = xVal+tCardW*index+75;
                        polyX[1] = xVal+tCardW*index+50;
                        polyX[2] = xVal+tCardW*index+75;
                        polyX[3] = xVal+tCardW*index+100;
                        polyY[0] = yVal+60+200;
                        polyY[1] = yVal+100+200;
                        polyY[2] = yVal+140+200;
                        polyY[3] = yVal+100+200;
                        g.fillPolygon(polyX, polyY, 4);
                    } else if (c.shape.equalsIgnoreCase("Spades")) {
                        g.fillOval(xVal+tCardW*index+42, yVal+90+200, 35, 35);
                        g.fillOval(xVal+tCardW*index+73, yVal+90+200, 35, 35);
                        g.fillArc(xVal+tCardW*index+30, yVal+15+200, 90, 90,
                                51+180, 78);
                        g.fillRect(xVal+tCardW*index+70, yVal+100+200, 10, 40);
                    } else {
                        g.fillOval(xVal+tCardW*index+40, yVal+90+200, 35, 35);
                        g.fillOval(xVal+tCardW*index+75, yVal+90+200, 35, 35);
                        g.fillOval(xVal+tCardW*index+58, yVal+62+200, 35, 35);
                        g.fillRect(xVal+tCardW*index+70, yVal+75+200, 10, 70);
                    }

                    //-------------------------
                    index++;
                }

                g.setColor(Color.black);
                g.setFont(fontQuest);
                g.drawString("Your total: ", xVal+width+60, yVal+40);
                if (pMaxTotal <= 21) {
                    g.drawString(Integer.toString(pMaxTotal), xVal+width+60, yVal+120);
                } else {
                    g.drawString(Integer.toString(pMinTotal), xVal+width+60, yVal+120);
                }
                g.drawString("Dealer's total: ", xVal+width+60, yVal+240);
                if (dMaxTotal <= 21) {
                    g.drawString(Integer.toString(dMaxTotal), xVal+width+60, yVal+320);
                } else {
                    g.drawString(Integer.toString(dMinTotal), xVal+width+60, yVal+320);
                }
            }

        }

    }

    public class Move implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent arg0) {

        }

        @Override
        public void mouseMoved(MouseEvent arg0) {

        }

    }

    public static class Click implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent arg0) {
        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
        }

        @Override
        public void mouseExited(MouseEvent arg0) {
        }

        @Override
        public void mousePressed(MouseEvent arg0) {
        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
        }

    }



    public class ActStay implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (hit_stay_q) {


                int tempMax = 0;
                if (pMaxTotal <= 21) {
                    tempMax = pMaxTotal;
                } else {
                    tempMax = pMinTotal;
                }
                String mess = ("You decided to stay! (total: " + Integer.toString(tempMax) + ")");
                messageHistory.add(new Message(mess, "Player"));

                hit_stay_q = false;
                dealer_turn = true;
            }
        }

    }
    public class ActHit implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if (hit_stay_q) {

                int tempMax = 0;
                if (pMaxTotal <= 21) {
                    tempMax = pMaxTotal;
                } else {
                    tempMax = pMinTotal;
                }
                String mess = ("You decided to hit! (total: " + Integer.toString(tempMax) + ")");
                messageHistory.add(new Message(mess, "Player"));

                tempVar = rand.nextInt(52);
                while (Cards.get(tempVar).used) {
                    tempVar = rand.nextInt(52);
                }
                pCards.add(Cards.get(tempVar));
                Cards.get(tempVar).setUsed();
            }
        }

    }

    public class ActYes implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            for (Card c : Cards) {
                c.setNotUsed();
            }

            pCards.clear();
            dCards.clear();
            messageHistory.clear();

            play_more_q = false;
            hit_stay_q = true;

            tempVar = rand.nextInt(52);
            pCards.add(Cards.get(tempVar));
            Cards.get(tempVar).setUsed();

            tempVar = rand.nextInt(52);
            while (Cards.get(tempVar).used) {
                tempVar = rand.nextInt(52);
            }
            dCards.add(Cards.get(tempVar));
            Cards.get(tempVar).setUsed();

            tempVar = rand.nextInt(52);
            while (Cards.get(tempVar).used) {
                tempVar = rand.nextInt(52);
            }
            pCards.add(Cards.get(tempVar));
            Cards.get(tempVar).setUsed();

            tempVar = rand.nextInt(52);
            while (Cards.get(tempVar).used) {
                tempVar = rand.nextInt(52);
            }
            dCards.add(Cards.get(tempVar));
            Cards.get(tempVar).setUsed();

        }

    }

    public class ActNo implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            RunBlackJack.terminator = true;
            dispose();
        }

    }

}
