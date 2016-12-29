package Domain.Entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public class Order implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Order.class);

    private UUID id;
    private UUID userid;
    private UUID orderstatusid;
    private UUID grocerylistid;
    private BigDecimal price;
    private Date datetime;
    private String address;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserid() {
        return userid;
    }

    public void setUserid(UUID userid) {
        this.userid = userid;
    }

    public UUID getOrderstatusid() {
        return orderstatusid;
    }

    public void setOrderstatusid(UUID orderstatusid) {
        this.orderstatusid = orderstatusid;
    }

    public UUID getGrocerylistid() {
        return grocerylistid;
    }

    public void setGrocerylistid(UUID grocerylistid) {
        this.grocerylistid = grocerylistid;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
