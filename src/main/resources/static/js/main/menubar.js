document.getElementById('randommatch').addEventListener('click', () => {
    document.getElementById('iframecontent').src = '/match/match';
});

//친구
document.getElementById('friend').addEventListener('click', () => {
    document.getElementById('iframecontent').src = '/friend/list';
});

//마이페이지
document.getElementById('mypage').addEventListener('click', () => {
    document.getElementById('iframecontent').src = '/member/mypage';
});


//모임
document.getElementById('club').addEventListener('click', () => {
    document.getElementById('iframecontent').src = '/club';
});

//친구 채팅
document.getElementById('fredchat').addEventListener('click', () => {
    document.getElementById('iframecontent').src = '/fredchat/fredchat';
});

// 로그아웃
document.getElementById('logout').addEventListener('click', () => {
    fetch('/member/logout', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        credentials: 'same-origin'
    })
    .then(response => {
        if (response.ok) {
            window.location.href = '/member/login';
        } else {
            alert('Logout failed');
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
});