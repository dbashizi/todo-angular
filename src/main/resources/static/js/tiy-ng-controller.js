angular.module('TIYAngularApp', [])
   .controller('SampleController', function($scope, $http) {

       $scope.getTodos = function() {
           console.log("About to go get me some data!");
           $scope.name = "James has run the getToDos() function";

           $http.get("/todos.json")
               .then(
                   function successCallback(response) {
                       console.log(response.data);
                       console.log("Adding data to scope");
                       $scope.todos = response.data;
                   },
                   function errorCallback(response) {
                       console.log("Unable to get data");
                   });
       };


        $scope.toggleTodo = function(todoText) {
            console.log("About to toggle Todo with text " + todoText);

            $http.get("/toggleTodo.json?todoText=" + todoText)
                .then(
                    function success(response) {
                        console.log(response.data);
                        console.log("Todo toggled");
                        $scope.todos = response.data;
                    },
                    function error(response) {
                        console.log("Unable to toggle todo");
                    });
        };


        $scope.addTodo = function() {
            console.log("About to add the following Todo " + JSON.stringify($scope.newTodo));

            $http.post("/addTodo.json", $scope.newTodo)
                .then(
                    function successCallback(response) {
                        console.log(response.data);
                        console.log("Adding data to scope");
                        $scope.todos = response.data;
                    },
                    function errorCallback(response) {
                        console.log("Unable to get data");
                    });
        };

        console.log("Running the sample controller now ...");
        $scope.name = "James";

        $scope.initTodos = function() {
            $scope.todos = [];
            $scope.firstTodo = {};
            $scope.firstTodo.text = "Test out hardcoded value";
            $scope.firstTodo.done = true;
            $scope.todos.push($scope.firstTodo);
        }

        $scope.newVarCrazySillyName = "some crazy silly value";

        $scope.userEmail = $scope.name + "@tiy.com";

        $scope.newTodo = {};
    });
