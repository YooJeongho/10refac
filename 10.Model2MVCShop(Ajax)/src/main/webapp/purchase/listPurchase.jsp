<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>���� �����ȸ</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>

<script type="text/javascript">

	function fncGetPurchaseList(currentPage) {
		$('#currentPage').val(currentPage);
		//document.detailForm.submit();
		$('form').attr("method", "post").attr("action", "/purchase/listPurchase").submit();
	}
	
	$(function() {
		
		
		// �������� �ҷ�����
		$(".ct_list_pop td:nth-child(1)").on('click', function() {
			var tranNo = $(this).children("input[name='tranNo']").val();
			self.location="/purchase/getPurchase?tranNo="+tranNo;
		});
		
		$( ".ct_list_pop td:nth-child(1)" ).css("color" , "red");
		
		$(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");
		
		// user �ҷ�����
		$( ".ct_list_pop td:nth-child(3)" ).css("color" , "blue");
		$( ".ct_list_pop td:nth-child(3)" ).on("click" , function() {
			self.location ="/user/getUser?userId="+$(this).text().trim();
		});
	})
	
</script>


</head>

<body bgcolor="#ffffff" text="#000000">


<div style="width: 98%; margin-left: 10px;">

<form name="detailForm">

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37"><img src="/images/ct_ttl_img01.gif"width="15" height="37"></td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left: 10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">���� �����ȸ</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"	width="12" height="37"></td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0"	style="margin-top: 10px;">

	<tr>
		<td colspan="11" >��ü ${pageresult.totalCount} �Ǽ�, ���� ${pageresult.currentPage} ������</td>
	</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">ȸ��ID</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">������</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ȭ��ȣ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�����Ȳ</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��������</td>
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
<c:set var = "i" value ="0"/>
	<c:forEach var ="purchase" items="${list}">
		<c:set var = "i" value = "${i+1}"/>
		<tr class="ct_list_pop">
			<td align="center">
			<%-- <a href="/purchase/getPurchase?tranNo=${purchase.tranNo}">${purchase.tranNo}</a> --%>
				${purchase.tranNo}
				<input type="hidden" name="tranNo" value="${purchase.tranNo}">
			</td>
			<td></td>
			<td align="left">
				<%-- <a href="/user/getUser?userId=${purchase.buyer.userId}">${purchase.buyer.userId}</a> --%>
				${purchase.buyer.userId}
			</td>
			<td></td>
			<td align="left">${purchase.receiverName}</td>
			<td></td>
			<td align="left">${purchase.receiverPhone}</td>
			<td></td>
			<td align="left">
				<c:if test="${!(purchase.tranCode eq '0')}">
					<c:if test="${purchase.tranCode.trim() eq '1' }">
						���ſϷ�
					</c:if>
					
					<c:if test="${purchase.tranCode.trim() eq '2' }">
						��� ��
					</c:if>
					
					<c:if test="${purchase.tranCode.trim() eq '3' }">
						��� �Ϸ�
					</c:if>
				</c:if>
			</td>
			<td></td> 
			
   		<td align="left">
   		
   		<c:if test = "${purchase.tranCode.trim() eq '3' }">
			��� �Ϸ�
		</c:if>
   		
		<c:if test = "${purchase.tranCode.trim() eq '2'}">
			<a href="/purchase/updateTranCode?tranNo=${purchase.tranNo}&tranCode=3&page=${pageresult.currentPage}">��ǰ����</a>
		</c:if>
		
		<c:if test = "${purchase.tranCode.trim() eq '1' }">
			��� ��
		</c:if>
		</td>
	</tr>
	
	<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
	</tr>

</c:forEach>		
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
	<tr>
		<td align="center">
			<input type="hidden" id="currentPage" name="currentPage" value="${pageresult.currentPage}"/>
			<jsp:include page="../common/pageNavigatorPurchase.jsp"/>
		</td>
	</tr>
</table>

<!--  ������ Navigator �� -->
</form>

</div>

</body>
</html>