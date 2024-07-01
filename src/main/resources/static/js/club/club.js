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


// 가입된 클럽 리스트 가져오는 부분
function getClubList() {
    axios.get('/club/list')
        .then(response => {
            console.log("club Code: ", response.data);
            const clubList = response.data;
            clubListData = clubList
            currentClubId = clubList[0]?.id
            // club-bar 내의 ul 요소 선택
            const clubUl = document.querySelector('#club-bar ul');

            // 기존 리스트 항목들 제거
            clubUl.innerHTML = '';

            // 새로운 클럽 리스트 항목 추가
            clubList.forEach(club => {
                const li = document.createElement('li');
                li.textContent = club.name; // club 객체의 name 속성 사용
                clubUl.appendChild(li);

                // WebSocket 구독 설정
                stompClient.subscribe('/sub/club/receive-message/' + club.id, function (greeting) {
                    // 메시지 수신 처리
                });
            });
        })
        .catch(error => console.error(error));
}

//message 보내는 기능
let clubListData = []
let currentClubId = 0; // 클럽 리스트를 가져오고 현재 선택된(보고있는 화면)의 클럽 아이디를 넣어주고 해당 클럽에 메세지 보낼것
function sendMessage() {
    console.log(`currentClubId ${currentClubId}`)
    stompClient.send(`/pub/club/send-message/${currentClubId}`, {}, JSON.stringify(
        {
            'message':$("#message").val(),
            'senderId': 1 //실제로 보내는 유저의 id값을 넣어줘야함
        })
    );
}

// 채팅 데이터 가져오기
function loadClubChat(clubId) {

    console.log(`loadClubChat : /club/chat?id=${clubId}`)
     axios.get(`/club/chat?id=${clubId}`)
    .then(response => {
        // 처리로직
        const data = response.data
        console.log('chat log : ', data)
        const $log = $("#main-chat")

        for(const idx of data) {
            const $newDiv = $('<div>');
            $newDiv.text(`${idx.userId}: ${idx.chatLog} ${idx.createdAt}`);
            $newDiv.addClass('main-chat-message-div');
            $log.append($newDiv);
        }
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

    const categoryListDom = document.getElementById('category-list');
    const selectedCategories = Array.from(categoryListDom.selectedOptions).map(option => option.value);
    const mySelect = document.getElementById('my-select');
    const categories = selectedCategories.map(id => ({ id: id }));
    const clubName = document.getElementById('club-name').value;
    axios.post('/club', {
        name: clubName,
        categories
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
        code: document.getElementById("club-code").value
    })
    .then(response => {
        console.log("enterClub  club: ", response.data);
        alert('가입되었습니다.')
        closePopup();
        stompClient.subscribe('/sub/club/receive-message/' + response.data, function (data) {
            console.log('greeting: ' + data);
        });
        getClubList();
    })
    .catch(error => console.error(error));
}

function showMainChatView() {
    const club = clubListData[currentClubId]

    console.log("current club data: ",club)

    $("#main-title>span").text(club.name)
    $("#show-users").text(club.members?.length);
    loadClubChat(club.id);

}

function clickEvents() {

    $(document).on('click', '#club-bar > ul > li', function() {
      const index = $(this).index();

      console.log('index : ', index)
      currentClubId = index
      showMainChatView();
    });
}

// 페이지 로딩시 실행되는 부분
$(function () {
    connect();
    getClubList();
    clickEvents()
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