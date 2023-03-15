<%@ page contentType="text/html; charset=euc-kr" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<c:if test="${ pageresult.currentPage <= pageresult.pageUnit }">
			�� ����
	</c:if>
	<c:if test="${ pageresult.currentPage > pageresult.pageUnit }">
			<a href="javascript:fncGetPurchaseList('${ pageresult.currentPage-1}')">�� ����</a>
	</c:if>
	
	<c:forEach var="i"  begin="${pageresult.beginUnitPage}" end="${pageresult.endUnitPage}" step="1">
		<a href="javascript:fncGetPurchaseList('${ i }');">${ i }</a>
	</c:forEach>
	
	<c:if test="${ pageresult.endUnitPage >= pageresult.maxPage }">
			���� ��
	</c:if>
	<c:if test="${ pageresult.endUnitPage < pageresult.maxPage }">
			<a href="javascript:fncGetPurchaseList('${pageresult.endUnitPage+1}')">���� ��</a>
	</c:if>