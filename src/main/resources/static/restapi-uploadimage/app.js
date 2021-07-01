var app = angular.module('myapp', []);
app.controller("ctrl", function($scope, $http){
    var url = '/../rest/files/images';
    // $scope.filenames = []
    $scope.url = function(filename) {
        return `${url}/${filename}`;
    }
    // console.log($scope.url('abc'))
    $scope.list = function() {
        $http.get(url).then(resp => {
            $scope.filenames = resp.data;
            console.log($scope.filenames)
        }).catch(error => {
            console.log('error: '+error);
        });
    }

    $scope.upload = function(files) {
        var form = new FormData();
        for(var i = 0; i < files.length ; i++) {
            form.append('files', files[i]);
        }
        for(var pair of form.entries()) {
            console.log(pair[0]+ ', '+ pair[1]); 
        }
        $http.post(url, form, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        }).then(resp => {
            $scope.filenames.push(...resp.data);
        }).catch(error => {
            console.log('Error: '+error);
        });
        // $scope.list();
    }

    $scope.delete = function(filename) {
        $http.delete(`${url}/${filename}`).then(resp => {
            let i = $scope.filenames.findIndex(name => name == filename);
            $scope.filenames.splice(i,1);
        }).catch(error => {
            console.log('error: '+error);
        });
    }

    $scope.list();
})