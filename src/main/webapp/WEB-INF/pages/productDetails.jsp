<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="${product.description}">
<a href="${pageContext.request.contextPath}">All products</a>
    <p class="success">${message}</p>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
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
    <form method="post">
        <input class="right-text" id="quantity" name="quantity" value="1">
        <label for="quantity">Quantity: </label>
        <input type="submit" value="Add to cart">
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
    </form>
</tags:master>
