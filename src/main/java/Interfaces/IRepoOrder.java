package Interfaces;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by raxis on 26.12.2016.
 */
public interface IRepoOrder<T> extends IRepo<T> {
    public List<T> selectByUserId(UUID userid);
    public UUID getId();
    public void setId(UUID id);
    public UUID getUserid();
    public void setUserid(UUID userid);
    public UUID getOrderstatusid();
    public void setOrderstatusid(UUID orderstatusid);
    public UUID getGrocerylistid();
    public void setGrocerylistid(UUID grocerylistid);
    public BigDecimal getPrice();
    public void setPrice(BigDecimal price);
    public Date getDatetime();
    public void setDatetime(Date datetime);
    public String getAddress();
    public void setAddress(String address);
}
