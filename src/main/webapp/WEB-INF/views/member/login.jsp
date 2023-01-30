<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	Cookie[] cookies = request.getCookies();
	String saveId = null;
	if(cookies != null){
		for(Cookie cookie : cookies){
			String name = cookie.getName();
			String value = cookie.getValue();
			if("saveId".equals(name))
				saveId = value;
		}
	}
%>
<% if(loginMember != null){ %>
<script src="<%= request.getContextPath() %>/js/ws.js"></script>
<% } %>
<script>
window.addEventListener('load', () => {
	
	<% if(msg != null) { %>
		alert("<%= msg %>"); // alert(아이디가 존재하지 않거나 비밀번호가 틀립니다.)
	<% } %>
	
	<% if (loginMember == null) { %>
	document.loginFrm.addEventListener('submit', (e) => {
		const memberId = document.querySelector("#memberId");
		const password = document.querySelector("#password");
		
		if(!/^[A-Za-z0-9]{6,}$/.test(memberId.value)){
			alert("유효한 아이디를 입력하세요.");
			memberId.select();
			e.preventDefault();
			return;
		}
		
		if(!/^[A-Za-z0-9!@#$%]{8,12}$/.test(password.value)){
			alert("유효한 비밀번호를 입력하세요.");
			password.select();
			e.preventDefault();
			return;
		}
	});
	<% } %>
	
});
</script>
<section id=login>
	<h2>로그인</h2><br>
	<p>
	<span class=login_jawa>카페 자와</span> 에 오신 것을 환영합니다 ! </p>
	<form id="loginFrm" name="loginFrm" action="<%= request.getContextPath() %>/member/login" method="POST">
		<table>
			<tr>
				<td><input type="text" name="memberId" id="memberId" placeholder="아이디를 입력해주세요" tabindex="1" required value="<%= saveId != null ? saveId : "" %>"></td>
				<td><input type="submit" value="로그인" tabindex="3"></td>
			</tr>
			<tr>
				<td><input type="password" name="password" id="password" placeholder="비밀번호를 입력해주세요" tabindex="2" required></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<input type="checkbox" name="saveId" id="saveId" <%= saveId != null ? "checked" : "" %>>
					<label for="saveId">아이디 저장</label>
				</td>
			</tr>
			<tr>
				<td>
					<input type="button" value="회원가입" onclick="location.href = '<%= request.getContextPath() %>/member/memberEnroll';">&nbsp;&nbsp;
					<input type="button" value="아이디 찾기" onclick="location.href = '<%= request.getContextPath() %>/member/findId';">&nbsp;&nbsp;
					<input type="button" value="비밀번호 찾기" onclick="location.href = '<%= request.getContextPath() %>/member/findPassword';">					
			</tr>
		</table>
	</form>







</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>