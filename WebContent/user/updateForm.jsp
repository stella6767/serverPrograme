<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<form action="/kang/user?cmd=update" method="post">
	<table border = "1">
		<tr>
			<th>번호</th>
			<th>유저네임</th>
			<th>패스워드</th>
			<th>이메일</th>
			<th>권한</th>
			<th>생성날짜</th>
		</tr>
		
		<tr>	
			<td>${sessionScope.principal.id}
				<input type ="hidden" name="id" value ="${sessionScope.principal.id}">
			</td>
			<td>
				<input type ="username" name="username" value ="${sessionScope.principal.username}">
			</td>
				
			<td>
				<input type ="password" name="password" value ="${sessionScope.principal.password}">
			</td>
			<td>
				<input name="email" value ="${sessionScope.principal.email}">
			</td>
			<td>${sessionScope.principal.userRole}
				<input type ="hidden" name="userRole" value="${sessionScope.principal.userRole}">
			</td>
			<td>${sessionScope.principal.createDate}
				<input type ="hidden" name="createDate" value="${sessionScope.principal.createDate}">
			</td>
		</tr>
	
	</table>
	<button>회원수정</button>
</form>

<form action ="user?cmd=delete" method="post">
	<input type="hidden" name="id" value="${sessionScope.principal.id}"/>
	<button>삭제</button>
</form>

</body>
</html> 