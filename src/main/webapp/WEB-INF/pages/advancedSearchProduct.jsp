<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Advanced search page">
    <head>

    </head>
    <h1>Advanced search page</h1>

    <form method="get" action="${pageContext.request.contextPath}/advancedSearchProducts">
        <table>
        <tags:formRowAdvancedSearch name="Product code" nameAttribute="productCode" value="${productCode}"
                                    errors="${errors}"></tags:formRowAdvancedSearch>
        <tags:formRowAdvancedSearch name="Min price" nameAttribute="minPrice" value="${minPrice}"
                                    errors="${errors}"></tags:formRowAdvancedSearch>
        <tags:formRowAdvancedSearch name="Max price" nameAttribute="maxPrice" value="${maxPrice}"
                                    errors="${errors}"></tags:formRowAdvancedSearch>
        <tags:formRowAdvancedSearch name="Min Stock" nameAttribute="minStock" value="${minStock}"
                                    errors="${errors}"></tags:formRowAdvancedSearch>
        </table>
        <p></p>
        <input type="submit" value="Search">
    </form>

    <c:if test="${not empty message}">
        <p class="success">${message}</p>
    </c:if>

    <c:if test="${empty errors}">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td class="price">Price</td>
                <td>Product code</td>
                <td>Stock</td>
            </tr>
            </thead>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>
                        <img class="product-tile" src="${product.imageUrl}">
                    </td>
                    <td><a href="${pageContext.request.contextPath}/products/${product.id}">${product.description}</a></td>
                    <td class="price">
                      <div class="popup">
                        <fmt:formatNumber value="${product.productPrice.price}" type="currency"
                                          currencySymbol="${product.productPrice.currency.symbol}"/>
                        <span class="popuptext">
                          <h3>Price history</h3>
                          <h4>${product.description}</h4>
                            <table>
                                <thead>
                                    <tr>
                                        <td>Start date</td>
                                        <td>Price</td>
                                    </tr>
                                </thead>
                                <c:forEach var="price" items="${product.priceList}">
                                    <tr>
                                        <td><fmt:formatDate value="${price.date}" pattern="dd MMM yyyy"/></td>
                                        <td><fmt:formatNumber value="${price.price}" type="currency"
                                                              currencySymbol="${price.currency.symbol}"/></td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </span>
                      </div>
                    </td>
                    <td>${product.code}</td>
                    <td>${product.stock}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <script src="${pageContext.servletContext.contextPath}/scripts/popUpScript.js"></script>
</tags:master>