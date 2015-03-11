var data;
function getUrlParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++) 
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam) 
        {
            return sParameterName[1];
        }
    }
} 

function getFriendList(){
    var token = getUrlParameter('token');
    var result = WV.getUserFriendList(token);
    
    data = JSON.parse(result);
    
    for(var i in data){
        var name = data[i].name;
        div = "<div class='list-group-item col-xs-12'>" +
                "<div class='row-picture col-xs-2 pull-left'>" + 
                    "<img class='circle' src='"+data[i].picture.data.url+"' alt='icon'>" +
                "</div>" +
                "<div class='row-content col-xs-8 pull-right'>" +
                    "<h5 class='list-group-item-heading'>"+data[i].name+"</h5>" +
                "</div>" +
              "</div>" +
              "<div class='list-group-separator'></div>" +
              "<button class='btn-xs btn-info btn-block' onclick=setFriend(" + "'" + data[i].id + "','" + name.split( " " )[0] + "'" +");>Responsável</button>";
        

        $('#list').append(div);
        }
    
//"<p class='list-group-item-text'>"+data[i].ies+"</p>" +
    
}

function setFriend(id,name){
    $('#friendId').val(id);
    $('#friendName').val(name);
}

function sendMessage(){
    
    var task = $('#task').val();
    var id = $('#friendId').val();
    
    var menssage = task + "@['"+id+"']";
    
//    WV.showToast('Enviando..' + task);
    WV.showToast("Enviando...........");
    
    WV.sendMessage(menssage);
//    
    $('#friendId').val('');
    $('#friendName').val('');
}

$(document).ready(function() {
    $.material.init();

    getFriendList();
    //    $('#test').html(getUrlParameter('token'));

});
