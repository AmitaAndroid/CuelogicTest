package com.cuelogic.cuelogictest;

public class Product {

    String imageUrl;
    int price;
    String name;
    String vendorName, vendorAddress;

    public int getPrice() {
        return price;
    }

    public Product(int price, String name, String vendorName, String vendorAddress, String imageUrl) {
        this.price = price;
        this.name = name;
        this.vendorName = vendorName;
        this.vendorAddress = vendorAddress;
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }

    public Product() {
    }
}
