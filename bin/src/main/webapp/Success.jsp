<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Registration Success</title>
	</head>
	<body>
		<h1>Registration Successful!</h1>
		<p>Thank you for registering with us. Your account has been created successfully.</p>
		<p>Here are your registration details:</p>
		<ul>
			<li>Username: <%= request.getParameter("username") %></li>
			<li>Password: ******** (hidden for security)</li>
		</ul>
	</body>
</html>
