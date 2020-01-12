import java.util.*;
import StockExchange;
import Trader;


public class Brokerage {

  public TreeMap<String, Trader> traderList;
  public TreeMap<String, Trader> loggedOnTraders;

  public Brokerage (StockExchange exchange) {
    traderList = new TreeMap<String, Trader>();
    loggedOnTraders = new TreeMap<String, Trader>();
  }

  public int addUser (String name, String password) {
    if (name.length() < 4 || name.length() > 10) {
      return -1;
    } else if (password.length() < 2 || password.length() > 10) {
      return -2;
    } else if (traderList.containsKey(name)) {
      return -3;
    }
    Trader add = new Trader(this.Brokerage, name, password);
    traderList.put(name, add);
    return 0;
  }

  public int login (String name, String password) {
    if (!traderList.containsKey(name)) {
      return -1;
    } else if (loggedOnTraders.containsKey(name)) {
      return -3;
    } else if (traderList.get(name).getPassword().equals(password)) {
      return -2;
    }
    Trader add = new Trader(this.Brokerage, name, password);
    loggedOnTraders.put(name, add);
    return 0;
  }

  public void logout (Trader trader) {
    loggedOnTraders.remove(Trader.getName());
  }

  public void getQuote (String symbol, Trader trader) {
    trader.receiveMessage(trader.getQuote(symbol));
  }

  public void placeOrder(TradeOrder order) {
    StockExchange.placeOrder(order));
  }
}
