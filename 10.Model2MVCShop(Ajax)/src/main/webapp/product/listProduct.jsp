<%@ page contentType="text/html; charset=euc-kr" %>

<%@ page import="java.util.*"  %>
<%@ page import="com.model2.mvc.service.domain.*" %>
<%@ page import="com.model2.mvc.common.*" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="EUC-KR">
	<title>��ǰ �����ȸ</title>
	
	<link rel="stylesheet" href="/css/admin.css" type="text/css">
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	<!-- cdn -->
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/css/bootstrap-theme.min.css" integrity="sha384-6pzBo3FDv/PJ8r2KRkGHifhEocL+1X2rVCTTkUfGk7/0pbek5mMa1upzvWbrUbOZ" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.4.1/dist/js/bootstrap.min.js" integrity="sha384-aJ21OjlMXNL5UyIl/XNwTMqvzeRMZH2w8c5cRVpzpU8Y5bApTppSuUkhZXN0VxHd" crossorigin="anonymous"></script>
	<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
	
	
	<script type="text/javascript">
		function fncGetUserList(currentPage) {
			//document.getElementById("currentPage").value = currentPage;
			$('#currentPage').val(currentPage)
		   	//document.detailForm.submit();
			$('form').attr("method", "post").attr("action", "/product/listProduct?menu=${requestScope.menu}").submit();
		}
		
		
		
		//�ٸ� ��� (�˻�, ������ )
		$(function() {
			
			$('td.ct_btn01:contains("�˻�")').on('click', function() {
				fncGetUserList('1');
			});
			
			
			
			let prodNo = $("input[name='prodNo']");
			let prodNoLen = prodNo.length;
			$(".ct_list_pop td:nth-child(3)").on('click', function() {
				
				console.log(prodNoLen);
				var text = $(this).children("input[name='prodNo']").val();
				console.log(text);
				self.location="/product/getProduct?menu=${menu}&prodNo="+text;	
					
				/* self.location= */
			})
		});
		
		
	</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form>

<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37">
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>	
					<td width="93%" class="ct_ttl01">${requestScope.menu eq 'manage' ? '��ǰ ����' : '��ǰ ���' }</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${! empty search.searchCondition && search.searchCondition == 0 ? "selected" : "" }>��ǰ��ȣ</option>
				<option value="1" ${! empty search.searchCondition && search.searchCondition == 1 ? "selected" : "" }>��ǰ��</option>
				<option value="2" ${! empty search.searchCondition && search.searchCondition == 2 ? "selected" : "" }>��ǰ����</option>
			</select>
			<input 	type="text" name="searchKeyword"  value="${! empty search.searchKeyword ? search.searchKeyword : "" }" 
							class="ct_input_g" style="width:200px; height:19px" >
		</td>
		
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23">
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						�˻�
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23">
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >��ü  ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������</td>
	</tr>
	
	<tr>
		<td class="ct_list_b" width="100">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">��ǰ��</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">����</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">��ϳ�¥</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">�������</td>		
	</tr>
	
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
	
	
<c:set var="i" value="0" />
	<c:forEach var="product" items="${list}">
		<c:set var="i" value="${ i+1 }" />
		<tr class="ct_list_pop">
			<td align="center">
				<img alt="��ǰ�̹���" src="/images/uploadFiles/${fileName[i-1]}" onerror="this.style.display='none'" height="180" width="180"/>
				
			</td>
			<td></td>
			<td align="left">
				${product.prodName}
				<input type="hidden" name="prodNo" value="${product.prodNo}"/>
			</td>
			<td></td>
			<td align="left">${product.price}</td>
			<td></td>
			<td align="left">${product.regDate}</td>
			<td></td>
			<td align="left">
				<c:if test="${sessionScope.user.role eq 'admin'}">
					<c:if test="${product.proTranCode.trim() eq '0'}">�Ǹ���</c:if>
					<c:if test="${product.proTranCode.trim() eq '1'}">
						���ſϷ� 
						<a href="/purchase/updateTranCodeByProd.do?prodNo=${product.prodNo}&tranCode=2">����ϱ�</a>
					</c:if>
					
					<c:if test="${product.proTranCode.trim() eq '2'}">��� ��</c:if>
					<c:if test="${product.proTranCode.trim() eq '3'}">��� �Ϸ�</c:if>
				</c:if>
				
				<c:if test="${sessionScope.user.role eq 'user'}">
					<c:if test="${product.proTranCode.trim() eq '0'}">�Ǹ���</c:if>
					
					<c:if test="${product.proTranCode.trim() eq '1'}">ǰ��</c:if>
						 
					<c:if test="${product.proTranCode.trim() eq '2'}">ǰ��</c:if>
					<c:if test="${product.proTranCode.trim() eq '3'}">ǰ��</c:if>
				</c:if>
					
					
			</td>
		</tr>
		
		<tr>
		<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>
		
	</c:forEach>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="center">
		<input type="hidden" id="currentPage" name="currentPage" value=""/>
		<jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  ������ Navigator �� -->
</form>
</div>

</body>
</html>