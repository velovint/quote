package io.spring.workshop.tradingservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public class TradingUser {

    @Id
    private String id;

    private String userName;

    private String fullName;

    public TradingUser() {
    }

    public TradingUser(String id, String userName, String fullName) {
        this.id = id;
        this.userName = userName;
        this.fullName = fullName;
    }

    public TradingUser(String userName, String fullName) {
        this.userName = userName;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        TradingUser that = (TradingUser) object;
        return java.util.Objects.equals(id, that.id) &&
                java.util.Objects.equals(userName, that.userName) &&
                java.util.Objects.equals(fullName, that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, userName, fullName);
    }
}