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

//document.addEventListener('DOMContentLoaded', () => {
//  const randomMatchButton = document.getElementById('randommatch');
//  const rchatStartButton = document.getElementById('rchatstart');
//
//  if (randomMatchButton) {
//    randomMatchButton.addEventListener('click', () => {
//      document.getElementById('iframecontent').src = '/match/match';
//            if (rchatStartButton) {
//              rchatStartButton.addEventListener('click', () => {
//                document.getElementById('iframecontent').src = '/templates/rmatch/rmatch.html';
//              });
//            }
//    });
//  }
//});




//document.getElementById('randommatch').addEventListener('click', () => {
//
//  document.getElementById('iframecontent').src = '/match/match';
//});
//
//
//
//document.getElementById('rchatstart').addEventListener('click', () => {
//  // 3번 버튼 클릭 시 3번 화면 변경
//  document.getElementById('iframe-content').src = '/rchat/rchat';
//});
//
//
