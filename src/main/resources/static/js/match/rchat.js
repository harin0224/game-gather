var stompClient = null;
var connectionTimeout;

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

    console.log(roomId);
//    var roomId = $("#roomId").val();
    if (!roomId) {
        alert("Please enter a room ID.");
        return;
    }

    var socket = new SockJS('/ws-websocket');
    stompClient = Stomp.over(socket);

    // 3초 동안 연결 시도
    connectionTimeout = setTimeout(function() {
        if (stompClient.connected) {
            return;
        }
        console.log('Connection timeout. Disconnecting...');
        disconnect();
    }, 3000);

    stompClient.connect({ roomId: roomId }, function (frame) {
        clearTimeout(connectionTimeout);
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/sub/rchat/' + roomId, function (greeting) { // 받을거
            console.log('greeting: ' + greeting);
            showGreeting(greeting);
        });
    });
}
//function connect() {
//    var socket = new SockJS('/ws-websocket');
//    stompClient = Stomp.over(socket);
//
//    // 3초 동안 연결 시도
//    connectionTimeout = setTimeout(function() {
//        if (stompClient.connected) {
//            return;
//        }
//        console.log('Connection timeout. Disconnecting...');
//        disconnect();
//    }, 3000);
//
//    stompClient.connect({}, function (frame) {
//        clearTimeout(connectionTimeout);
//        setConnected(true);
//        console.log('Connected: ' + frame);
//        stompClient.subscribe('/sub/rchat/test', function (greeting) { // 받을거
//        console.log('greeting: ' + greeting);
//        showGreeting(greeting);
//        });
//    });
//}
function saveChatLogs() {
    var chatLogs = [];
    $("#greetings tr td").each(function() {
        chatLogs.push({
            message: $(this).text(),
            timestamp: new Date().toJSON()  // 다른 형식의 타임스탬프
        });
    });

    console.log("Chat logs to save: ", chatLogs);

    $.ajax({
        url: "/rchat/save-chat-logs",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(chatLogs),
        success: function(response) {
            console.log("Chat logs saved successfully: " + response);
        },
        error: function(error) {
            console.log("Error saving chat logs: " + error);
        }
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
        saveChatLogs();
    }
    setConnected(false);
    console.log("Disconnected");
}
function sendName() {
    let currentTime = new Date().toISOString(); // 현재 시간을 ISO 포맷으로 가져옵니다.
    stompClient.send("/pub/rchat/" + roomId + "/hello", {}, JSON.stringify({
        'name': $("#name").val(),
        'timestamp': currentTime // 타임스탬프 추가
    }));
}
//function sendName() {
////    var roomId = $("#roomId").val();
//    stompClient.send("/pub/rchat/" + roomId + "/hello", {}, JSON.stringify({'name': $("#name").val()}));
//}
//function sendName() {
//    stompClient.send("/pub/rchat/hello", {}, JSON.stringify({'name': $("#name").val()}));
//}

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

//function disconnect() {
//      if (stompClient !== null) {
//          stompClient.disconnect();
//      }
//      setConnected(false);
//      console.log("Disconnected");
//  }