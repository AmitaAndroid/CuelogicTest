package com.cuelogic.cuelogictest;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{

    String imageUrl;
    int price;
    String name;
    String vendorName, vendorAddress;
    String phoneNo;
//    int isInCart;

/*
    public int isInCart() {
        return isInCart;
    }

    public void setInCart(int inCart) {
        isInCart = inCart;
    }
*/

    public Product(int price, String name, String vendorName, String vendorAddress, String imageUrl, String phoneNo) {
        this.price = price;
        this.name = name;
        this.vendorName = vendorName;
        this.vendorAddress = vendorAddress;
        this.imageUrl = imageUrl;
        this.phoneNo = phoneNo;
    }

    protected Product(Parcel in) {
        imageUrl = in.readString();
        price = in.readInt();
        name = in.readString();
        vendorName = in.readString();
        vendorAddress = in.readString();
        phoneNo = in.readString();
//        isInCart=in.readInt();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imageUrl);
        dest.writeInt(price);
        dest.writeString(name);
        dest.writeString(vendorName);
        dest.writeString(vendorAddress);
        dest.writeString(phoneNo);
//        dest.writeInt(isInCart);

    }
}
