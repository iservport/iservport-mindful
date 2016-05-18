(function() {
	app = angular.module('home', ['ui.bootstrap', 'ngResource', 'ngSanitize', 'helianto.security']);
	
	app.controller('HomeController', ['$scope', '$http', '$resource', 'securityServices'
	                                  , function($scope, $http, $resource, securityServices) {
		
		/*
		 * initializers
		 */
		$scope.baseName = "home";
		var baseUrl = '/app/'+$scope.baseName+'/';
		
		$scope.votoResource = $resource('/app/voto/');

		securityServices.getAuthorizedRoles();
		$scope.isAuthorized = function(role, ext){
			return securityServices.isAuthorized(role, ext);
		}
		$scope.logout = function(){
			return securityServices.logout();
		}

		$scope.login = '';
		
		function getLogo() {
			return $http.get('/login/logo')
			.success(function(data, status, headers, config) {
				console.log(data);
					$scope.login = data;
			})    
		}
		getLogo();
		
		/**
		 * Estatísticas de conteúdo
		 */
		$http.get('/app/home/content/stats')
		.success(function(data, status, headers, config) {
			$scope.contentStats = data;
			$scope.contentStatsSum = 0;
			data.forEach(function(item) {
				$scope.contentStatsSum += item.itemCount;
			});
		})
		.error(function(data, status, headers, config) {
		});
	    
		////////
		
		securityServices.getAuthorizedRoles();
		$scope.isAuthorized = function(role, ext){
			return securityServices.isAuthorized(role, ext);
		}
		$scope.logout = function(){
			return securityServices.logout();
		}
		$http.get('/app/home/entity').success(function(data, status, headers, config) {
			return 	$scope.authorizedEntity = data;
		})
		
		
		/*
		 * initializers
		 */
		$scope.documentoResource = $resource(baseUrl);
		$scope.featureResource = $resource(baseUrl+'feature');
		

		//usado para paginação
		$scope.nextAndPrevious = [];
		$scope.page = 0;
		$scope.pageFolder  = 0;
		var sectionTab = 1;
		/*
		 * end initializers
		 */
		
		$scope.sectionTab = 0;
		$scope.setSectionTab = function(value) {
			$scope.sectionTab = value;
		};
		$scope.isSectionTabSet = function(value) {
			return $scope.sectionTab === value;
		};

		/*
		 * Ajax get's
		 */
		
		/**
		 * Qualifier = tipo de report
		 */
		$scope.qualifierValue = 0;
		$scope.qualifierResource = $resource(baseUrl + "qualifier");
		$scope.listQualifiers = function() {
			$scope.qualifiers = $scope.qualifierResource.query();
			$scope.qualifiers.$promise.then(function(data) {
				if ($scope.qualifierValue === 0 && data.length>0 && $scope.externalId==0) {
					$scope.setQualifier($scope.qualifiers[0]);
				}
			})
		};
		$scope.setQualifier = function(qualifierItem) {
			$scope.qualifier = qualifierItem;
			$scope.qualifierValue = qualifierItem.qualifierValue;
			$scope.qualifierName = qualifierItem.qualifierName;
			$scope.reportFolders = [];
			$scope.listReportFolders(qualifierItem.qualifierValue);
		}
		$scope.listQualifiers();
		
		/**
		 * Documento
		 * 
		 */
		$scope.getVotos = function(id){
			$scope.votos = $scope.documentoResource.query({documentoId:id});
		}
		$scope.getVotos(1);
		$scope.totalDocumentos = function(val){
			return $scope.documentoResource.get({resolution:val});
		}
		
		$scope.getVotoDetails = function(id){	
			$scope.documento = $scope.documentoResource.get({documentoId:id, one: true});
			$scope.votoDetails = $scope.documentoResource.get({documentoId:id, details: true});
			openForm('form-voto-details');
		}
		
		
		$scope.votados = $scope.totalDocumentos('D');
		
		$scope.votando = $scope.totalDocumentos('T');

		$scope.getDocumentos = function(){
			$scope.setSectionTab(0);
			$scope.documentos = $scope.documentoResource.query({all:true});
		}
		$scope.getDocumentos();
		
		$scope.getDocumento = function(val){
			$scope.documento = $scope.documentoResource.get({documentoId:val, one: true});
			openForm('form-voto');
		}
		
		$scope.vote = function (val, id){
			$scope.documento = $scope.votoResource.get({documentoId:id, voto : val});
		}
		
		/**
		 * Feature
		 */
		
		$scope.getFeatures = function(){
			$scope.features = $scope.featureResource.query();
		}
		$scope.getFeatures();
		/**
		 * Pastas.
		 */
		$scope.folderValue = 0;
		$scope.reportFolder= {};
		$scope.reportFolderResource = $resource(baseUrl + "folder", { qualifierValue:"@qualifierValue",folderId:"@folderId"}, {
			save: { method: 'PUT' },
			create: { method: 'POST' }
		});
		
		/**
		 * Exportação
		 */
		$scope.reportFolderExportResource = $resource(baseUrl + "folder/export", { reportFolderId:"@reportFolderId", entitiesIds:"@entitiesIds" }, {
			save: { method: 'PUT' },
			exportFolder: { method: 'POST' }
		});
		
		//abre o modal para exportação e lista entidades à exportar e já exportadas.
		$scope.exportFolder = function(){
			$scope.entityList = $scope.reportFolderExportResource.get({reportFolderId:$scope.reportFolder.id});
			$scope.entityListExported = $scope.reportFolderExportResource.get({reportFolderId:$scope.reportFolder.id, selected:true});
			$scope.openForm('form-folder-export');
		}
		
		//verifica se o item é pertencente a outra unidade.
		$scope.isExported = function(targetId){
			return $scope.authorizedEntity.id != targetId;
		}
		
		$scope.entitiesIds = [];
		/**
		 * Método usado para inserir ou retirar valores de um array.
		 * 
		 * Uso com checkBox: <input data-ng-click="toggleSelection(idToAdd)"  type="checkbox" id="item-" name="{{idToAdd}}" > 
		 * 
		 */
		$scope.toggleSelection = function toggleSelection(id) {
			var idx = $scope.entitiesIds.indexOf(id);

			if (idx > -1) {
				$scope.entitiesIds.splice(idx, 1);
			}
			else {
				$scope.entitiesIds.push(id);
			}
		};
		//salva exportação
		$scope.saveExport = function(){
			$scope.save =  $scope.reportFolderExportResource.exportFolder({reportFolderId:$scope.reportFolder.id,entitiesIds:$scope.entitiesIds});
			$scope.entitiesIds = [];
			$("#modalBody").modal('hide');
		}
		
		//end export
		
		$scope.setSection= function(val){
			$scope.section = val;
			$scope.qualifierValue = val;
		}
		
		$scope.listReportFolders = function(reportCategory) {
			$scope.setSection(reportCategory);
			$scope.reportFolderList = $scope.reportFolderResource.get({qualifierValue:reportCategory});
			$scope.reportFolderList.$promise.then(function(data) {
				if (data.content.length>0) {
					$scope.setSectionTab(0);
					$scope.reportFolders = data.content;
					if($scope.externalId==0){	
						$scope.folderValue = data.content[0].id;
						if($scope.folderValue>0 ){
							$scope.listReports($scope.folderValue);
						}
					}
				}else{
					$scope.reportFolder = null;
					$scope.reportFolders = [];
					$scope.reports = [];
					$scope.report = {"id":-1};
				}
			})
		};

		$scope.newReportFolder = function() {
			$scope.reportFolder = $scope.reportFolderResource.create({qualifierValue:$scope.qualifierValue});
			openForm('form-report-folder');
		};
		$scope.getReportFolder = function(id) {
			$scope.reportFolder = $scope.reportFolderResource.get({folderId: id});
			$scope.reportFolder.$promise.then(function(data) {
				 $scope.folderValue = data.id;
			});
		};
		$scope.updateReportFolder = function() {

			$scope.reportFolder = $scope.reportFolderResource.save($scope.reportFolder);
			$scope.reportFolder.$promise.then(
					function(data, getReponseHeaders) {
						$scope.listReportFolders($scope.qualifierValue);
						$scope.listReports(data.id);
						$("#modalBody").modal('hide');
					},
					function(data, getReponseHeaders) {
						console.log(data);
						if(data.status === 302) {
							$scope.message= data.data;
							$scope.message.exist = true;
						}
					}
			);
		};

		/**
		 * Abre um modal.
		 * 
		 * @param formName Nome do Fragmento (form-YYYY)
		 * 
		 */
		$scope.openForm = function(formName){
			openForm(formName);
		}
		
		function openForm(formName){
			$scope.message = [];
			//inicialização em form-report
			$scope.formUrl = '/assets/home/'+formName+'.html';
			$("#modalBody").modal('show');
	
		}
		
		/**
		 * Retorna o form a ser mostrado no Modal
		 */
		$scope.getFormUrl = function(){
			return $scope.formUrl;
		} 

		$scope.getFavouriteIcon = function() {
			return $scope.favouriteIcon;
		}

		$scope.setFavouriteId = function(favouriteId) {
			$scope.favouriteId = favouriteId;
		}

		/**
		 * Retorna o form a ser mostrado no Modal
		 */
		$scope.getFormUrl = function(){
			return $scope.formUrl;
		} 

		$scope.setSectionTab = function(value) {
			console.log('set section to '+value);
			$scope.sectionTab = value;
		};
		$scope.isSectionTabSet = function(value) {
			return $scope.sectionTab === value;
		};
		
		$scope.setSearch  = function(value) {
			$scope.searchBool = value;
		};
		$scope.isSearch = function() {
			return $scope.searchBool;
		};		
		
		$scope.open = function($event,value) {
			$event.preventDefault();
			$event.stopPropagation();
			$scope.datePicker = [];
			$scope.datePicker[value]=true;
		};

	}]);
} )();
