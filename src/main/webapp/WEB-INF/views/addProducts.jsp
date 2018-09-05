<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Products</title>
</head>
<body>
	<c:if test="${not empty success}">
		<div class="error">${success}</div>
	</c:if>
	<c:if test="${not empty error }">
		<div class="error">${error }</div>
	</c:if>
	<pre>
	<form:form action="addProduct?${_csrf.parameterName}=${_csrf.token}"
			method="post" modelAttribute="productbean"
			enctype="multipart/form-data">
			
			<form:hidden path="code" name="code" value="${productbean.code }" />		
			Code: <input type="text" disabled="true" value="${productbean.code }" />
			Name:<form:input path="name" name="name" /><form:errors path="name" cssClass="error" />
			Price: <form:input path="price" name="price" /><form:errors path="price" cssClass="error" />
			Image: <form:input path="data" name="data" type="file" />
			<br />
			<input type="submit" value="Submit">
			  <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
	</form:form>
</pre>


</body>
</html>