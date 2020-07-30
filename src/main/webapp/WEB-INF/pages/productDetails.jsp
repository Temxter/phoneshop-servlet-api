<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="${product.description}">
<a href="${pageContext.request.contextPath}">All products</a>
    <p>${cart}</p>

    <p class="success">${param.message}</p>
    <c:if test="${not empty error}">
        <p class="error">Product not added to cart: incorrect value of quantity. ${error}</p>
    </c:if>
    <h1>${product.description}</h1>
<table>
    <tr>
        <td>Image</td>
        <td><img src="${product.imageUrl}"></td>
    </tr>
    <tr>
        <td>Code</td>
        <td>${product.code}</td>
    </tr>
    <tr>
        <td>Price</td>
        <td class="right-text"><fmt:formatNumber value="${product.productPrice.price}" type="currency"
                              currencySymbol="${product.productPrice.currency.symbol}"/></td>
    </tr>
    <tr>
        <td>Stock</td>
        <td class="right-text">${product.stock}</td>
    </tr>
</table>
    <form method="POST" action="${pageContext.request.contextPath}/products/${product.id}">
        <div>
            <label for="quantity">Quantity: </label>
            <input class="right-text" id="quantity" name="quantity"
                   value="${not empty param.quantity
                   ? param.quantity
                   : 1}">
            <div>
                <input type="submit" value="Add to cart">
            </div>
            <c:if test="${not empty error}">
                <p class="error">${error}</p>
            </c:if>
        </div>
    </form>
    <c:if test="${not empty recentlyViewedProducts}">
        <h2>Recently viewed</h2>
        <div class="viewed-items">
            <c:forEach var="product" items="${recentlyViewedProducts}">
            <div class="viewed-item">
                <img class="product-tile" src="${product.imageUrl}">
                <p><a href="${pageContext.request.contextPath}/products/${product.id}">${product.description}</a></p>
                <p><fmt:formatNumber value="${product.productPrice.price}" type="currency"
                                                   currencySymbol="${product.productPrice.currency.symbol}"/></p>
            </div>
            </c:forEach>
        </div>
    </c:if>
</tags:master>
