var stompClient = null;

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
function connect() {
    var socket = new SockJS('/club-websocket');
    var clubCode = $("#clubCode").val();
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
    });
}

//1. 방 만들때 생성자한테 연결
//2. 사람이 방 들어갔을 때 연결

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/pub/club/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}


// 가입된 클럽 리스트 가져오는 부분
function getClubList() {
    axios.get('/club/list')
        .then(response => {
            console.log("club Code: ", response.data);
            const clubList = response.data;
            document.getElementById("clubList").innerHTML = response.data;

            //3. 리스트 가져오는거(세션 다시 킬때마다 리스트 다시 다 연결해줘야하는거)
            clubList.forEach(club => {
                stompClient.subscribe('/sub/club/receive-message/' + club.id , function (greeting) { // 받을거
                    });
            });
        })
        .catch(error => console.error(error));
}

//message 보내는 기능
let currentClubId = 0; // 클럽 리스트를 가져오고 현재 선택된(보고있는 화면)의 클럽 아이디를 넣어주고 해당 클럽에 메세지 보낼것
function sendMessage() {
    currentClubId = $("#currentClubId").val();
    console.log(`currentClubId ${currentClubId}`)
    stompClient.send(`/pub/club/send-message/${currentClubId}`, {}, JSON.stringify(
        {
            'message':$("#message").val(),
            'senderId': 1
        })
    );
}

function loadClubChat() {
     axios.get(`/club/chat?id=${$("#loadClubChat").val()}`)
    .then(response => {
        // 처리로직
        const data = response.data

        console.log('chat log : ', data)
    })
    .catch(error => console.error(error));
}

function kickUser() {
    axios.patch(`/club/member`,{
        clubId: document.getElementById("kickClubId").value,
        userId: document.getElementById("kickUserId").value
    })
    .then(response => {
        // 처리로직
        const data = response.data

        console.log('kick user : ', data)
    })
    .catch(error => console.error(error));
}




// 클럽 삭제 기능
function deleteClub() {
    const clubId = document.getElementById("deleteClubId").value;
    console.log(`deleteClubId: ${clubId}`)
    axios.delete('/club', {
        data: {
            id: clubId
        }
    })
    .then(response => {
        console.log("club Code: ", response.data);
        document.getElementById("clubList").innerHTML = response.data;
    })
    .catch(error => console.error(error));
}

// 클럽 추가 기능
function addClub() {
    axios.post('/club', {
        name: "test1",
        categories: [
            { id: 1 },
            { id: 2 }
        ]
    })
    .then(response => {
        console.log("add club: 1", response.data);
        stompClient.subscribe('/sub/club/receive-message/' + response.data, function (data) {
            console.log('greeting: ' + data);
        });
    })
    .catch(error => console.error(error));
}

// 클럽 가입할 때
function enterClub() {
    axios.post('/club/join', {
        code: document.getElementById("enterClub").value
    })
    .then(response => {
        console.log("enterClub  club: ", response.data);
        stompClient.subscribe('/sub/club/receive-message/' + response.data, function (data) {
            console.log('greeting: ' + data);
        });
    })
    .catch(error => console.error(error));
}

$(function () {
    connect();
});

// 클럽 탈퇴할 때
function exitClub() {

    console.log('document.getElementById("exitClub").value : ', document.getElementById("exitClub").value)
    axios.delete('/club/exit', {
        data:{
            id: document.getElementById("exitClub").value
        }
    })
    .then(response => {
        console.log("exitClub  club: ", response.data);
        stompClient.unsubscribe('/sub/club/receive-message/' + response.data);
    })
    .catch(error => console.error(error));
}