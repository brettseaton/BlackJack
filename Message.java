package org.cis1200.blackjack;

public class Message {

    String message = "empty_for_now";
    String person = "nobody_for_now";

    public Message(String m, String w) {
        this.message = m;
        this.person = w;
    }

    public String getMessage() {
        return this.message;
    }

    public String getWho() {
        return this.person;
    }

}
