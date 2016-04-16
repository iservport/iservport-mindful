(function() {
	app = angular.module('security', ['ui.bootstrap', 'helianto.security', 'stats']);
	
	app.controller('SecurityController', ['$scope', '$window', '$http', '$resource', 'securityServices'
	                                  , function($scope, $window, $http, $resource, securityServices) {
	
		$scope.baseName = "home";
		$scope.menuName = "home";
		$scope.email = email!='undefined'?email:'' ;
		
		/**
		 * Autorização
		 */
		$scope.authList =[];
		defineAuthorities();
		function defineAuthorities(){
			securityServices.getAuthorizedRoles(null).success(function(data, status, headers, config) {
				$scope.authList = data.content;
			});
		}
		$scope.isAuthorized =function(role, ext){
			return securityServices.isAuthorized($scope.authList, role, ext);
		}
		$scope.loginResource = $resource("/login/",{ username:"@username", password: "@password", rememberme:"@rememberme"},{
			login : { method:'POST',  headers : {'Content-Type': 'application/x-www-form-urlencoded'}}
		});
		$scope.signUpResource = $resource("/signup/");
		$scope.passwordResource = $resource("/signup/createPass");
		$scope.passwordRecoveryResource = $resource("/recovery/", { email:"@email"}, {
			update: { method: 'PUT' },
			create: { method: 'POST' }
		});
		
		$scope.login = function(usernameVal,passwordVal){
//			$scope.formLogin = {"username" :usernameVal, "password" :passwordVal };
			$scope.logged = $scope.loginResource.login($.param({username :usernameVal, password :passwordVal })); 
			$scope.logged.$promise.then(
					function(data, getReponseHeaders) {
						$window.location.href = "/";
					},
					function(data, getReponseHeaders) {
							console.log(data);
							$scope.error = true;
						}
			);
		}
		
		$scope.saveEmail = function(emailVal){
			$scope.signUpResource.get({tempEmail:emailVal});
		}
		
		//form initializer
		$scope.create = function(){
			$scope.form =  $scope.signUpResource.get({create:true});
			$scope.form.$promise.then(function(data) {
				data.email = $scope.email;
				$scope.form = data;
			});
		} 
		$scope.create();
		$scope.signUp = function(){
			$scope.form.password = 'save';
			$scope.returnCode = $scope.signUpResource.save($scope.form);
			$scope.returnCode.$promise.then(function(data) {
				console.log(data);
			});
		};
		
		$scope.updateUser = function(){
			$scope.form.email = $scope.email;
			$scope.passwordResource.save($scope.form);
		};
		
		$scope.passwordEmail = function(val){
			$scope.passwordRecoveryResource.save({email:val});
		}
		
		$scope.updatePassword = function(){
			$scope.form.email = $scope.email;
			$scope.passwordRecoveryResource.update($scope.form);
		}
		
		$scope.passwordMatches = function(){
			return $scope.cpassword === $scope.form.password;
		}
		 
	}]); // SecurityController
	
} )();
