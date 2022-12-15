This is Brett's BlackJack Game!

To play the game, press play on the "RunBlackJack" file, not the Game File.

I deviated significantly from my original plan, so hopefully I can explain everything in a bit more detail here.

Collections:
- I used two ArrayLists in my blackjack game. The first is for cards. A card is an object that I created for each card in
the card deck. Every card has the following fields:
    String name;
    int value;
    String shape;
    boolean used = false;
    int id;
    String symbol;

Used is the most important field here, meaning if the card has already been dealt, at which point it must be removed
from further consideration as a potentially dealt card.

- The second collection is messageHistory which I used to display the history of the game. If you decide to hit, the
message history will store and display that you have hit. It will continue to display that information, even if a new
message shows up. I knew messages needed to be stored in a certain order and I knew that I needed to manipulate those
messages in certain scenarios, like at the restart of a new game--so I though an arrayList would work well.

Inheritance and Subtyping:
- I used a lot of inheritance. GUI extends JFrame in order to do the main display. Board extends JPanel in order to
host all of the controls and visuals written in GUI and in the Board. The Move class implements MouseMotionListener,
Click implements mouseListener, ActStay, ActHit, ActYes, and ActNo implement ActionListener. The overall inheritance
hierarchy is a bit segregated:
GUI -> JFrame
 Board -> JPanel -> JFrame (Thus Board and JFrame share a common ancestor of JFrame);

Similarly,
Move, Click, ActStay, ActHit, ActYes, and ActNo share a common ancestor java.util.EventListener, in this hierarchy:
Move -> MouseMotionListener -> EventListener
Click -> mouseListener -> EventListener
ActStay -> ActionListener -> EventListener
ActHit -> ActionListener -> EventListener
ActYes -> ActionListener -> EventListener
ActNo -> ActionListener -> EventListener

Finally, RunBlackJack implements Runnable.

GUI Summary:
The GUI class extends the JFrame class and implements the MouseMotionListener and MouseListener interfaces,
which allows it to handle mouse events in the GUI. The GUI class contains a number of instance variables,
including buttons, fonts, and lists of cards and messages. It also has several boolean variables that keep track of the
game's state, such as whether the player is currently making a move or whether the dealer is thinking.
The GUI class has a constructor that sets up the GUI and adds the game board, which is an instance of the Board class.
The Board class extends the JPanel class and contains the logic for drawing the game board and cards on the screen.
It also contains the logic for handling mouse events and updating the game state based on player actions.

Card Summary:
The Card class represents a card in a deck of playing cards. It has instance variables that store the name, value,
and shape of the card, as well as a boolean variable that indicates whether the card has been used in the game.
The Card class has a constructor that takes three arguments: an integer representing the number on the card,
a string representing the shape of the card, and an integer representing the unique ID of the card. The constructor
uses these arguments to initialize the instance variables of the Card object. The Card class also has two methods,
setUsed() and setNotUsed(), which are used to mark a card as used or not used. The Card class is used in the GUI class,
where it is used to create a list of cards that are available to be dealt in the game of
blackjack.

RunBlackJack Summary:
The RunBlackJack class is a Runnable class that is used to run the game of blackjack.
It contains an instance of the GUI class, and its run() method is used to refresh the game board and repaint the GUI
at a certain rate. The RunBlackJack class also has a main() method that is used to start the game by creating a new
thread and running the run() method in that thread. The RunBlackJack class is used to run the game of blackjack by
creating a new instance of the GUI class and calling its refresher() and repaint() methods at regular intervals to
update the game board and redraw the GUI.

Testing Summary:
These JUnit tests are used to test the behavior of the GUI class in the org.cis1200.blackjack package.
The tests use the assertEquals(), assertTrue(), and assertFalse() methods to check the return values of various
methods in the GUI class, such as the getTitle() and getDealerThinking() methods. The tests also use the setHitStayQ(),
setPlayMoreQ(), setDealerTurn(), setDealerThinking(), setPMaxTotal(), setPMinTotal(), setDMaxTotal(),
and setDMinTotal() methods to set the state of GUI before calling its refresher() method and checking the
values of its instance variables. These tests are used to ensure that the GUI class is functioning correctly. It also
tests the functionality of the Message and Card classes.

File I/O:
Although I did not use File I/O, I used the Try, Catch structure for Thread creation with the following code:
 try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

AI:
I created a "House" computer that plays against the human just like the blackjack game, which I believe achieves the
complex AI component of the game implementation.