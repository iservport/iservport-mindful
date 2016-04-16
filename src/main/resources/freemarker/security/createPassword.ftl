[#ftl]
<!DOCTYPE html>
<html id="ng-app" xmlns:ng="http://angularjs.org" data-ng-app="security">
<head>
    <title>Create password</title>
	[#include "../frame-head.ftl" /]
</head>
<body>
<div id="unauth">
<div id="main" class="container"  data-ng-controller="SecurityController as securityCtrl">
<div class="container-small">

	<div class="clearfix">
		<a href="#" class="text-center" target="_self"><img src="/images/logo.png" alt="iservport" ></a>
	</div>

	<div class="heading">
		<h2>Seu e-mail é [#if email??]${email}[/#if]!</h2>
	</div>
	<script >
		var email = [#if email??]'${email}' [#else]''[/#if];
	</script>
	
    <div class="well well-lg">
			<form name="form"  id="signup" data-ng-submit="updateUser()" >
				
				<div id="form-group-password" class="field">
					<input type="password"  required="" name="password" data-ng-model="form.password" id="password" placeholder="Senha" class="form-control">
				</div>
				<br><div id="messages"></div>
				<div id="form-group-passwordc" class="field">
					<input type="password"  required="" name="cpassword" data-ng-model="cpassword" id="cpassword" placeholder="Confirmação de senha" class="form-control">
				</div>
				<div class="alert alert-danger" role="alert" data-ng-show="cpassword.length>0 && !passwordMatches()" >Senhas não correspondem</div>	
				<button type="submit" class="btn btn-primary" style="width: 100%;" data-ng-show="cpassword.length>0 && passwordMatches()">Criar Senha</button>
			</form>
		</div>
		<div class="footnote"><h3> <a href="/signup/">Criar Conta</a></h3></div>
		
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
	<script type="text/javascript" src="/assets/helianto-security.js"></script>

</div>
</body>
</html>
