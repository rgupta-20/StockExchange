/**
* Author: Vincent Fan
* Editor: Ronit Gupta
* Class Period: 5
* Description: Creates the Stock object and the ability to execute stock orders depending on whether it is market or limit order.
*/

import java.text.DecimalFormat;
import java.util.PriorityQueue;
import java.lang.Math;

public class Stock {

  public static java.text.DecimalFormat money;
  private String symbol, name; 
  private double lo, hi, last, price;
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
    this.sellOrders = new PriorityQueue<>(new PriceComparator(true));
    this.buyOrders = new PriorityQueue<>(new PriceComparator(false));

    this.money = new DecimalFormat("#,###.00");
  }

  /**
  * Returns a formatted description of the stock, including price and daily statistics.
  */
  public String getQuote() {
    
    String lowestSellPrice = "";
    String lowestSellVolume = "";
    String highestBuyPrice = "";
    String highestBuyVol = "";

    if (sellOrders.isEmpty()) {
      lowestSellPrice = "$" + money.format(this.price);
      lowestSellVolume = "none";
    }

    else {
      lowestSellPrice = "$" + money.format(sellOrders.peek().getPrice());
      lowestSellVolume = "" + sellOrders.peek().getShares();
    }

    if (buyOrders.isEmpty()) {
      highestBuyPrice = "$" + money.format(this.price);
      highestBuyVol = "none";
    }

    else {
      highestBuyPrice = "$" + money.format(buyOrders.peek().getPrice());
      highestBuyVol = "" + buyOrders.peek().getShares();
    }

    return name+" ("+symbol+")\n"+"Price: $"+money.format(price)+" hi: "+hi+" lo: "+ lo+ " vol: " + volume + " Ask: " + lowestSellPrice + " size: " + lowestSellVolume + " Bid: " + highestBuyPrice + " size: " + highestBuyVol;
  
  }
  
  /**
  * Places an order given details of the order.
  */
  public void placeOrder(TradeOrder order) {

    String buyOrSell = "";
    String marketOrPrice = "";

    if (order.isBuy()) {
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
      marketOrPrice = "$" + money.format(order.getPrice());
    }

    order.getTrader().receiveMessage("New order: " + buyOrSell + " " + symbol + " (" + name + ") \n" + order.getShares() + " shares at " + marketOrPrice);

    executeOrders();

  }

  /**
  * Executes all pending orders possible depending on buyers and sellers.
  */
  protected void executeOrders()
  {
    while(!buyOrders.isEmpty()&&!sellOrders.isEmpty())
    {
      TradeOrder currentB = buyOrders.peek();
      TradeOrder currentS = sellOrders.peek();

      if(currentB.isMarket()&&currentS.isMarket())
      {
        if(currentB.getShares()==currentS.getShares())
        {
          currentB.getTrader().receiveMessage("Success! You bought "+currentB.getShares()+ " "+symbol +" at " + money.format(last) + " for a total of $"+money.format(currentB.getShares()*last));
          currentS.getTrader().receiveMessage("Success! You sold "+currentB.getShares()+ " "+symbol +" at " + money.format(last) + " for a total of $"+money.format(currentB.getShares()*last));
          buyOrders.remove();
          sellOrders.remove();
          volume+=currentB.getShares();
        }
        else if(currentB.getShares()>currentS.getShares())
        {
          currentB.getTrader().receiveMessage("Success! You bought "+currentS.getShares()+ " "+symbol +" at " + money.format(last) + " for a total of $"+money.format(currentS.getShares()*last));
          currentS.getTrader().receiveMessage("Success! You sold "+currentS.getShares()+ " "+symbol +" at " + money.format(last) + " for a total of $"+money.format(currentS.getShares()*last));
          sellOrders.remove();
          buyOrders.peek().subtractShares(currentS.getShares());
          volume+=currentS.getShares();
        }
        else
        {
          currentB.getTrader().receiveMessage("Success! You bought "+currentB.getShares()+ " "+symbol +" at " + money.format(last) + " for a total of $"+money.format(currentB.getShares()*last));
          currentS.getTrader().receiveMessage("Success! You sold "+currentB.getShares()+ " "+symbol +" at " + money.format(last) + " for a total of $"+money.format(currentB.getShares()*last));
          buyOrders.remove();
          volume+=currentB.getShares();
          sellOrders.peek().subtractShares(currentB.getShares());
        }
      }
      else if(currentB.isLimit()&&currentS.isMarket())
      {
        if(currentB.getShares()==currentS.getShares())
        {
          currentB.getTrader().receiveMessage("Success! You bought "+currentB.getShares()+ " "+symbol +" at " + money.format(currentB.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentB.getPrice()));
          currentS.getTrader().receiveMessage("Success! You sold "+currentB.getShares()+ " "+symbol +" at " + money.format(currentB.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentB.getPrice()));
          buyOrders.remove();
          sellOrders.remove();
          volume+=currentB.getShares();

        }
        else if(currentB.getShares()>currentS.getShares())
        {
          currentB.getTrader().receiveMessage("Success! You bought "+currentS.getShares()+ " "+symbol +" at " + money.format(currentB.getPrice()) + " for a total of $"+money.format(currentS.getShares()*currentB.getPrice()));
          currentS.getTrader().receiveMessage("Success! You sold "+currentS.getShares()+ " "+symbol +" at " + money.format(currentB.getPrice()) + " for a total of $"+money.format(currentS.getShares()*currentB.getPrice()));
          sellOrders.remove();
          volume+=currentS.getShares();
          buyOrders.peek().subtractShares(currentS.getShares());
        }
        else
        {
          currentB.getTrader().receiveMessage("Success! You bought "+currentB.getShares()+ " "+symbol +" at " + money.format(currentB.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentB.getPrice()));
          currentS.getTrader().receiveMessage("Success! You sold "+currentB.getShares()+ " "+symbol +" at " + money.format(currentB.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentB.getPrice()));
          buyOrders.remove();
          volume+=currentB.getShares();
          sellOrders.peek().subtractShares(currentB.getShares());
        }
        last=currentB.getPrice();
        hi=Math.max(hi, last);
        lo = Math.min(lo, last);
      }
      else if(currentB.isMarket()&&currentS.isLimit())
      {
        if(currentB.getShares()==currentS.getShares())
        {
          currentB.getTrader().receiveMessage("Success! You bought "+currentB.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentS.getPrice()));
          currentS.getTrader().receiveMessage("Success! You sold "+currentB.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentS.getPrice()));
          buyOrders.remove();
          sellOrders.remove();
          volume+=currentB.getShares();

        }
        else if(currentB.getShares()>currentS.getShares())
        {
          currentB.getTrader().receiveMessage("Success! You bought "+currentS.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentS.getShares()*currentS.getPrice()));
          currentS.getTrader().receiveMessage("Success! You sold "+currentS.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentS.getShares()*currentS.getPrice()));
          sellOrders.remove();
          volume+=currentS.getShares();
          buyOrders.peek().subtractShares(currentS.getShares());
        }
        else
        {
          currentB.getTrader().receiveMessage("Success! You bought "+currentB.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentS.getPrice()));
          currentS.getTrader().receiveMessage("Success! You sold "+currentB.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentS.getPrice()));
          buyOrders.remove();
          volume+=currentB.getShares();
          sellOrders.peek().subtractShares(currentB.getShares());
        }
        last=currentS.getPrice();
        hi=Math.max(hi, last);
        lo = Math.min(lo, last);
      }
      else
      {
        if(currentB.getPrice()<currentS.getPrice()) break;
        else
        {
          if(currentB.getShares()==currentS.getShares())
          {
            currentB.getTrader().receiveMessage("Success! You bought "+currentB.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentS.getPrice()));
            currentS.getTrader().receiveMessage("Success! You sold "+currentB.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentS.getPrice()));
            buyOrders.remove();
            sellOrders.remove();
            volume+=currentB.getShares();

          }
          else if(currentB.getShares()>currentS.getShares())
          {
            currentB.getTrader().receiveMessage("Success! You bought "+currentS.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentS.getShares()*currentS.getPrice()));
            currentS.getTrader().receiveMessage("Success! You sold "+currentS.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentS.getShares()*currentS.getPrice()));
            sellOrders.remove();
            volume+=currentS.getShares();
            buyOrders.peek().subtractShares(currentS.getShares());
          }
          else
          {
            currentB.getTrader().receiveMessage("Success! You bought "+currentB.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentS.getPrice()));
            currentS.getTrader().receiveMessage("Success! You sold "+currentB.getShares()+ " "+symbol +" at " + money.format(currentS.getPrice()) + " for a total of $"+money.format(currentB.getShares()*currentS.getPrice()));
            buyOrders.remove();
            volume+=currentB.getShares();
            sellOrders.peek().subtractShares(currentB.getShares());
          }
          last=currentS.getPrice();
          hi=Math.max(hi, last);
          lo = Math.min(lo, last);
        }
      }
    }
  }
}
