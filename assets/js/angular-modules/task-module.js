(function() {

    var app = angular.module('task-module', []);

    app.controller('TaskController', ['$http', '$scope', function($http, $scope){

        var thisController = this;
        thisController.selectedFriendsId = [];
        thisController.selectedFriendsName = [];
        thisController.task = {};

        thisController.task.prioprity;
        thisController.btnSelected = 0;

        thisController.tasksCreated = [];

        thisController.toogleSelection = function (friendId, friendName){
            var index = thisController.selectedFriendsId.indexOf(friendId);
            if(index > -1){
                thisController.selectedFriendsId.splice(index, 1);
                thisController.selectedFriendsName.splice(index, 1)
            }else{
                thisController.selectedFriendsId.push(friendId);
                thisController.selectedFriendsName.push(friendName);
            }
            console.log(thisController.selectedFriendsId);
        };

        thisController.createTask = function(userId){
            console.log('new task created by ' + userId)
            console.log(thisController.task.deadline);
            deadLineArray = thisController.task.deadline.split('/');
            console.log(deadLineArray);
            thisController.task.deadline = new Date(deadLineArray[2], deadLineArray[1] - 1, deadLineArray[0]);

            var newTask = {
                name : thisController.task.name,
                description : thisController.task.description,
                assignedTo : [],
                priority : thisController.task.priority,
                deadline : thisController.task.deadline
            };
            for(var i = 0; i < thisController.selectedFriendsName.length; i++){
                var assignedTo = {
                    friendId : thisController.selectedFriendsId[i],
                    friendName : thisController.selectedFriendsName[i] 
                };
                newTask.assignedTo.push(assignedTo);
            }

            console.log(newTask);

            $http.post('http://ifpbtasks.herokuapp.com/users/newTask/'+userId, newTask)
            .success(function(data){
                console.log(data);

                swal("Tarefa Enviada", "Sua tarefa chegará ao seu amigo em breve", "success");

                $('#newTaskForm').trigger('reset');
                $scope.newTaskForm.$setPristine();
                $scope.newTaskForm.$setUntouched();
                resetVariables();

            })
            .error(function(data){
                console.log(data);
            });
        };

        thisController.selectBtn = function(btn){
            this.btnSelected = btn;
            switch(btn){
                case 1:
                    thisController.task.priority = 'Baixa';
                    break;
                case 2: 
                    thisController.task.priority = 'Média';
                    break;
                case 3:
                    thisController.task.priority = 'Alta';
                    break;
            }
        }

        thisController.isSelected = function(checkBtn){
            return this.btnSelected === checkBtn;
        }

        $http.get('http://ifpbtasks.herokuapp.com/userInfo').success(function(user){
            $http.get('http://ifpbtasks.herokuapp.com/users/'+user.facebook_id+'/tasks').success(function(data){
                thisController.tasksCreated = data;
                $('#loaderDiv').hide();
                
                if(thisController.tasksCreated.length==0){
                    $('#emptyTasks').show();
                }
                
            }).error(function(data){
                console.log(data);
            });

        });
        

//        $http.get('./../../../../userInfo').success(function(user){
//            $http.get('./../../../../users/'+user._id+'/tasksAssigned').success(function(data){
//                thisController.tasksAssigned = data;
//                console.log(data);
//            }).error(function(data){
//                console.log(data);
//            });
//
//        });

        function resetVariables(){
            thisController.selectedFriendsId = [];
            thisController.selectedFriendsName = [];
            thisController.task = {};
            thisController.btnSelected = 0;
        }

    }]);
})();