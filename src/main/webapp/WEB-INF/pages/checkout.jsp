<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="order" type="com.es.phoneshop.model.order.Order" scope="request"/>
<tags:master pageTitle="Checkout">
    <head>
    </head>

    <c:if test="${not empty errors}">
        <p class="error">Fail update order</p>
    </c:if>

    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="quantity">Quantity</td>
            <td class="price">Price</td>
        </tr>
        </thead>
        <c:forEach var="item" items="${order.itemList}">
            <tr>
                <td>
                    <img class="product-tile" src="${item.product.imageUrl}">
                </td>
                <td><a href="${pageContext.request.contextPath}/products/${item.product.id}">${item.product.description}</a></td>
                <td class="quantity">${item.quantity}</td>
                <td class="price">
                    <fmt:formatNumber value="${item.product.productPrice.price}" type="currency"
                                      currencySymbol="${item.product.productPrice.currency.symbol}"/>
                </td>
            </tr>
        </c:forEach>
        <tr class="price total">
            <td></td>
            <td></td>
            <td>Subtotal</td>
            <td>
                <fmt:formatNumber value="${order.subtotal}" type="currency"
                                  currencySymbol="${order.currency.symbol}"/>
            </td>
        </tr>
        <tr class="price total">
            <td></td>
            <td></td>
            <td>Delivery</td>
            <td>
                <fmt:formatNumber value="${order.deliveryCost}" type="currency"
                                  currencySymbol="${order.currency.symbol}"/>
            </td>
        </tr>
        <tr class="price total">
            <td></td>
            <td></td>
            <td>Total cost</td>
            <td>
                <fmt:formatNumber value="${order.totalCost}" type="currency"
                                  currencySymbol="${order.currency.symbol}"/>
            </td>
        </tr>
    </table>
    <h2>Your details</h2>
    <form action="${pageContext.request.contextPath}/checkout" method="POST">
        <table>
            <tags:formRowCheckout name="First name" nameAttribute="firstName" order="${order}" errors="${errors}"></tags:formRowCheckout>
            <tags:formRowCheckout name="Last name" nameAttribute="lastName" order="${order}" errors="${errors}"></tags:formRowCheckout>
            <tags:formRowCheckout name="Phone" nameAttribute="phone" order="${order}" errors="${errors}"></tags:formRowCheckout>
            <tags:formRowCheckout name="Delivery date" nameAttribute="deliveryDate" order="${order}" errors="${errors}" isDate="true"></tags:formRowCheckout>
            <tags:formRowCheckout name="Delivery address" nameAttribute="deliveryAddress" order="${order}" errors="${errors}"></tags:formRowCheckout>
            <tr>
                <td>Payment method<span style="color: red">*</span></td>
                <td>
                    <select name="paymentMethod">
                        <option></option>
                        <c:forEach var="paymentMethod" items="${paymentMethodsList}">
                            <option
                                ${not empty order.paymentMethod
                                && paymentMethod == order.paymentMethod
                                ? "selected" : ""}
                            >${paymentMethod}</option>
                        </c:forEach>
                    </select>
                    <c:if test="${not empty errors['paymentMethod']}">
                        <p class="error">${errors['paymentMethod']}</p>
                    </c:if>
                </td>
            </tr>
        </table>
        <input value="Submit" type="submit">
    </form>
</tags:master>