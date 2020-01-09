import java.util.*;
import Stock;

public class StockExchange {

    public StockExchange() {
        HashMap<String, Stock> stockExchange = new HashMap<>();
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
        Stock stockOrder = stockExchange.get(tradeOrder.getSymbol);
        stockOrder.placeOrder();
    }
}

