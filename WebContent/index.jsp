<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	//response.sendRedirect("user/userList.jsp");
/* 	RequestDispatcher dis = request.getRequestDispatcher("user?cmd=list&page=0");
	dis.forward(request, response); */
	/* 디스패처 만들면 안 통하는 이유? */
			
/* 	디스패처는 내부에서 파일을 찾는 용도지, 식별자를 못 찾음.
	식별자를 찾을려면 sendRedirect로 찾아야 됨.  아니면 필터에서 예외로 index까지 바로 갈 수있도록 설정*/
	
	response.sendRedirect("/kang/user?cmd=userList&page=0");
%>
