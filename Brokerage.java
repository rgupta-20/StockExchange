/**
* Author: Jack Diodati
* Editors: Ronit Gupta, Vincent Fan
* Class Period: 5
* Description: Brokerage that holds users and and places orders.
*/

import java.util.*;

public class Brokerage implements Login {

  public TreeMap<String, Trader> traderList;
  public TreeMap<String, Trader> loggedOnTraders;
  public StockExchange stockEx;

  public Brokerage (StockExchange exchange) {
    traderList = new TreeMap<String, Trader>();
    loggedOnTraders = new TreeMap<String, Trader>();
    stockEx = exchange;
  }

  /**
   * Adds a user to the brokerage.
   */
  public int addUser (String name, String password) {
    if (name.length() < 4 || name.length() > 10) {
      return -1;
    } else if (password.length() < 2 || password.length() > 10) {
      return -2;
    } else if (traderList.containsKey(name)) {
      return -3;
    }
    Trader add = new Trader(this, name, password);
    traderList.put(name, add);
    return 0;
  }

  /**
   * Logs a user in to the brokerage.
   */
  public int login (String name, String password) {
    if (!traderList.containsKey(name)) {
      return -1;
    } else if (loggedOnTraders.containsKey(name)) {
      return -3;
    } else if (!(traderList.get(name).getPassword().equals(password))) {
      return -2;
    }
    Trader add = traderList.get(name);
    loggedOnTraders.put(name, add);
    if (!add.hasMessages()) {
      add.receiveMessage("Welcome to SafeTrade!");
    }
    add.openWindow();
    return 0;
  }

  /**
   * Logs a user out of the brokerage.
   */
  public void logout (Trader trader) {
    loggedOnTraders.remove(trader.getName());
  }

  /**
   * Sends a message to a trader with the quote for a stock.
   */
  public void getQuote (String symbol, Trader trader) {
    trader.receiveMessage(stockEx.getQuote(symbol));
  }

  /**
   * Places an order given order details.
   */
  public void placeOrder(TradeOrder order) {
    stockEx.placeOrder(order);
  }
}
