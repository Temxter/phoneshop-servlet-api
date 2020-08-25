<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="cart" type="com.es.phoneshop.model.cart.Cart" scope="request"/>
<tags:master pageTitle="Cart">
    <head>
    </head>

    <c:if test="${not empty errors}">
        <p class="error">Fail update cart</p>
    </c:if>
    <c:if test="${not empty param.message}">
        <p class="success">${param.message}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/cart" method="POST">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="quantity">Quantity</td>
                <td class="price">Price</td>
            </tr>
            </thead>
            <c:forEach var="item" items="${cart.itemList}">
                <tr>
                    <td>
                        <img class="product-tile" src="${item.product.imageUrl}">
                    </td>
                    <td><a href="${pageContext.request.contextPath}/products/${item.product.id}">${item.product.description}</a></td>
                    <td>
                        <input class="quantity" id="quantity" name="quantity"
                               value="${not empty paramValues["quantity"][item.product.id.intValue()]
                               ? paramValues["quantity"][item.product.id.intValue()]
                               : item.quantity}">
                        <input type="hidden" value="${item.product.id}" name="productId" >
                        <c:if test="${not empty errors[item.product.id.longValue()]}">
                            <p class="error">${errors[item.product.id.longValue()]}</p>
                        </c:if>
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${item.product.productPrice.price}" type="currency"
                                          currencySymbol="${item.product.productPrice.currency.symbol}"/>
                    </td>
                    <td>
                        <button form="deleteForm" formaction="${pageContext.request.contextPath}/deleteProduct/${item.product.id}">
                            Delete</button>
                    </td>
                </tr>
            </c:forEach>
            <tr class="total">
                <td></td>
                <td>Total</td>
                <td class="quantity">${cart.totalQuantity}</td>
                <td class="price">
                    <fmt:formatNumber value="${cart.totalCost}" type="currency"
                                      currencySymbol="${cart.currency.symbol}"/>
                </td>
            </tr>
        </table>
        <p></p>
        <div>
            <button>
                Update
            </button>
        </div>
    </form>
    <form action="${pageContext.request.contextPath}/checkout">
        <button>
            Checkout
        </button>
    </form>
    <form id="deleteForm" method="POST"></form>
</tags:master>