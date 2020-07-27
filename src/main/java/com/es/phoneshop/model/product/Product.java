package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Product {
    private Long id;
    private String code;
    private String description;
    private List<ProductPrice> priceList;
    private ProductPrice currentPrice;
    private int stock;
    private String imageUrl;

    public Product() {
        priceList = new ArrayList<>();
    }

    public Product(Long id, String code, String description, BigDecimal price, Currency currency, int stock, String imageUrl) {
        this(id, code, description, null, stock, imageUrl);
        this.newPrice(new ProductPrice(price, currency));
    }

    public Product(Long id, String code, String description, List<ProductPrice> priceList, int stock, String imageUrl) {
        this(code, description, priceList, stock, imageUrl);
        this.id = id;
    }

    public Product(String code, String description, List<ProductPrice> priceList, int stock, String imageUrl) {
        this.code = code;
        this.description = description;
        this.stock = stock;
        this.imageUrl = imageUrl;
        if (priceList == null) {
            this.priceList = new ArrayList<>();
        } else {
            this.priceList = priceList;
            currentPrice = priceList.get(priceList.size() - 1);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductPrice> getPriceList() {
        return priceList;
    }

    public ProductPrice getProductPrice() {
        return currentPrice;
    }

    private void setCurrentPrice(ProductPrice currentPrice) {
        this.currentPrice = currentPrice;
    }

    public void newPrice(ProductPrice price) {
        setCurrentPrice(price);
        priceList.add(price);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            Product otherProduct = (Product) obj;
            if (otherProduct.id == this.id
                && otherProduct.code == this.code
                && otherProduct.stock == this.stock
                && otherProduct.description.equals(this.description)
                && otherProduct.imageUrl.equals(this.imageUrl)
                && otherProduct.currentPrice.equals(this.currentPrice)
                && otherProduct.priceList.equals(this.priceList)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result
                + id
                + code
                + stock
                + description == null ? 0 : description.hashCode()
                + imageUrl == null ? 0 : imageUrl.hashCode();
        return result;
    }
}