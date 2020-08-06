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


<%--    ${paramValues}--%>


    <form action="${pageContext.request.contextPath}/cart" method="POST">
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
                               value="${not empty paramValues["quantity"][item.product.id]
                               ? paramValues["quantity"][item.product.id]
                               : item.quantity}">
                        <input type="hidden" value="${item.product.id}" name="productId" >
                        <c:if test="${not empty errors[item.product.id.longValue()]}">
                            <p class="error">${errors[item.product.id.longValue()]}</p>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div>
            <button>
                Update
            </button>
        </div>
    </form>
</tags:master>