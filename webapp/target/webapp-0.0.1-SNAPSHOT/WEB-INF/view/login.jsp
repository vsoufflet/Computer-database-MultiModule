<jsp:include page="include/header.jsp" />

<form method="post" id="loginForm"
	action="<c:url value="j_spring_security_check"/>">
	<input type="text" name="j_username" id="j_username" /> 
	<input type="password" name="j_password" id="j_password" />
</form>

<jsp:include page="include/footer.jsp" />