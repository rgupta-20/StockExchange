/**
* Author: Harry Shin
* Editors: Ronit Gupta, Vincent Fan
* Class Period: 5
* Description: Create a Trader object with the ability to place orders and receive messages.
*/
import java.util.*;

public class Trader implements Comparable<Trader> {
    private Queue messages;
    private Brokerage brok;
    private String id, pw;
    private TraderWindow myWindow;

    public Trader(Brokerage brokerage, String name, String pswd) {
        brok = brokerage;
        id = name;
        pw = pswd;
        messages = new LinkedList<String>();
        myWindow = null;
    }

    /**
    * Returns the trader's name.
    */
    public String getName() {
        return id;
    }

    /**
    * Return's the trader's password.
    */
    public String getPassword() {
        return pw;
    }

    /**
    * Compares two traders depening on their names.
    */
    public int compareTo(Trader other) {
        int diff = this.id.toLowerCase().compareTo(other.id.toLowerCase());
        if (diff == 0) {
            return 0;
        }
        else if (diff > 0) {
            return 1;
        }
        else {
            return -1;
        }
    }

    /**
    * Checks if two traders have the same name.
    */
    public boolean equals(Object other) {
        
        if (this.id.compareToIgnoreCase(((Trader) other).getName()) == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
    * Opens a trader's message window and shows any messages they have.
    */
    public void openWindow() {
        myWindow = new TraderWindow(this);
        while (!messages.isEmpty()) {
            myWindow.showMessage((String) messages.remove());
        }
    }

    /**
    * Checks if a trader has any messages.
    */
    public boolean hasMessages() {
        if (messages.size() != 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
    * Adds messages to trader's inbox and displays those messages to the trader.
    */
    public void receiveMessage(String msg) {
        messages.add(msg);
        if (myWindow != null) {
            while (!messages.isEmpty()) {
                myWindow.showMessage((String) messages.remove());
            }
        }
    }

    /**
    * Finds the quote for a stock given its symbol.
    */
    public void getQuote(String symbol) {
        brok.getQuote(symbol, this);
    }

    /**
    * Places an order given order details.
    */
    public void placeOrder(TradeOrder order) {
        brok.placeOrder(order);
    }

    /**
    * Logs out a user.
    */
    public void quit() {
        brok.logout(this);
        myWindow = null;
    }
}
