<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration </title>
<style type="text/css">
.error {
	color: red;
}
</style>
</head>
<body>
<c:if test="${empty success }">
<h1> You are not registered. Please register yourself </h1>
</c:if>
<c:out value="${success }"></c:out>
<form:form action="userRegistration" modelAttribute="userdetails">
<pre>
			Name: <form:input path="name"/> <form:errors path="name" cssClass="error" />
			Address:<form:input path="address"/> <form:errors path="address" cssClass="error" />
			Phone:<form:input path="phone"/> <form:errors path="phone" cssClass="error" />
			Email ID:<form:input path="email"/> <form:errors path="email" cssClass="error" />
			UserID:<form:input path="username"/> <form:errors path="username" cssClass="error" />
			Password:<form:input path="password"/> <form:errors path="password" cssClass="error" />
			
			<input type="submit" value="Register">
			<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
</pre>

</form:form>


</body>
</html>