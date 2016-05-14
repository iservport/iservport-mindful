
	<head>
		<meta content="text/html; iso-8859-1" http-equiv="content-type">
		<meta http-equiv="content-language" content="${.locale!'pt-BR'}" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<#assign bootstrapVersion = "/webjars/bootstrap/3.3.2" />
		
		<#-- Styles -->
		<#-- Bootstrap style -->
		<link rel='stylesheet' href='/webjars/bootstrap/3.3.2/css/bootstrap.min.css'>
		
		<!-- Slim Style -->
		<link rel='stylesheet' href='/static/css/slim-main-1.3.css'>
		<link rel='stylesheet' href='/static/css/slim-ui-1.3.css'>
		<link rel='stylesheet' href='/static/css/slim-ui-ext.css'>
		<!-- Loading bar -->
		<link rel='stylesheet' href='/webjars/angular-loading-bar/0.7.1/loading-bar.min.css'>
	
		<#-- iservport css extension -->
		<link rel='stylesheet' href='/static/css/iservport.css'>
		<link rel='stylesheet' href='/static/css/iservport-bootstrap.css'>
		
		<#-- Morris style -->
		<link rel="stylesheet" href="/webjars/morrisjs/0.5.0/morris.css">
		
		<#-- font-awesome -->
		<link rel='stylesheet' href="/webjars/font-awesome/4.3.0/css/font-awesome.css">
		
		<#-- font-ionicons -->
		<link rel='stylesheet' href="/webjars/ionicons/1.5.2/css/ionicons.min.css">
		
		<#-- font-foundation -->
		<link rel='stylesheet' href="/webjars/foundation-icon-fonts/d596a3cfb3/foundation-icons.css">

        <#-- Google Material icons-->
        <link rel="stylesheet" href="/webjars/material-design-icons/2.2.0/iconfont/material-icons.css">

	    <!-- Redactor -->
	    <link rel="stylesheet" href="/static/redactor/redactor.css" />
	    
	    <!-- ngImgCrop -->
	    <link rel="stylesheet" href="/webjars/ngImgCrop/0.3.2/compile/minified/ng-img-crop.css" />
    
		<!--[if lte IE 7]>
			<script src="/webjars/json3/3.3.2/json3.min.js"></script>
		<![endif]-->
  
   		<#-- Javascript -->
		<#-- jQuery package -->
	    <script type="text/javascript" src="/webjars/jquery/2.1.1/jquery.min.js"></script>
	    
    	<#-- jQuery.cookie package -->
	    <script type="text/javascript" src="/webjars/jquery-cookie/1.4.0/jquery.cookie.js"></script>
		
		<#-- Bootstrap package -->
	    <script type="text/javascript" src="/webjars/bootstrap/3.3.2/js/bootstrap.min.js"></script>
   
		<#-- Knob -->
		<script type="text/javascript" src="/webjars/jquery-knob/1.2.2/jquery.knob.min.js"></script>
		
		<#-- Morris -->
		<script src="/webjars/raphaeljs/2.1.2/raphael-min.js"></script>
		<script src="/webjars/morrisjs/0.5.0/morris.min.js"></script>
		
	    <#-- Js padrão-->
	   	<script type="text/javascript" src='/static/js/defaut.js'></script>

	   	<!--
		<link type="images/x-icon" href="/static/images/favicon.ico" rel="shortcut icon">
		<link type="images/x-icon" href="/static/images/favicon.ico" rel="icon">
		-->
		<link rel="localization"  href="/locales/manifest.json">
		  <!-- i18n para menu -->
   		<script type="text/javascript" src='/assets/_i18n/menu_pt_br.js'></script>
		<title>${title!"Selection"}</title>

	</head>
    
