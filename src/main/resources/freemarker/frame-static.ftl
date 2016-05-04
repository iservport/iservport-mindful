[#ftl]
<!DOCTYPE html >
<html id="ng-app" xmlns:ng="http://angularjs.org"
    data-ng-app="${baseName!'static'}"
    [#if baseName?? ]
        data-ng-controller="${baseName?capitalize}StaticController as ${baseName}StaticCtrl"
    [#else]
        data-ng-controller="StaticController as staticCtrl"
    [/#if]

    data-ng-cloak>

<head>
    <meta content="text/html; iso-8859-1" http-equiv="content-type">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
    [#include "/frame-head.ftl" /]

    <script type="text/javascript">var externalId = ${(externalId!0)?c};</script>
    <link type="image/x-icon" href="/static/images/favicon.ico" rel="shortcut icon">
    <link type="image/x-icon" href="/static/images/favicon.ico" rel="icon">

    <title>${title!''}</title>

</head>
<body id="app" class="app ng-animate">

	<!-- 
	 ! Menu
	 ! -->
	[#if externalBrand??]
	<div class="header clearfix ng-scope">
	<nav>
		<ul class="nav nav-pills pull-right">
			<li role="presentation" class="active"><a href="/home">Login</a></li>
			<li role="presentation"><a href="/signup">Criar conta</a></li>
		</ul>
	</nav>
	<h3 class="text-muted">${externalBrand}</h3>
	</div>
	[/#if]
	
	<!-- 
	 ! Conteúdo principal
	 ! -->
	<div class="main-container">
	
		[#if staticContent??][#include "${staticContent}.html"/][/#if]
		[#if staticInclude??][#include "${baseName!''}/${staticInclude}.html"/][/#if]
		
	</div><!--main-container-->

	[#include "/_js.html" /]
    [#include "/_custom.html" /]
	
</body>

</html>
