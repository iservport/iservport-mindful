(function() {
	app = angular.module('voto', ['ui.bootstrap', 'ngResource', 'ngSanitize', 'helianto.security', 'stats']);
	
	app.controller('VotoController', ['$scope', '$http', '$resource', 'securityServices'
	                                  , function($scope, $http, $resource, securityServices) {
		
		/*
		 * initializers
		 */
		$scope.baseName = "voto";
		var baseUrl = '/app/'+$scope.baseName+'/';

		securityServices.getAuthorizedRoles();
		$scope.isAuthorized = function(role, ext){
			return securityServices.isAuthorized(role, ext);
		}
		$scope.logout = function(){
			return securityServices.logout();
		}

	}]);
} )();
