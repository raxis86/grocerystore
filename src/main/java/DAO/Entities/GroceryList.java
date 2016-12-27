package DAO.Entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public class GroceryList {
    private static final Logger logger = LoggerFactory.getLogger(GroceryList.class);

    private UUID id;
    private UUID groceryId;
    private int quantity;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGroceryId() {
        return groceryId;
    }

    public void setGroceryId(UUID groceryId) {
        this.groceryId = groceryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
