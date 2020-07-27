<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="products" type="java.util.ArrayList" scope="request"/>
<tags:master pageTitle="Product List">
    <head>

    </head>
    <p>
        Welcome to Expert-Soft training!
    </p>
    <form method="get">
        <input name="query" type="text" value="${query}">
        <input type="submit" value="Search">
    </form>
    <table>
        <thead>
        <tr>
            <td>Image</td>
            <td>Description
                <tags:sortLink field="DESCRIPTION" order="ASC" query="${param.query}"></tags:sortLink>
                <tags:sortLink field="DESCRIPTION" order="DESC" query="${param.query}"></tags:sortLink>
            </td>
            <td class="price">Price
                <tags:sortLink field="PRICE" order="ASC" query="${param.query}"></tags:sortLink>
                <tags:sortLink field="PRICE" order="DESC" query="${param.query}"></tags:sortLink>
            </td>
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
            </tr>
        </c:forEach>
    </table>
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
    <script src="${pageContext.servletContext.contextPath}/scripts/popUpScript.js"></script>
</tags:master>