package Interfaces;

import java.util.UUID;

/**
 * Created by raxis on 26.12.2016.
 */
public interface IRepoUser<T> extends IRepo<T> {
    public T selectOne(String email, String passwordHash);

    public UUID getId();

    public void setId(UUID id);

    public UUID getRoleID();

    public void setRoleID(UUID roleID);

    public String getName();

    public void setName(String name);

    public String getEmail();

    public void setEmail(String email);

    public String getPassword();

    public void setPassword(String password);

    public String getLastName();

    public void setLastName(String lastName);

    public String getSurName();

    public void setSurName(String surName);

    public String getAddress();

    public void setAddress(String address);

    public String getPhone();

    public void setPhone(String phone);
}
