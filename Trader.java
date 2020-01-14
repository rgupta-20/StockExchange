/**
 * Author: Harry Shin
 * Trader Class
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
        
        if (this.id.compareToIgnoreCase(((Trader) other).getName()) == 0) {
            return true;
        }
        else {
            return false;
        }
    }
    public void openWindow() {
        myWindow = new TraderWindow(this);
        System.out.println(myWindow==null);
        System.out.println("oh we here now bitch");
        for (Object m : messages) {
            myWindow.showMessage((String) m);
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
        System.out.println("we are here13456789");
        if (myWindow != null) {
            myWindow.showMessage((String) messages.peek());
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
