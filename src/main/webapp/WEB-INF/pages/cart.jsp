<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <head>
    </head>

    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description</td>
            <td class="price">Price</td>
            <td>Quantity</td>
        </tr>
        </thead>
        <c:forEach var="item" items="${cart.itemList}">
            <tr>
                <td>
                    <img class="product-tile" src="${item.product.imageUrl}">
                </td>
                <td><a href="${pageContext.request.contextPath}/products/${item.product.id}">${item.product.description}</a></td>
                <td class="price">
                    <fmt:formatNumber value="${item.product.productPrice.price}" type="currency"
                                      currencySymbol="${item.product.productPrice.currency.symbol}"/>
                </td>
                <td>
                    <input class="right-text" id="quantity" name="quantity"
                           value="${item.quantity}">
                    <input type="hidden" value="${item.product.id}" name="productId" >
                </td>
            </tr>
        </c:forEach>
    </table>
</tags:master>