package cn.xbmchina.entity;

import java.io.Serializable;

public class UavEntity implements Serializable{

    private String id;//id
    private String name;//名称
    private String brand;//品牌


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    @Override
    public String toString() {
        return "UavEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }
}
