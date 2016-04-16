<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
	  xmlns:social="http://spring.io/springsocial"
	  xmlns:layout="http://www.ultraq.net.nz/thymeleaf/layout"
	  layout:decorator="layout">
	<head>
		<title>Connect</title>
	</head>
	<body>
		<div id="content" layout:fragment="content">
			<h3>Connect to Facebook</h3>
			
			<form action="/connect/facebook" method="POST">
				<input type="hidden" name="_csrf" value="${_csrf.token}" />
				<input type="hidden" name="scope" value="publish_stream,user_photos,offline_access" />
				<div class="formInfo">
					<p>You aren't connected to Facebook yet. Click the button to connect Spring Social Showcase with your Facebook account.</p>
				</div>
				<p><button type="submit">Conectar</button></p>
				<label for="postToWall"><input id="postToWall" type="checkbox" name="postToWall" /> Tell your friends about Spring Social Showcase on your Facebook wall</label>
			</form>
		</div>		
	</body>
</html>
