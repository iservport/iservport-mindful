[#ftl]
<!DOCTYPE html >
<!--
  Material Design Lite
  Copyright 2015 Google Inc. All rights reserved.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      https://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License
-->
<html id="ng-app" xmlns:ng="http://angularjs.org"
      data-ng-app="${baseName}"
      data-ng-controller="ViewController as ViewCtrl"
      data-ng-cloak>
<head>
    <meta content="text/html; iso-8859-1" http-equiv="content-type">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0">

    [#include "/frame-head.ftl" /]

    <script type="text/javascript">var externalId = ${(externalId!0)?c};</script>


    <link type="image/x-icon" href="/static/images/favicon.ico" rel="shortcut icon">
    <link type="image/x-icon" href="/static/images/favicon.ico" rel="icon">

    <title>${title!''}</title>

    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="Material Design Lite">

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileColor" content="#3372DF">

    <meta name="_csrf" content="${_csrf.token}"/>
    <!-- default header name is X-CSRF-TOKEN -->
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

</head>
<body id="app"
      data-ng-controller="${baseName?capitalize}Controller as ${baseName}Ctrl"
      class="app ng-animate [#if navCollapsedMin?? && navCollapsedMin]nav-collapsed-min[/#if]">

<div class="demo-layout mdl-layout mdl-js-layout mdl-layout--fixed-drawer mdl-layout--fixed-header">

    <header class="demo-header mdl-layout__header mdl-color--grey-100 mdl-color-text--grey-600">
        [#include "/_top.html" /]

    </header>
    <div class="demo-drawer mdl-layout__drawer mdl-color--blue-grey-900 mdl-color-text--blue-grey-50">
        [#include "/_menu.html" /]
    </div>

    <main class="mdl-layout__content mdl-color--grey-100">
        <div data-ng-include="'/assets/${baseName}/selection-${baseName}.html'"></div>
    </main>
</div>
<!--
 ! Modal mostrado quando o modelo exibe modalBody.
 !-->
<div class="modal fade" id="modalBody" data-backdrop="static" tabindex="-1" role="dialog" aria-labelledby="modalLabel"
     aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div data-ng-include="getFormUrl()"></div>
        </div>

    </div><!-- modal-dialog -->
</div><!-- modal-fade -->

[#include "/_js.html" /]
[#include "/_custom.html" /]
[#if customControllerBody??]
<script type="text/javascript">
		var app = angular.module(${baseName});
		app.controller('CustomController', ['$scope', '$http','$resource', 'genericServices', 'securityServices', 'commomLang', 'controllerLang'
                                       , function($scope, $http, $resource, genericServices, securityServices, commomLang, contentLocale) {
			${customControllerBody}
        }

</script>

[/#if]
<input type="hidden" id="_csrf" name="${_csrf.parameterName}" value="${_csrf.token}"/>

</body>
</html>
