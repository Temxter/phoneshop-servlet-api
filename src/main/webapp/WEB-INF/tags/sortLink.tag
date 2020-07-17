<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="field" required="true" %>
<%@ attribute name="order" required="true" %>
<%@ attribute name="query" required="true" %>

<a href="?field=${field}&order=${order}&query=${query}">${order}</a>
