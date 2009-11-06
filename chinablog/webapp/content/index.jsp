<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.naming.NamingException" %>
<%@ page import="org.six11.chinablog.DatabaseThing" %>
<%@ page import="org.six11.chinablog.Post" %>
<%@ page import="org.six11.chinablog.Author" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.util.Collection" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>The Erstwhile China Blog</title>
<link rel="stylesheet" href="style.css" TYPE="text/css">

<% 

   InitialContext ic = new InitialContext();
   DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/good");
   DatabaseThing dbThing = new DatabaseThing(ds);

	Collection<Post> posts = Post.getPosts(dbThing);
        request.setAttribute("posts", posts);
%>
</head>

<body>

		<c:forEach items="${posts}" var="p">

			 <div class="postTop">
				<div class="title">${p.title}</div>

				<span class="authorName">${p.authorName}</span>

				<span class="date">${p.date}</span>
                         </div>
				<div class="content">${p.content}</div>

				<div class="space"></div>

		</c:forEach>
</body>
</html>
