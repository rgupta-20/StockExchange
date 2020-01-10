//Author: Vincent Fan

//Explanation

import java.text.DecimalFormat;

public class Stock {

  public static java.text.DecimalFormat money;

  private String symbol, String name, double prices;

  private double lo, double high, double last;

  private int volume;

  private PriorityQueue<TradeOrder> sellorders;
  private PriorityQueue<TradeOrder> buyorders;

  public Stock(String symbol, String name, double price)
  {
    this.symbol=symbol;
    this.name=name;
    this.price=price;
    this.lo=price;
    this.high=price;
    this.last=price;
    this.volume=0;
    this.sellorders = new PriorityQueue<>(Comparator<TradeOrder> PriceComparator);
    this.buyorders = new PriorityQueue<>(Comparator<TradeOrder> PriceComparator);

    this.money = new DecimalFormat(" #,###.##")
  }

  public String getQuote()
  {
    return name+" ("+symbol+")\n"+"Price: "+price+" hi:"+hi+" lo:"+ lo+
  }

  public void placeOrder(TradeOrder order)
  {

  }

}
