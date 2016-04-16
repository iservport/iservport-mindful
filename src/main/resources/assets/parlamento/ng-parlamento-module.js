(function() {
	app = angular.module('parlamento', ['ui.bootstrap', 'ngResource', 'ngSanitize', 'helianto.security', 'stats']);
	
	app.controller('ParlamentoController', ['$scope', '$http', '$resource', 'securityServices'
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
		$scope.baseName = "parlamento";
		var baseUrl = '/app/'+$scope.baseName+'/';
		$scope.qualifierResource = $resource(baseUrl + "qualifier");
		$scope.parlamentoResource = $resource(baseUrl + "entity", {entityType: "@entityType", rootUserId: "@rootUserId"}, {
			save: { method: 'PUT' }
			, create: { method: 'POST' }
			, search : { method: 'POST', url: baseUrl+'search'}
			, authorize : { method: 'GET'}
		});
		
		/**
		 * Qualificadores.
		 */
		$scope.qualifierValue = ' ';
		$scope.listQualifiers = function() {
			$scope.qualifiers = $scope.qualifierResource.query();
			$scope.qualifiers.$promise.then(function(data) {
				if ($scope.qualifierValue === ' ' && data.length>0) {
					$scope.qualifierValue = $scope.qualifiers[0].qualifierValue;
					$scope.listParlamentos($scope.qualifierValue);
				}
			})
		};
		$scope.listQualifiers();
		
		/**
		 * Parlamentos
		 */
		$scope.parlamentoValue = 0;
		// list
		$scope.listParlamentos = function(qualifierValue) {
			$scope.parlamentos = $scope.parlamentoResource.get({entityType: qualifierValue});
			$scope.parlamentos.$promise.then(function(data) {
				if ($scope.rootValue === 0 && data.length>0) {
					$scope.rootValue = $scope.rootList[0].id;
				}
			})
		};
		// search
		$scope.search = function(page, searchUrl) {
			$scope.searchString = $("#searchString").val();
			search(page, $scope.searchString, searchUrl) ;
		}
		function search(page, searchString, searchUrl) {
			var dataObj = {	
					"searchString" : searchString ,
					"qualifierValue" : $scope.categoryId,
			};
			var res =  $http.post(searchUrl+'/'+$scope.sectionTab+'/'+page
					, dataObj
					, {});
			res.success(function(data, status, headers, config) {
				$scope.resultFromSearch = data;
				$scope.searchBool = true;
				$scope.page = page;
				$scope.nextAndPrevious = genericServices.getNextAndPreviousLinkByList(data);
				if(data.totalElements == 1){
					$scope.root = data.content[0];
				}
				else if(data.totalElements>1) {
					$scope.rootList = data;	
				}
				else {					
					$scope.rootList = [];
				}
			})    
		}
		// get
		$scope.getRoot = function(id) {
			console.log("USER ID = "+id);
			if (id==0) {
				$scope.root = $scope.parlamentoResource.create({categoryId:$scope.qualifierValue});
			}
			else {
				$scope.root = $scope.parlamentoResource.get({userId: id});
			}
			$scope.root.$promise.then(
				function(data, getReponseHeaders) {
					if (data.length>0) {
						$scope.rootValue = data.userId;
					}
				}
			);
		};
		// authorize
		$scope.authorize = function() {
			$scope.newUser = $scope.parlamentoResource.authorize({rootUserId:$scope.root.userId});
			$scope.newUser.$promise.then(
				function(data, getReponseHeaders) {
					if (data.success==1) {
						$window.location = "/";
					}
				}
			);
		}
				

	}]);
} )();
