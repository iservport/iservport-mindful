<!DOCTYPE html >
<html id="ng-app" xmlns:ng="http://angularjs.org" data-ng-app="${baseName}" data-ng-cloak>

<head>
    <meta content="text/html; iso-8859-1" http-equiv="content-type">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
    <!-- Styles -->
	<!-- Bootstrap style -->
	<link rel='stylesheet' href='/webjars/bootstrap/3.3.2/css/bootstrap.min.css'>
	<!-- Morris style -->
	<link rel="stylesheet" href="/webjars/morrisjs/0.5.0/morris.css">
	<!-- font-awesome -->
	<link rel='stylesheet' href="/webjars/font-awesome/4.3.0/css/font-awesome.css">
	<!-- font-ionicons -->
	<link rel='stylesheet' href="/webjars/ionicons/1.5.2/css/ionicons.min.css">
	<!-- font-foundation -->
	<link rel='stylesheet' href="/webjars/foundation-icon-fonts/d596a3cfb3/foundation-icons.css">
	<!--[if lte IE 7]>
		<script src="/webjars/json3/3.3.2/json3.min.js"></script>
	<![endif]-->
	<!-- iservport css extension -->
	<link rel='stylesheet' href='/css/iservport.css'>
	<!-- cidadaonoplenario css -->
	<link rel='stylesheet' href='/css/cidadaonoplenario.css'>

    <!-- Javascript -->
    <!-- JQuery -->
	<script type="text/javascript" src="/webjars/jquery/2.1.1/jquery.min.js"></script>
	<!-- Bootstrap package -->
	<script type="text/javascript" src="/webjars/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<!-- Knob -->
	<script type="text/javascript" src="/webjars/jquery-knob/1.2.2/jquery.knob.min.js"></script>
	<!-- Morris -->
	<script src="/webjars/raphaeljs/2.1.2/raphael-min.js"></script>
	<script src="/webjars/morrisjs/0.5.0/morris.min.js"></script>
    <script type="text/javascript">var externalId = ${externalId!0};</script>
    
	<link type="image/x-icon" href="/images/favicon.ico" rel="shortcut icon">
	<link type="image/x-icon" href="/images/favicon.ico" rel="icon">

    <title>${title!''}</title>
	<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>
<body id="app" 
	data-ng-controller="${baseName?capitalize}Controller as ${baseName}Ctrl"
	data-ng-include="'/assets/frame-body-${layoutSize!'2'}col.html'"
	class="app">
	
	<!-- AngularJs package -->
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular.min.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular-sanitize.min.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular-resource.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular-route.min.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular-cookies.min.js"></script>
	<script type="text/javascript" src="/webjars/angularjs/1.3.1/angular-animate.min.js"></script>
	<script type="text/javascript" src="/webjars/angular-ui-bootstrap/0.11.2/ui-bootstrap-tpls.js"></script>
	<script type="text/javascript" src="/webjars/angular-ui-utils/0.1.1/ui-utils.min.js"></script>
    <!-- Services to apps-->
	<script type="text/javascript" src="/assets/helianto-security.js"></script>
	<script type="text/javascript" src="/assets/stats.js"></script>
	<script type="text/javascript" src="/assets/${baseName}/ng-${baseName}-module.js"></script>
	<input type="hidden" id="_csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
	

</body>

</html>
