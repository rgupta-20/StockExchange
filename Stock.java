//Author: Vincent Fan
//Author: Ronit Gupta

//Explanation

import java.text.DecimalFormat;
import java.util.PriorityQueue;

import TradeOrder;

public class Stock {

  public static java.text.DecimalFormat money;

  private String symbol, String name, double prices;

  private double lo, double hi, double last;

  private int volume;

  private PriorityQueue<TradeOrder> sellOrders;
  private PriorityQueue<TradeOrder> buyOrders;

  public Stock(String symbol, String name, double price)
  {
    this.symbol=symbol;
    this.name=name;
    this.price=price;
    this.lo=price;
    this.hi=price;
    this.last=price;
    this.volume=0;
    this.sellOrders = new PriorityQueue<>(Comparator<TradeOrder> PriceComparator(true));
    this.buyOrders = new PriorityQueue<>(Comparator<TradeOrder> PriceComparator(false));
    //true is ascending

    this.money = new DecimalFormat(" #,###.##")
  }

  public String getQuote() {

    double lowestSellPrice = 0;
    String lowestSellVolume = "";
    double highestBuyPrice = 0;
    String highestBuyVol = "";

    if (sellOrders.isEmpty()) {
      lowestSellPrice = this.price;
      lowestSellVolume = "none";
    }

    if (buyOrders.isEmpty()) {
      highestBuyPrice = this.price;
      highestBuyVol = "none"
    }

    lowestSellPrice = sellOrders.peek().getPrice();
    lowestSellVolume = "" + sellOrders.peek().getShares();
    highestBuyPrice = buyOrders.peek().getPrice();
    highestBuyVol = "" + sellOrders.peek().getShares();
    

    return name+" ("+symbol+")\n"+"Price: "+price+" hi: "+hi+" lo: "+ lo+ " vol: " + volume + " Ask: " + lowestSellPrice + " size: " + lowestSellVolume + " Bid: " + highestBuyPrice + " size: " + highestBuyVol;
  
  }

  public void placeOrder(TradeOrder order) {

    String buyOrSell = "";
    String marketOrPrice = "";

    if (order.buyOrder()) {
      buyOrders.add(order);
      buyOrSell = "Buy";
    }

    else {
      sellOrders.add(order);
      buyOrSell = "Sell";
    }

    if (order.isMarket()) {
      marketOrPrice = "market";
    }
    
    else {
      money.format(order.getPrice());
      marketOrPrice = "" + order.getPrice();
    }

    System.out.println("New order: " + buyOrSell + " " + symbol + " (" + name + ")");
    System.out.println(order.getShares() + " shares at " + marketOrPrice)



  }

}
