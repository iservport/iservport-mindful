/**
 * Stats services.
 */                                                                                                                                                                                                  
var module = angular.module('stats', ['ngResource'])
.controller('StatsController', ['$scope', '$http', '$resource'
	                                  , function($scope, $http, $resource) {
	var baseUrl = '/app/documento/';
	$scope.featureResource = $resource(baseUrl+'feature');
	$scope.getFeatures = function(){
		$scope.features = $scope.featureResource.query();
	}
	$scope.getFeatures();
}])
