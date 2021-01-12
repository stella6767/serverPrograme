<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<!-- JSTL foreach문을 써서 뿌리세요. el표현식과 함께 -->
<div class="container">

	<div class="progress col-md-12 m-2">
		<div class="progress-bar" style="width:  ${currentPosition}%"></div>
	</div>

	<c:forEach var="user" items="${users}">
		<div class="card col-md-12 m-2 mb-5 ">
			<div class="card-header">
				<h4 class="card-title">유저이름: ${user.username}</h4>
			</div>
			<div class="card-body d-flex justify-content-between">
				<table border="1">
					<tr>
						<th>권한</th>
						<th>이메일</th>
						<th>생성날짜</th>
					</tr>

					<tr>
						<td>${user.userRole}</td>
						<td>${user.email}</td>
						<td>${user.createDate}</td>
					</tr>
				</table>
				<div>
					<c:if test="${sessionScope.principal.userRole eq 'admin'}">
						<i onclick="deleteUser(${user.id})" class="material-icons ml-auto">delete</i>
					</c:if>
				</div>
			</div>


		</div>
	</c:forEach>


	<br />
	<!-- disabled -->
	<ul class="pagination justify-content-center">

		<c:choose>
			<c:when test="${param.page == 0}">
				<li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="/kang/user?cmd=userList&page=${param.page-1}">Previous</a></li>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="${lastPage == param.page}">
				<li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="/kang/user?cmd=userList&page=${param.page+1}">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>

<script>

function deleteUser(id){

	$.ajax({
		url : "/kang/user?cmd=deleteByAdmin&id="+id,

	}).done(function(result) {
		location.reload();
	});

	
}



</script>


</body>
</html>

