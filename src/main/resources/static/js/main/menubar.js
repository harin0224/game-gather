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


//마이페이지
document.getElementById('club').addEventListener('click', () => {
    document.getElementById('iframecontent').src = '/club';
});

