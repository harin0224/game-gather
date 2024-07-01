let isIframeOpen = false;

document.getElementById('randommatch').addEventListener('click', () => {
  if (!isIframeOpen) {
    document.getElementById('iframecontent').src = '/match/match';
    isIframeOpen = true;
  }
  else {
    document.getElementById('iframecontent').src = '/rchat/rchat';
    isIframeOpen = false;
  }
});

//마이페이지
document.getElementById('mypage').addEventListener('click', () => {
    document.getElementById('iframecontent').src = '/member/mypage';
});


//마이페이지
document.getElementById('club').addEventListener('click', () => {
    document.getElementById('iframecontent').src = '/club';
});

