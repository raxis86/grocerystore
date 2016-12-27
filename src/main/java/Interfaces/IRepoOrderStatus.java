package Interfaces;

import java.util.UUID;

/**
 * Created by raxis on 26.12.2016.
 */
public interface IRepoOrderStatus<T> extends IRepo<T> {
    public UUID getId();
    public void setId(UUID id);
    public String getStatus();
    public void setStatus(String name);
}
