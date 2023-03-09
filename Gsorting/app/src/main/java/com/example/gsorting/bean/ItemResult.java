package com.example.gsorting.bean;

import java.io.Serializable;

public class ItemResult implements Serializable {
    private String id;
    private String itemName;
    private String itemCategory;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemCategory() {
        return itemCategory;
    }

}
