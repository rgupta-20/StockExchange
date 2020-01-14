/*
create the stock exchange with methods for adding a stock, getting stock price, and placing an order
author: ronit gupta

*/

import java.util.*;

public class StockExchange {

    private HashMap<String, Stock> stockExchange; 

    public StockExchange() {
        stockExchange = new HashMap<>();
    }

    public void listStock(String symbol, String name, double price) {
        Stock addStock = new Stock(symbol, name, price);
        stockExchange.put(symbol, addStock);
    }

    public String getQuote(String symbol) {
        Stock ourStock = stockExchange.get(symbol);
        return ourStock.getQuote();
    }

    public void placeOrder(TradeOrder tradeOrder) {
        stockExchange.get(tradeOrder.getSymbol()).placeOrder(tradeOrder);
    }
}

