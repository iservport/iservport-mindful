<!DOCTYPE html>
<html>
	<head>
		<title>Profile</title>
	</head>
	<body>
		<div id="header">
			<h1><a href="/">Profile</a></h1>
		</div>
		
		<div id="leftNav">
			Left nav menu
		</div>
		
		<div id="content" layout:fragment="content">
			<h3>Your Facebook Profile</h3>
			<p>Hello, <span >${profile.firstName} first name</span>!</p>
			<dl>
				<dt>Facebook ID: ${profile.id}</dt>
				<dt>Name: ${profile.name}</dt>
			</dl>
			
			<form id="disconnect" th:action="@{/connect/facebook}" method="post">
				<input type="hidden" name="_csrf" th:value="${_csrf.token}" />
				<button type="submit">Disconnect from Facebook</button>	
				<input type="hidden" name="_method" value="delete" />
			</form>
		</div>
	</body>
</html>
