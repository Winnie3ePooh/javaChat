var check = true;

function upMess(){
    var k;
    var mess = [];
    if(check){
        check = false;
        k = "NaN";
    }
    else k = getLastMessageID();
    $.ajax({
        url: '/javaChat/GetMess/get?last='+k,
        type: 'GET',
        dataType: 'json',
        success: function(data, textStatus, xhr) {
            $('#response').empty();
            for(var i = 0; i < data.length; i++){
                var tr = $('<tr msgid='+data[i].dt+'></tr>');
                $('<td>' +data[i].nick+': '+data[i].mess+ '</td>').appendTo(tr);
                tr.appendTo('#response');
            };
            document.getElementById('chatik').scrollTop=document.getElementById('chatik').scrollHeight;
        }
    });
}

function getLastMessageID(){
    return $('#response tbody tr:last').attr("msgid").toString();
}

function get_cookie ( cookie_name )
{
  var results = document.cookie.match ( '(^|;) ?' + cookie_name + '=([^;]*)(;|$)' );
 
  if ( results )
    return ( unescape ( results[2] ) );
  else
    return null;
}

function getUsers() {
    $.ajax({
        url: '/javaChat/ChatServlet?action=users',
        type: 'GET',
        dataType: 'json',
        success: function(data, textStatus, xhr) {
            $('#on').empty();
            for(var i = 0; i < data.length; i++){
                var tr = $('<tr></tr>');
                $('<td>' +data[i].login+ '</td>').appendTo(tr);
                tr.appendTo('#on');
            };
            document.getElementById('users').scrollTop=document.getElementById('users').scrollHeight;
        }
    });
}

function ajax() { //Ajax отправка формы
    var msg = $("#mesChat").serialize();
    $.ajax({
        type: "POST",
        url: "/javaChat/TakeMess/take",
        data: {mess: $('#mesChat').val(), nick: get_cookie("user")},
        success: function(data) {
            var tr = $('<tr msgid='+data[0].dt+'></tr>');
            $('<td>' +data[0].nick+': '+data[0].mess+ '</td>').appendTo(tr);
            tr.appendTo('#response');
            upMess();
            $('#mesChat').val('');
        },
        error:  function(xhr, str){
            alert("Возникла ошибка!");
        }
    });
}

function getOn(){
    $.ajax({
        url: '/javaChat/ChatServlet?action=users',
        type: 'GET',
        dataType: 'json',
        success: function(data, textStatus, xhr) {
            $('#on').empty();
            for(var i = 0; i < data.length; i++){
                var tr = $('<tr></tr>');
                $('<td>' +data[i].nick).appendTo(tr);
                tr.appendTo('#on');
            };
            document.getElementById('users').scrollTop=document.getElementById('users').scrollHeight;
        }
    });
}

///function addBtnUser() { //Ajax отправка формы
//   var arr = $('#newUser').serialize();
//   alert(arr);
 //   $.ajax({
//        url: '/javaChat/ChatServlet?action=addUser&login=' + arr[0].value + '&pass=' + arr[1].value,
 //       type: 'GET',
 //       dataType: 'json',
//        success: function(data, textStatus, xhr) {
//            $('#addcontact').modal('hide');
//        }
  //  });
//}

function addBtnUser() {
    $('#newUser').submit(function() {
        var arr = $(this).serializeArray();
        $.getJSON('/javaChat/ChatServlet?action=addUser&login=' + arr[0].value + '&pass=' +
                arr[1].value);
        $('#addcontact').modal('hide');
        return false;
    });
}