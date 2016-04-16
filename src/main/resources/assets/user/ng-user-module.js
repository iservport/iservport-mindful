(function() {
	app = angular.module('user', ['ui.bootstrap', 'ngResource', 'ngSanitize', 'helianto.security', 'stats']);
	
	app.controller('UserController', ['$scope', '$http', '$resource', 'securityServices'
	                                  , function($scope, $http, $resource, securityServices) {
		
		/*
		 * initializers
		 */
		securityServices.getAuthorizedRoles();
		$scope.isAuthorized = function(role, ext){
			return securityServices.isAuthorized(role, ext);
		}
		$scope.logout = function(){
			return securityServices.logout();
		}

		/*
		 * RESOURCES
		 */
		$scope.baseName = "user";
		var baseUrl = '/app/'+$scope.baseName+'/';
		$scope.qualifierResource = $resource(baseUrl + "qualifier");
		$scope.mainResource = $resource(baseUrl, {userGroupId: "@userGroupId"}, {
			save: { method: 'PUT' }
			, create: { method: 'POST' }
		});
		
		/**
		 * Qualificadores.
		 */
		$scope.qualifierValue = 0;
		$scope.listQualifiers = function() {
			$scope.qualifiers = $scope.qualifierResource.query();
			$scope.qualifiers.$promise.then(function(data) {
				if ($scope.qualifierValue === 0 && data.length>0) {
					$scope.qualifierValue = $scope.qualifiers[0].qualifierValue;
					$scope.listMain($scope.qualifierValue);
				}
			})
		};
		$scope.listQualifiers();
		
		/**
		 * Lista principal.
		 */
		$scope.mainValue = 0;
		// list
		$scope.listMain = function(qualifierValue) {
			$scope.mainList = $scope.mainResource.get({userGroupId: qualifierValue});
			$scope.mainList.$promise.then(function(data) {
				if ($scope.mainValue === 0 && data.length>0) {
					$scope.mainValue = $scope.mainList[0].id;
				}
			})
		};
	}]);
} )();
