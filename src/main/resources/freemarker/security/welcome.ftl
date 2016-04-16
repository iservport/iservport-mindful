[#ftl]
<!DOCTYPE html>
<html id="ng-app" xmlns:ng="http://angularjs.org" data-ng-app="security">
<head>
    <title>Welcome</title>
	[#include "../frame-head.ftl" /]
</head>
<body>
<div id="unauth">
<div id="main" class="container"  data-ng-controller="SecurityController as securityCtrl">
<div class="container-small">

	<div class="clearfix">
		<a href="#" class="text-center" target="_self"><img src="/images/logo.png" alt="iservport" ></a>
	</div>
	<script >
		var email = [#if email??]'${email}' [#else]''[/#if];
	</script>
	<div class="heading">
		<h2>Bem-vindo</h2>
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
	<script type="text/javascript" src="/assets/helianto-security.js"></script>

</div>
</body>
</html>
