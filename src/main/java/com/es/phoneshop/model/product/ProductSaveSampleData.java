package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;

public class ProductSaveSampleData {

    private ProductSaveSampleData() { }

    public static void saveSampleProducts() {
        ArrayListProductDao productDao = ArrayListProductDao.getInstance();
        Currency usd = Currency.getInstance("USD");

        List<ProductPrice> priceSgs = new ArrayList<>();
        priceSgs.add(new ProductPrice(new BigDecimal(200), usd, new Date(100 + 18, 10, 23)));
        priceSgs.add(new ProductPrice(new BigDecimal(250), usd, new Date(100 + 19, 1, 1)));
        priceSgs.add(new ProductPrice(new BigDecimal(100), usd, new Date(100 + 20, 3, 17)));
        List<ProductPrice> priceIphone = new ArrayList<>();
        priceIphone.add(new ProductPrice(new BigDecimal(600), usd, new Date(100 + 07, 9, 1)));
        priceIphone.add(new ProductPrice(new BigDecimal(400), usd, new Date(100 + 10, 2, 2)));
        priceIphone.add(new ProductPrice(new BigDecimal(150), usd, new Date(100 + 19, 3, 15)));
        List<ProductPrice> priceSgs3 = new ArrayList<>();
        priceSgs3.add(new ProductPrice(new BigDecimal(700), usd, new Date(100 + 12, 9, 1)));
        priceSgs3.add(new ProductPrice(new BigDecimal(550), usd, new Date(100 + 15, 2, 2)));
        priceSgs3.add(new ProductPrice(new BigDecimal(300), usd));

        productDao.save(new Product(1L, "sgs", "Samsung Galaxy S", priceSgs, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        productDao.save(new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg"));
        productDao.save(new Product(3L, "sgs3", "Samsung Galaxy S III", priceSgs3, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg"));
        productDao.save(new Product(4L, "iphone", "Apple iPhone", priceIphone, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        productDao.save(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productDao.save(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productDao.save(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productDao.save(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productDao.save(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));
        productDao.save(new Product(10L, "palmp", "Palm Pixi", new BigDecimal(170), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));
        productDao.save(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        productDao.save(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));
        productDao.save(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 40, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));
    }

}
