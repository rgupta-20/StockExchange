/**
 * Author: Harry Shin
 * Trader Class
 */
import java.util.*;

publicclass Trader implements Comparable<Trader> {
    private Queue messages = new LinkedList<String>();
    private Brokerage brok;
    private String id, pw;

    public Trader(Brokerage brokerage, String name, String pswd) {
        brok = brokerage;
        id = name;
        pw = pswd;
    }
    public String getName() {
        return id;
    }
    public String getPassword() {
        return pw;
    }
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
    public boolean equals(Object other) {
        
        if (this.id.compareToIgnoreCase(other.getName()) == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public void openWindow() {
        TraderWindow myWindow = new TraderWindow(this);
        for (String m : messages) {
            myWindow.showMessage(m);
        }
    }
    public boolean hasMessages() {
        if (messages.size() != 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public void receiveMessage(String msg) {
        messages.add(msg); 
        if (myWindow != null) {
            for (String item : messages) {
                myWindow.showMessage(msg);
            }
        }
    }
    public void getQuote(String symbol) {
        brok.getQuote(symbol, this);
    }
    public void placeOrder(TradeOrder order) {
        brok.placeOrder(order);
    }
    public void quit() {
        brok.logout(this);
        myWindow = null;
    }
}
