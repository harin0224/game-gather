var stompClient = null;
var connectionTimeout;

// HTML 제어
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

// 소켓 연결해서 기능 불러오기
// 구독
function connect() {
    console.log(roomId);
    // var roomId = $("#roomId").val();
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

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendChatting() {
    let currentTime = new Date().toISOString(); // 현재 시간을 ISO 포맷으로 가져옵니다.
    stompClient.send("/pub/rchat/" + roomId + "/hello", {}, JSON.stringify({
        'chatting': $("#chatting").val(),
        'timestamp': currentTime // 타임스탬프 추가
    }));
}

function saveChatLogs() {
    var chatLogs = [];
    // .last()를 사용하여 greetings 테이블의 마지막 td 요소를 선택합니다.
    var lastMessage = $("#greetings tr td").last().text();

    chatLogs.push({
        message: lastMessage,
    });

    console.log("Chat log to save: ", chatLogs);

    $.ajax({
        url: "/rchat/save-chat-logs",
        method: "POST",
        contentType: "application/json",
        data: JSON.stringify(chatLogs),
        success: function(response) {
            console.log("Chat log saved successfully: " + response);
        },
        error: function(error) {
            console.log("Error saving chat log: " + error);
        }
    });
}

function showGreeting(message) {
    console.log(message);
    $("#greetings").append("<tr><td>" + message.body + "</td></tr>");
    saveChatLogs(); // 메시지를 보여준 후에 로그를 저장합니다.
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function() { connect(); });
    $("#disconnect").click(function() { disconnect(); });
    $("#send").click(function() { sendChatting(); });
});
