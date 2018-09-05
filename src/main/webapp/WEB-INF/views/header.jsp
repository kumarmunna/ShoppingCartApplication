<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet" type="text/css" />
</head>
<body>
	<h2>Online Shopping Cart</h2>
	<!-- <sec:authentication property="principal.authorities"/> -->
	
	<a href="${pageContext.request.contextPath}/productList.html">Product
		List</a> |
	<a href="${pageContext.request.contextPath}/getCart.html">My Cart</a> |
	<c:if
		test="${sessionScope.userstatus eq 'auth'}">
		<a href="${pageContext.request.contextPath}/getOrderListByUser.html"> I ordered</a> |
    </c:if>
	<c:if
		test="${(sessionScope.userstatus eq 'auth') && ((sessionScope.userrole eq 'MANAGER') || (sessionScope.userrole eq 'EMPLOYEE'))}">
		<a href="${pageContext.request.contextPath}/getOrderList.html">Order
			List</a> |
    </c:if>
	<c:if
		test="${(sessionScope.userstatus eq 'auth') && ((sessionScope.userrole eq 'MANAGER'))}">
		<a href="${pageContext.request.contextPath}/createProduct.html">Create
			Product</a> |
    </c:if>
	<c:if test="${sessionScope.userstatus ne 'auth'}">
		<a href="${pageContext.request.contextPath}/admin">Login</a>  |
				<a href="${pageContext.request.contextPath}/register"> User
			Registration </a>
	</c:if>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<a href="${pageContext.request.contextPath}/GetProfile.html"> My
			Profile </a> |
	</c:if>
	<c:if test="${pageContext.request.userPrincipal.name != null}">
		<a href="<c:url value="/logout" />" > Logout</a>  
	</c:if>
	<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	<hr />
</body>
</html>