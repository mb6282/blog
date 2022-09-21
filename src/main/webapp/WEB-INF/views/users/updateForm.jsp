<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br />

	<form action="/users/${users.id}/delete" method="post">
		<button class="btn btn-danger">회원탈퇴</button>
	</form>
	<form action="/users/${users.id}/update" method="post">

		<div class="mb-3 mt-3">
			<input type="text" class="form-control" placeholder="Enter username" value="${users.username}"
				readonly="readonly">
		</div>
		<div class="mb-3">
			<input id="password" type="password" class="form-control" placeholder="Enter password"
				value="${users.password}" name="password">
		</div>
		<div class="mb-3">
			<input id="email" type="email" class="form-control" placeholder="Enter email"
				value="${users.email}" name="email">
		</div>
		<button type="submit" class="btn btn-primary">회원수정완료</button>
	</form>
</div>

<%@ include file="../layout/footer.jsp"%>
