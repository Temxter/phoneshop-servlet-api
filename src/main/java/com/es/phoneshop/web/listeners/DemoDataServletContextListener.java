package com.es.phoneshop.web.listeners;

import com.es.phoneshop.model.product.ProductSaveSampleData;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DemoDataServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (servletContextEvent.getServletContext().getInitParameter("insertDemoData").equals("true")) {
            ProductSaveSampleData.saveSampleProducts();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }

}
