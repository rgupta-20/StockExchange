/**
 * Author: Ronit Gupta
 * Editor: Vincent Fan
 * Class Period: 5
 * Description: Creates the Stock Exchange with methods for adding a stock, getting stock price, and placing an order.
 */

 import java.util.*;

public class StockExchange {

    private HashMap<String, Stock> stockExchange; 

    public StockExchange() {
        stockExchange = new HashMap<>();
    }

    /**
     * Adds a stock to the stock exchange.
     */
    public void listStock(String symbol, String name, double price) {
        Stock addStock = new Stock(symbol, name, price);
        stockExchange.put(symbol, addStock);
    }

    /**
     * Returns the stock price of a specified stock.
     */
    public String getQuote(String symbol) {
        Stock ourStock = stockExchange.get(symbol);
        return ourStock.getQuote();
    }

    /**
     * Places a stock order given details of the trade order.
     */

    public void placeOrder(TradeOrder tradeOrder) {
        stockExchange.get(tradeOrder.getSymbol()).placeOrder(tradeOrder);
    }
}

