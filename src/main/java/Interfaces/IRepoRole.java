package Interfaces;

import java.util.UUID;

/**
 * Created by raxis on 26.12.2016.
 */
public interface IRepoRole<T> extends IRepo<T> {
    public UUID getId();
    public void setId(UUID id);
    public String getName();
    public void setName(String name);
}
