var stompClient = null;

const $currentClub = $('<div></div>');
$currentClub.attr('id', 'currentId');
let clubListData = []
let currentClubId = 0;
let categories = [];


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
async function connect() {
    return new Promise((resolve, reject) => {
        var socket = new SockJS('/club-websocket');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            resolve(stompClient);
        }, function (error) {
            console.error('STOMP error:', error);
            reject(error);
        });
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
async function getClubList() {
    const response = await axios.get('/club/list')
    const clubList = response.data;

    console.log('getClubList: ',JSON.stringify(clubList ), clubList.length)
    clubListData = clubList
    currentClubId = clubList[0]?.id

    // club-bar 내의 ul 요소 선택
    const clubUl = document.querySelector('#club-bar ul');

    // 기존 리스트 항목들 제거
    clubUl.innerHTML = '';

    // 새로운 클럽 리스트 항목 추가
    response.data.map(club => {
        console.log('add club data : ', club)
        const li = document.createElement('li');
        li.textContent = club.name; // club 객체의 name 속성 사용
        clubUl.appendChild(li);

        // WebSocket 구독 설정
        stompClient.subscribe('/sub/club/receive-message/' + club.id, function (greeting) {
            loadClubChat(JSON.parse(greeting.body).clubId)
        });
    });
}

//message 보내는 기능
// 클럽 리스트를 가져오고 현재 선택된(보고있는 화면)의 클럽 아이디를 넣어주고 해당 클럽에 메세지 보낼것
function sendMessage(id) {
    console.log(`currentClubId ${id} ${currentClubId}`)
    stompClient.send(`/pub/club/send-message/${id}`, {}, JSON.stringify(
        {
            'message':$("#chat-input").val(),
            'senderId': 1 //실제로 보내는 유저의 id값을 넣어줘야함
        })
    );
    $("#chat-input").val('')
}

// 채팅 데이터 가져오기
async function loadClubChat(clubId) {

    if(clubId !== clubListData[currentClubId].id) {
        return
    }


    console.log(`loadClubChat : /club/chat?id=${clubId}`)
     axios.get(`/club/chat?id=${clubId}`)
    .then(response => {
        // 처리로직
        const data = response.data
        console.log('chat log : ', data)
        const $log = $("#main-chat")
        $log.text('')

        for(const idx of data) {
            const $newDiv = $('<div>');
            $newDiv.text(`${idx.userIdFromUsersTable}: ${idx.chatLog} ${idx.createdAt}`);
            $newDiv.addClass('main-chat-message-div');
            $log.append($newDiv);
        }
    })
    .catch(error => console.error(error));
}

async function kickUser(userId) {
    const clubId = $("#user-popup").data('clubId');

    console.log('kick user' ,userId, "  clubId : ", clubId)
    await axios.patch(`/club/member`,{
        clubId ,
        userId
    })

    showMainChatView()
}




// 클럽 삭제 기능
async function deleteClub(id) {
    console.log(`deleteClubId: ${id}`)
    axios.delete('/club', {
        data: {
            id
        }
    })
    .then(response => {
        console.log("club Code: ", response.data);
        getClubList();
        cleanView();
    })
    .catch(error => console.error(error));
}


// 클럽 추가 기능
function addClub() {
    const categoryListDom = document.getElementById('category-list');
    const selectedCategories = Array.from(categoryListDom.selectedOptions).map(option => option.value);
    const categories2 = selectedCategories.map(id => ({ id: id }));

    console.log(`addClub selected : `,categories2)
    const clubName = document.getElementById('club-name').value;
    axios.post('/club', {
        name: clubName,
        categories:categories2
    })
    .then(response => {
        console.log("add club: 1", response.data);
        getClubList();
        closePopup();
        stompClient.subscribe('/sub/club/receive-message/' + response.data, function (data) {
            loadClubChat(JSON.parse(data.body).clubId)
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
            loadClubChat(JSON.parse(data.body).clubId)
        });
        getClubList();
    })
    .catch(error => console.error(error));
}

function cleanView() {
console.log('clean~')
    $("#main-title>span").text(' ')
    $("#show-users").text(' ');
    $("#user-popup").text(' ');
    $("#main-chat").text(' ');
}

function showMainChatView() {
    let club = clubListData[currentClubId]
    console.log("current club data: ",club)

    $("#main-title>span").text(club.name)
    $("#show-users").show()
    $("#show-users").text(club.members?.length);
    loadClubChat(club.id);
    $("#user-popup").text('');
    $("#user-popup").data('clubId',club.id);
    club.members.map(v=>{
        console.log('add user : ', v)

        const $newDiv = $('<div>');
        $newDiv.text(`${v.name}`);
        $newDiv.addClass('member-user-list');
        $newDiv.data('user-id', v.no);
        if(club.status ==='MANAGER'){
            $newDiv.append(`<button  class="kick-user">추방</button>`);
        }
        $('#user-popup').append($newDiv);
    })
}

function clickEvents() {


    $(document).on('click', '#sendMessageBtn', function() {
      const id = $currentClub.data('id')

      sendMessage(clubListData[id].id);
    });



     $(document).on('click', '.kick-user', function() {
        const userId = $(this).closest('.member-user-list').data('user-id');
        console.log('clicked userId : ', userId)
        kickUser(userId);
    });

    $('#show-users').click(function() {
        $('#user-popup').removeClass('hidden');

        $(document).on('click', function(event) {
            if (!$('#user-popup').is(event.target) && $('#user-popup').has(event.target).length === 0 && event.target.id !== 'show-users') {
                $('#user-popup').addClass('hidden');
                $(document).off('click');
            }
        });
    });

    $(document).on('click', '#club-bar > ul > li', function() {
      const index = $(this).index();
      console.log('index : ', index)
      currentClubId = index
      $currentClub.data('id',index)

      console.log('temp : ', $currentClub.data('id'))
      showMainChatView();
    });

    const $clubBar = $("#club-bar");
    const $customMenu = $("#custom-menu");

    $clubBar.on("contextmenu", "li", function (e) {
      e.preventDefault();
      console.log('Context menu opened');
      const $this = $(this);
      const index = $this.index();

     $("#deleteClub").css("display", "none");

      const rect = this.getBoundingClientRect();
      const x = e.clientX - rect.left;
      const y = e.clientY - rect.top;

      $customMenu.css({
        display: "block",
        left: `${e.clientX}px`,
        top: `${e.clientY}px`,
      });

      currentRightClickClubId = index;

      const currentClub = clubListData[currentRightClickClubId]
      console.log('cuurent club right clicked',currentClub )

      if(currentClub.status === 'MANAGER') {
        $("#deleteClub").css("display", "block");
      }
    });

    $(document).on("click", function () {
      $customMenu.css("display", "none");
    });

    $customMenu.on("click", "li", function (e) {
      const selectedClub = $customMenu.data("selectedClub");
      const action = $(this).text();
      console.log(`Action: ${action}, Club: ${selectedClub}`);
      $customMenu.css("display", "none");
    });


    $('#copyClub').on('click', function() {
        copyToClipboard(clubListData[currentRightClickClubId].code).then(()=>{
        alert('복사 완료')})
    });

    // 모임 탈퇴 클릭 이벤트
    $('#outClub').on('click', function() {
      // 모임 탈퇴 로직 작성
      exitClub(clubListData[currentRightClickClubId].id)
      console.log('모임 탈퇴');
    });

    // 모임 삭제(관리자) 클릭 이벤트
    $('#deleteClub').on('click', function() {
      // 모임 삭제 로직 작성
      console.log('모임 삭제(관리자)');
      deleteClub(clubListData[currentRightClickClubId].id)
    });
}





// 클럽 탈퇴할 때
function exitClub(id) {
    console.log('document.getElementById("exitClub").value : ', id )
    axios.delete('/club/exit', {
        data:{
            id
        }
    })
    .then(response => {
        console.log("exitClub  club: ", response.data);
        stompClient.unsubscribe('/sub/club/receive-message/' + response.data);
        getClubList()
        cleanView()

    })
    .catch(error => console.error(error));
}


function createClub() {
  document.getElementById("add-club-popup").style.display = "block";
}

function showJoinClub() {
  closePopup();
  document.getElementById("join-club-popup").style.display = "block";
}

function showCreateClub() {
  closePopup();
  document.getElementById("create-club-popup").style.display = "block";
}

function closePopup() {
  document.getElementById("add-club-popup").style.display = "none";
  document.getElementById("join-club-popup").style.display = "none";
  document.getElementById("create-club-popup").style.display = "none";
}




function getCategories() {
    axios
    .get('/club/categories')
    .then(response => {
        categories = response.data;
        console.log(`data : `,categories)
        populateCategoryList();
    })
}

function populateCategoryList() {
    $('#category-list').empty();
    categories.forEach(category => {
        $('#category-list').append($('<option>', {
            value: category.id,
            text: category.title
        }));
    });
}

function selectCategory(category) {
    document.getElementById('club-description').value = category;
    document.getElementById('category-list').style.display = 'none';
}

function copyToClipboard(text) {
  if (navigator.clipboard && window.isSecureContext) {
    return navigator.clipboard.writeText(text);
  } else {
    const textArea = document.createElement("textarea");
    textArea.value = text;
    textArea.style.position = "fixed";
    document.body.appendChild(textArea);
    textArea.focus();
    textArea.select();

    try {
      const successful = document.execCommand('copy');
      const msg = successful ? '성공적으로 복사됨' : '복사 실패';
      console.log(msg);
    } catch (err) {
      console.error('클립보드 복사 중 오류 발생:', err);
    }

    document.body.removeChild(textArea);
  }
}


function addMessage(idx) {
    const $log = $("#main-chat")
    const $newDiv = $('<div>');
    $newDiv.text(`${idx.userIdFromUsersTable}: ${idx.chatLog} ${idx.createdAt}`);
    $newDiv.addClass('main-chat-message-div');
    $log.append($newDiv);
}




// 페이지 로딩시 실행되는 부분
$(function  () {
    init();
});

async function init() {
    await connect();
    await getClubList();
    await clickEvents()
    await getCategories();
}