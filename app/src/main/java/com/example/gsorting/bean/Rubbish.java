package com.example.gsorting.bean;


import java.io.Serializable;

/**
 * 垃圾
 */
public class Rubbish implements Serializable {
    private Integer id;//id
    private String name;//名称
    private Integer typeId;//类型id
    private String type;//类型
    private String content;//内容

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Rubbish(Integer id, String name, Integer typeId, String type, String content) {
        this.id = id;
        this.name = name;
        this.typeId = typeId;
        this.type = type;
        this.content = content;
    }
}
