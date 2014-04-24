<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%--For displaying Previous link except for the 1st page --%>
    <c:if test="${PageWrapper.getCurrentPage() != 1}">
        <td><a href="dashboard?search=${PageWrapper.getSearch()}&searchby=${PageWrapper.getSearchBy()}&orderby=${PageWrapper.getOrderBy()}&page=${PageWrapper.getCurrentPage() - 1}">Previous</a></td>
    </c:if>
 
    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <table border="1" cellpadding="5" cellspacing="5">
        <tr>
            <c:forEach begin="1" end="${PageWrapper.getNumberOfPages()}" var="i">
                <c:choose>
                    <c:when test="${PageWrapper.getCurrentPage() eq i}">
                        <td>${i}</td>
                    </c:when>
                    <c:otherwise>
                        <td><a href="dashboard?search=${PageWrapper.getSearch()}&searchby=${PageWrapper.getSearchBy()}&orderby=${PageWrapper.getOrderBy()}&page=${i}">${i}</a></td>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </tr>
    </table>
 
    <%--For displaying Next link --%>
    <c:if test="${PageWrapper.getCurrentPage() lt PageWrapper.getNumberOfPages()}">
        <td><a href="dashboard?search=${PageWrapper.getSearch()}&searchby=${PageWrapper.getSearchBy()}&orderby=${PageWrapper.getOrderBy()}&page=${PageWrapper.getCurrentPage() + 1}">Next</a></td>
    </c:if>

