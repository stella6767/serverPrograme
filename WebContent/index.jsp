<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	//response.sendRedirect("user/userList.jsp");
	/* RequestDispatcher dis = request.getRequestDispatcher("user?cmd=list&page=0");
	dis.forward(request, response); */
	/* 디스패처 만들면 안 통하는 이유? */
	
	response.sendRedirect("/kang/user?cmd=userList&page=0");
%>
