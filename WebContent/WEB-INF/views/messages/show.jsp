<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">

        <h2>Page that message detail; id : ${message.id} </h2>

        <p>Title: <c:out value="${message.title}" /></p>
        <p>Message: <c:out value="${message.content}" /></p>
        <p>Date created: <fmt:formatDate value="${message.created_at}" pattern="MM/dd/YYYY HH:mm:ss" /></p>
        <p>Date updated: <fmt:formatDate value="${message.updated_at}" pattern="MM/dd/YYYY HH:mm:ss" /></p>
        <p><a href="${pageContext.request.contextPath}/index">Back to list</a></p>
    </c:param>
</c:import>
