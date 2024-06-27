var stompClient = null;
// 전체 응답 데이터를 문자열로 받아온다고 가정


// HTML 제어
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

// 소켓 연결해서 기능 불러오기
// 구독
function connect() {
    var socket = new SockJS('/ws-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/sub/rchat/test', function (greeting) { // 받을거
        console.log('greeting: ' + greeting);
        showGreeting(greeting);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/pub/rchat/hello", {}, JSON.stringify({'name': $("#name").val()}));
}



function showGreeting(message) {
    console.log(message);
    $("#greetings").append("<tr><td>" + message.body + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});