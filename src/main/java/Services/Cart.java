package Services;

import Models.Grocery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by raxis on 25.12.2016.
 */
public class Cart {
    private static final Logger logger = LoggerFactory.getLogger(Cart.class);

    private Map<Grocery,Integer> map = new HashMap<>();

    public Map<Grocery, Integer> getMap() {
        return map;
    }

    public void addItem(Grocery grocery, Integer quantity){
        Integer sum=map.put(grocery,quantity);
        if(sum!=null){
            map.put(grocery,quantity+sum);
        }
    }

    public void removeItem(Grocery grocery){
        map.remove(grocery);
    }

    public BigDecimal computeTotalPrice(){
        BigDecimal priceSum = BigDecimal.valueOf(0);
        BigDecimal price=BigDecimal.valueOf(0);
        BigDecimal quan=BigDecimal.valueOf(0);

        for(HashMap.Entry entry: map.entrySet()){
            price=((Grocery)entry.getKey()).getPrice();
            quan=BigDecimal.valueOf((Integer)entry.getValue());
            priceSum=priceSum.add(price.multiply(quan));
        }
        return priceSum;
    }

    public void clear(){
        map.clear();
    }


}
