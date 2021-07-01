var app = angular.module('app', []);

app.config(function($httpProvider){
	$httpProvider.defaults.headers.common['Authorization']='Basic dGhhbmhsYW0wMToxMjM=';
});

app.controller('ctrl', function($scope, $http){
	$http.get('/../rest/authorities').then(resp => {
		$scope.db = resp.data;
		console.log($scope.db);
	})
	
	$scope.index_of = function(username, role) {
		return $scope.db.authorities
			.findIndex(a => a.account.username == username && a.role.id == role);
	}
	
	$scope.update = function(username, role) {
		var index = $scope.index_of(username, role);
		if(index >= 0) {
			var id = $scope.db.authorities[index].id;
			$http.delete(`/rest/authorities/${id}`).then(resp => {
				$scope.db.authorities.splice(index, 1);
			})
		}
		else {
			var authority = {
				account:{username: username},
				role:{id:role}
			};
			$http.post('/rest/authorities/', authority).then(resp => {
				$scope.db.authorities.push(resp.data);
			});
		}
	}
})