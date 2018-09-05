<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<c:choose>
	<c:when test="${not empty emptyProduct}">
	
			<c:choose>
				<c:when test="${sessionScope.userrole eq 'MANAGER'}">
					<h3>${emptyProduct} </h3>
					Click <a href="${pageContext.request.contextPath}/createProduct.html" >here</a> to add products.
				</c:when>
				<c:otherwise>
					<h3>${emptyProduct} </h3>
				</c:otherwise>
			</c:choose>
			
		</c:when>
		
		<c:otherwise>
			<c:forEach items="${productList}" var="item">
				<div style="float: left; border: 1px solid red;">
					<img
						src="${pageContext.request.contextPath}/resources/images/${item.filename}" /><br>
					<div style="padding-left: 2em; padding-bottom: 1em;">
						Code: ${item.code}<br> Name: ${item.name}<br> Price:
						${item.price}<br> <a
							href="${pageContext.request.contextPath}/addInCart.html?productCode=${item.code}">Add
							to cart </a><br>

						<c:if test="${sessionScope.userrole eq 'MANAGER'}">
							<a
								href="${pageContext.request.contextPath}/editProduct?productCode=${item.code}">Edit
								Product </a>
							<br>
							<a
								href="${pageContext.request.contextPath}/deleteProduct.html?productCode=${item.code}">Delete
								Product </a>
						</c:if>
					</div>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>

	

	<br>
</body>
</html>