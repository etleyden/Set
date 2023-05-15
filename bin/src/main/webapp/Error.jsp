<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Registration Error</title>
</head>
<body>
	<h1>Registration Error</h1>
	<p>We're sorry, but an error occurred during the registration process. Please try again later.</p>
	<p>Error message: <%= request.getAttribute("errorMessage") %></p>
	<p>If the problem persists, please contact our support team at support@yourwebsite.com.</p>
</body>
</html>
