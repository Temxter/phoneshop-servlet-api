package com.es.phoneshop.web.servlets;

import com.es.phoneshop.enums.PaymentMethod;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.services.CartService;
import com.es.phoneshop.services.OrderService;
import com.es.phoneshop.services.impl.DefaultCartService;
import com.es.phoneshop.services.impl.DefaultOrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CheckoutPageServlet extends HttpServlet {

    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        super.init();
        cartService = DefaultCartService.getInstance();
        orderService = DefaultOrderService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req);
        req.setAttribute("order", orderService.getOrder(cart));
        req.setAttribute("paymentMethodsList", orderService.getPaymentMethodsList());
        req.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cart cart = cartService.getCart(req);
        Order order = orderService.getOrder(cart);

        Map<String, String> errors = new HashMap<>();

        setRequiredParameter(req, "firstName", errors, order::setFirstName);
        setRequiredParameter(req, "lastName", errors, order::setLastName);
        setRequiredParameter(req, "phone", errors, order::setPhone);
        setRequiredParameter(req, "deliveryAddress", errors, order::setDeliveryAddress);
        setDeliveryDate(req, errors, order);
        setPaymentMethod(req, errors, order);

        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.setAttribute("order", order);
            req.setAttribute("paymentMethodsList", orderService.getPaymentMethodsList());
            req.getRequestDispatcher("/WEB-INF/pages/checkout.jsp").forward(req, resp);
        } else {
            orderService.placeOrder(order);
            cartService.clear(req);
            resp.sendRedirect(String.format("%s/order/overview/%s", req.getContextPath(), order.getSecureId()));
        }
    }

    private void setDeliveryDate (HttpServletRequest req, Map<String, String> errors, Order order) {
        String parameter = "deliveryDate";
        String value = req.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            try {
                LocalDate localDate = LocalDate.parse(value);
                order.setDeliveryDate(localDate);
            } catch (DateTimeParseException e) {
                errors.put(parameter, "Date format is not correct. Format example: 1799-05-26");
            }
        }
    }

    private void setPaymentMethod (HttpServletRequest req, Map<String, String> errors, Order order) {
        String parameter = "paymentMethod";
        String value = req.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            PaymentMethod paymentMethod = PaymentMethod.valueOf(value);
            order.setPaymentMethod(paymentMethod);
        }
    }

    private void setRequiredParameter(HttpServletRequest req, String parameter, Map<String, String> errors,
                                      Consumer<String> consumer) {
        String value = req.getParameter(parameter);
        if (value == null || value.isEmpty()) {
            errors.put(parameter, "Value is required");
        } else {
            consumer.accept(value);
        }
    }
}
