package DAO.Entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by raxis on 27.12.2016.
 */
public class OrderStatus {
    private static final Logger logger = LoggerFactory.getLogger(OrderStatus.class);

    private UUID id;
    private String status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
