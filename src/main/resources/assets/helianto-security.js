/**
 * Security services.
 */                                                                                                                                                                                                  
var module = angular.module('helianto.security', ['ngResource'])
.factory("securityServices", ['$http', function($http) {
	var categoryMapList =  {};
	var getCategoryMap = function() {
		return $http.get('/app/category/qualifier')
		.success(function(data, status, headers, config) {
			categoryMapList = data;
		});
	}
	var roleList = [];
	var getAuthorizedRoles = function(userId) {
		return $http.get('/app/user/role'+((userId!=null && userId!='null')?'?userId='+userId:''))
		.success(function(data, status, headers, config) {
			roleList = data.content;
		});
	}
	return {
		getAuthorizedRoles : (getAuthorizedRoles) ,
		isAuthorized: function(role, extension) {
			var result = false;
			roleList.forEach(function(entry) {
				if(entry.serviceName == (role) && entry.serviceExtension.indexOf(extension)>-1){
					result = true;
				}
			});
			return result;
		
		}
		, getCategoryMap:(getCategoryMap)
		, showMenuItem : function(menuCode){
			var result = false;
			categoryMapList.forEach(function(entry) {
				if(entry.qualifierValue == menuCode && entry.countItems>0){
					result = true;
				}	
			});
			return result;
		}
		, logout: function() {return $http.post('/logout')}
		}
}])
/**
 * Diretiva para recuperar entidade autorizada.
 */
.directive('authorizedEntity', [ '$http', function($http) {
		return {
			restrict: 'A',			
			link:function(scope, element, attrs) {
				$http.get('/app/home/entity')
				.success(function(data, status, headers, config) {
					scope.authorizedEntity = data;
				});		
			},
			template :'<div id="authorizedEntity">{{authorizedEntity.entityAlias}}</div>'
		}

}])
.controller('EntityController', ['$scope', '$http'
	                                  , function($scope, $http) {
	$http.get('/app/home/entity')
	.success(function(data, status, headers, config) {
		$scope.authorizedEntity = data;
	});		
}])
/**
 * Diretiva para recuperar usuário autorizado.
 * 
 * Default : userKey 
 * 
 */
.directive('authorizedUser', [ '$http', function($http) {
		return {
			restrict: 'EA',
			link:function(scope, element, attrs) {
				$http.get('/app/home/user')
				.success(function(data, status, headers, config) {
					
					scope.userLabel = data.userKey; 
					if(typeof attrs.typeName != 'undefined' && attrs.typeName.indexOf('name')>-1 ){
						scope.userLabel = data.userName;
					}else if(typeof attrs.typeName != 'undefined' && attrs.typeName.indexOf('display')>-1){
						scope.userLabel = data.displayName;
					}
					console.log(scope.userLabel);
				});		
			},
			template :'<div id="authorizedUser">{{userLabel}}</div>'
		}

}])
;

