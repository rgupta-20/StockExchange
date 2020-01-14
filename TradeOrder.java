/**
* Author: Ronit Gupta
* Editor: Vincent Fan
* Class Period: 5
* Description: Trade order object that contains information about the order, including trader, stock symbol, number of shares, order classification, and price.
*/

public class TradeOrder {

    private Trader trader;
    private String symbol;
    private boolean buyOrder;
    private boolean marketOrder;
    private int numShares;
    private double price;

    public TradeOrder(Trader trader, String symbol, boolean buyOrder, boolean marketOrder, int numShares, double price) {
        this.trader = trader;
        this.symbol = symbol;
        this.buyOrder = buyOrder;
        this.marketOrder = marketOrder;
        this.numShares = numShares;
        this.price = price;
    }

    /**
     * Getter methods to return fields.
     */
    public double getPrice() {
        return price;
    }

    public int getShares() {
        return numShares;
    }

    public String getSymbol() {
        return symbol;
    }

    public Trader getTrader() {
        return trader;
    }

    public boolean isBuy() {
        return buyOrder;
    }

    public boolean isLimit() {
        return !isMarket();
    }

    public boolean isMarket() {
        return marketOrder;
    }

    public boolean isSell() {
        return !isBuy();
    }

    /**
     * Edits field for number of shares.
     */
    public void subtractShares(int shares) {
        numShares = numShares - shares;
    }

}