<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<tags:master pageTitle="${product.description}">
<a href="${pageContext.request.contextPath}">All products</a>

<table>
    <tr>
        <td>Description</td>
        <td>${product.description}</td>
    </tr>
    <tr>
        <th>Image</th>
        <th><img src="${product.imageUrl}"></th>
    </tr>
    <tr>
        <td>Code</td>
        <td>${product.code}</td>
    </tr>
    <tr>
        <th>Price</th>
        <th><fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/></th>
    </tr>
    <tr>
        <th>Stock</th>
        <th>${product.stock}</th>
    </tr>
</table>
</tags:master>
