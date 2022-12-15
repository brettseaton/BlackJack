package org.cis1200.blackjack;

public class RunBlackJack implements Runnable {

    long xTime = System.nanoTime();
    public static boolean terminator = false;
    public static int pWins = 0;
    public static int dWins = 0;

    //screen refresh rate
    public int refresh = 100;

    GUI gui = new GUI();
    public static void main(String[] args) {
        new Thread(new RunBlackJack()).start();
    }

    @Override
    public void run() {
        while(!terminator) {
            if (System.nanoTime() - xTime >= 1000000000/refresh) {
                gui.refresher();
                gui.repaint();
                xTime = System.nanoTime();
            }
        }
    }

}
