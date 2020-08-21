<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:master pageTitle="Error">
    <p>${pageContext.errorData.statusCode}. That’s an error.</p>
    <p><a href="${pageContext.request.contextPath}">Return to main page</a></p>
</tags:master>
