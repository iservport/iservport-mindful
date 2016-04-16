[#ftl]
<!DOCTYPE html>
<html >
<head>
    <title>Login</title>
	[#include "../frame-head.ftl" /]
</head>
<body>
<div id="unauth">
<div id="main" class="container"  >
<div class="container-small">

	<div class="clearfix">
		<a href="#" class="text-center" target="_self"><img src="/images/logo.png" alt="iservport" ></a>
	</div>

	<div class="heading">

	</div>
	<script >
		var email = [#if email??]'${email}' [#else]''[/#if];
	</script>
    <div class="well well-lg">
		<form  name="form"  id="signup" action="/signup" method="POST">
			<input type="hidden" name="_csrf" value="${_csrf.token}" />
			
			<!--div id="form-group-email" class="form-group">
				<label for="firstName">Email</label>
				<input type="email" name="email" required="true" id="email" 
				data-ng-model="form.email" data-ng-blur="saveEmail(form.email)" placeholder="E-mail" class="form-control">
			</div>
			<div id="form-group-email" class="form-group">
				<label for="firstName">Primeiro nome</label>
				<input type="text" required="true" name="firstName" data-ng-model="form.firstName" id="firstName"  placeholder="Primeiro nome" class="form-control">
			</div>
			<div id="form-group-email" class="form-group">
				<label for="lastName">Sobrenome</label>
				<input type="text" required="true" name="lastName" data-ng-model="form.lastName" id="lastName"  placeholder="Sobrenome" class="form-control">
			</div-->
			
			<div id="form-group-email" class="form-group">
				<label for="firstName">Email</label>
				<input type="email" name="principal" value="${form.principal}" required="true" placeholder="E-mail" class="form-control">
			</div>
			<div id="form-group-email" class="form-group">
				<label for="firstName">Primeiro nome</label>
				<input type="text" name="firstName" required="true" value="${form.firstName}"  placeholder="Primeiro nome" class="form-control">
			</div>
			<div id="form-group-email" class="form-group">
				<label for="lastName">Sobrenome</label>
				<input type="text" name="lastName" required="true" value="${form.lastName}" placeholder="Sobrenome" class="form-control">
			</div>
			
			<br><div id="messages"></div>
			
			<button type="submit" class="btn btn-primary" style="width: 100%;" >Envie e-mail de confirmação</button>

		</form>
	</div>
		
</div>
</div>
<!-- AngularJs package -->
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular.min.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular-sanitize.min.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular-resource.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular-route.min.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular-cookies.min.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/i18n/angular-locale_pt-br.js"></script>
	<script type="text/javascript" src="/webjars/angular-ui-bootstrap/0.11.2/ui-bootstrap-tpls.js"></script>
	<script type="text/javascript" src="/webjars/angular-ui-utils/0.1.1/ui-utils.min.js"></script>

	<script type="text/javascript" src="/assets/security/ng-security-module.js"></script>
	<script type="text/javascript" src="/assets/stats.js"></script>

</div>
</body>
</html>
