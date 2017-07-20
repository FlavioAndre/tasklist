var app = angular.module('tasklists', ['ngResource', 'ngGrid', 'ui.bootstrap']);


app.controller('tasklistsListController', function ($scope, $rootScope, taskService) {
    $scope.sortInfo = {fields: ['id'], directions: ['asc']};
    $scope.tasklists = {currentPage: 1};

    $scope.gridOptions = {
        data: 'tasklists.list',
        useExternalSorting: true,
        sortInfo: $scope.sortInfo,

        columnDefs: [
            { field: 'id', displayName: 'Id' },
            { field: 'titulo', displayName: 'Titulo' },
            { field: 'descricao', displayName: 'Descrição' },
            { field: 'status', displayName: 'Status' },
            { field: '', width: 30, cellTemplate: '<span class="glyphicon glyphicon-remove remove" ng-click="deleteRow(row)"></span>' }
        ],

        multiSelect: false,
        selectedItems: [],
        afterSelectionChange: function (rowItem) {
            if (rowItem.selected) {
                $rootScope.$broadcast('taskSelected', $scope.gridOptions.selectedItems[0].id);
            }
        }
    };

  
    $scope.refreshGrid = function () {
        var listTasksArgs = {
            page: $scope.tasklists.currentPage,
            sortFields: $scope.sortInfo.fields[0],
            sortDirections: $scope.sortInfo.directions[0]
        };

        taskService.get(listTasksArgs, function (data) {
            $scope.tasklists = data;
        })
    };

    $scope.deleteRow = function (row) {
        $rootScope.$broadcast('deleteTask', row.entity.id);
    };

    $scope.$watch('sortInfo', function () {
        $scope.tasklists = {currentPage: 1};
        $scope.refreshGrid();
    }, true);

    $scope.$on('ngGridEventSorted', function (event, sortInfo) {
        $scope.sortInfo = sortInfo;
    });

    $scope.$on('refreshGrid', function () {
        $scope.refreshGrid();
    });

    $scope.$on('clear', function () {
        $scope.gridOptions.selectAll(false);
    });
});

app.controller('tasklistsFormController', function ($scope, $rootScope, taskService) {
      $scope.clearForm = function () {
        $scope.task = null;
        document.getElementById('imageUrl').value = null;
        $scope.taskForm.$setPristine();
        $rootScope.$broadcast('clear');
    };

    // Chamada do rest
    $scope.updateTask = function () {
        taskService.save($scope.task).$promise.then(
            function () {
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('taskSaved');
                $scope.clearForm();
            },
            function () {
               $rootScope.$broadcast('error');
            });
    };

   
    $scope.$on('taskSelected', function (event, id) {
        $scope.task = taskService.get({id: id});
    });

    $scope.$on('deleteTask', function (event, id) {
        taskService.delete({id: id}).$promise.then(
            function () {
                $rootScope.$broadcast('refreshGrid');
                $rootScope.$broadcast('taskDeleted');
                $scope.clearForm();
            },
            function () {
                $rootScope.$broadcast('error');
            });
    });
});

// Cria o controller 
app.controller('alertMessagesController', function ($scope) {
    $scope.$on('taskSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Registro salvo com sucesso!' }
        ];
    });

    
    $scope.$on('taskDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Registro excluido com sucesso!' }
        ];
    });


    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'Há problemas no serviço do servidor!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

// Service que provem operações da tasklists
app.factory('taskService', function ($resource) {
    return $resource('resources/tasklists/:id');
});
