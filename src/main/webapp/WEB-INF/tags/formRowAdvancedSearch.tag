<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" %>
<%@ attribute name="nameAttribute" required="true" %>
<%@ attribute name="value" required="true" %>
<%@ attribute name="errors" required="true" type="java.util.Map"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<tr>
    <c:set var="error" value="${not empty errors ? errors[nameAttribute] : null}"/>
    <td>${name}</td>
    <td>
        <input name="${nameAttribute}"
               value="${value}">
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>
    </td>
</tr>