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

  public int login (String name, String password) {
    if (!traderList.containsKey(name)) {
      return -1;
    } else if (loggedOnTraders.containsKey(name)) {
      return -3;
    } else if (!(traderList.get(name).getPassword().equals(password))) {
      return -2;
    }
    Trader add = new Trader(this, name, password);
    loggedOnTraders.put(name, add);
    add.receiveMessage("Welcome to SafeTrade!");
    add.openWindow();
    return 0;
  }

  public void logout (Trader trader) {
    loggedOnTraders.remove(trader.getName());
  }

  public void getQuote (String symbol, Trader trader) {
    System.out.println("oh we here here");
    trader.receiveMessage(stockEx.getQuote(symbol));
  }

  public void placeOrder(TradeOrder order) {
    stockEx.placeOrder(order);
  }
}
