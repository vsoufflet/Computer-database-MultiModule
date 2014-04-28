<jsp:include page="include/header.jsp" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<section id="main">
	<spring:message code="language" />
	<a href="?language=en">English</a> | <a href="?language=fr">Français</a>
	<form action="login" method="post"
		action="<c:url value='j_spring_security_check'/>">
		<spring:message code="login" />
		<input type="text" name="j_username" id="j_username" />
		<spring:message code="password" />
		<input type="password" name="j_password" id="j_password" /> <input
			type="submit" value=<spring:message code="connect"/>
			class="btn primary">
	</form>

</section>
<jsp:include page="include/footer.jsp" />