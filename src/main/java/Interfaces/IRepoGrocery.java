package Interfaces;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by raxis on 26.12.2016.
 */
public interface IRepoGrocery<T> extends IRepo<T> {
    public UUID getId();
    public void setId(UUID id);
    public UUID getParentid();
    public void setParentid(UUID parentid);
    public boolean isIscategory();
    public void setIscategory(boolean iscategory);
    public String getName();
    public void setName(String name);
    public int getQuantity();
    public void setQuantity(int quantity);
    public BigDecimal getPrice();
    public void setPrice(BigDecimal price);
}
