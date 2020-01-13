/**
 * Author: Harry Shin
 * Trader Class
 */
import java.lang.Math;
import java.util.*;

class PriceComparator implements Comparator<TradeOrder> {
    private boolean ascending;
    public PriceComparator() {
        ascending = true;
    }
    public PriceComparator(boolean asc) {
        ascending = asc;
    }
    public int compare(TradeOrder order1, TradeOrder order2) {
        if (!(order1.isLimit()) && !(order2.isLimit())) {
            return 0;
        }
        else if (!(order1.isLimit()) && order2.isLimit()) {
            return -1;
        }
        else if (!(order2.isLimit()) && order1.isLimit()) {
            return 1;
        }
        else {
            int d = (int) (Math.round((order1.getPrice() - order2.getPrice()) * 100.0) / 100.0);
            if (ascending) {
                return d;
            }
            else {
                return -d;
            }
        }
    }

}
