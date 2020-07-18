<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="${product.description}">
<a href="${pageContext.request.contextPath}">All products</a>

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
        <td><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></td>
    </tr>
    <tr>
        <td>Stock</td>
        <td>${product.stock}</td>
    </tr>
</table>
</tags:master>
