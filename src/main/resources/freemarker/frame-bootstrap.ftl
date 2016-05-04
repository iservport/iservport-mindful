[#ftl]
<!DOCTYPE html >
<html id="ng-app" xmlns:ng="http://angularjs.org" 
    data-ng-app="${baseName}" 
    data-ng-controller="ViewController as ViewCtrl" 
    data-ng-cloak >

<head>
    <meta content="text/html; iso-8859-1" http-equiv="content-type">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
    [#include "/frame-head.ftl" /]

   <script type="text/javascript">var externalId = ${(externalId!0)?c};</script>


	<link type="image/x-icon" href="/static/images/favicon.ico" rel="shortcut icon">
	<link type="image/x-icon" href="/static/images/favicon.ico" rel="icon">

    <title>${title!''}</title>
	<meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>
<body id="app" 
	data-ng-controller="${baseName?capitalize}Controller as ${baseName}Ctrl"
	class="app ng-animate [#if navCollapsedMin?? && navCollapsedMin]nav-collapsed-min[/#if]">
	
	<!-- 
	 ! Linha superior.
	 ! -->
	<section id="header" 
		class="header-container header-fixed bg-dark" >
		[#include "/_top.html" /]
	</section>
	
	<!-- 
	 ! Conteúdo principal
	 ! -->
	<div class="main-container" >
	
	    <aside id="nav-container" 
	    	class="nav-container nav-fixed nav-vertical bg-white">
	    	[#include "/_menu.html" /]
	    </aside>

	    <div id="content" class="content-container ui-ribbon-container ui-ribbon-primary">
	    [#if beta??]
	        <div class="ui-ribbon-wrapper">
	            <div class="ui-ribbon">${beta}</div>
	        </div>
	    [/#if]
	    <section class="view-container animate-fade-up">
			<div class="page page-dashboard" >
		    	<div class="row" data-ng-include="'/assets/${baseName}/selection-${baseName}.html'"></div>
		    </div><!-- end of page -->
		</section>
	    </div><!--content-->
		</div><!--main-container-->
	
	<!--
	 ! Modal mostrado quando o modelo exibe modalBody.
	 !-->
	<div class="modal fade" id="modalBody" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg">
	   	<div class="modal-content">
	    	<div data-ng-include="getFormUrl()" ></div>
		</div>
		
		</div><!-- modal-dialog -->
	</div><!-- modal-fade -->	
	
	[#include "/_js.html" /]
	[#include "/_custom.html" /]
	[#if customControllerBody??]
	<script type="text/javascript" >
		var app = angular.module(${baseName});
		app.controller('CustomController', ['$scope', '$http','$resource', 'genericServices', 'securityServices', 'commomLang', 'controllerLang'
                                       , function($scope, $http, $resource, genericServices, securityServices, commomLang, contentLocale) {
			${customControllerBody}
        }
	</script>
	
	[/#if]
	<input type="hidden" id="_csrf" name="${_csrf.parameterName}" value="${_csrf.token}" />
	

</body>

</html>
