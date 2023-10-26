window.addEventListener('load', function(){

// SDK를 초기화 합니다. 사용할 앱의 JavaScript 키를 설정해 주세요.
    if (!Kakao.isInitialized()) {
        Kakao.init('7f5f612c37590a5d3b0a2cfec0c466a4');
    };


    // SDK 초기화 여부를 판단합니다.
    console.log(Kakao.isInitialized());

    let kShare = document.querySelector('#kShare');
    kShare.addEventListener('click', function(){
        Kakao.Link.sendDefault({
                objectType: 'feed',
                content: {
                  title: '세탁! 걱정할 시간도 아끼세요! ',
                  description: '초대받고 가입하면 초대받은 친구도 초대한 친구도 5,000P 적립 혜택!',
                  imageUrl: 'https://blog.kakaocdn.net/dn/bI9Kl8/btssfQ5fx4u/PAykLrfZ9OD5YfYv82jKVk/img.png',
                  link: {
                    mobileWebUrl: 'http://localhost:8080',
                    webUrl: 'http://localhost:8080',
                  },
                },
                buttons: [
                  {
                    title: '세탁하러 가기',
                    link: {
                      mobileWebUrl: 'http://localhost:8080',
                      webUrl: 'http://localhost:8080',
                    },
                  },
                ],
                // 카카오톡 미설치 시 카카오톡 설치 경로이동
                //installTalk: true,
              })
    })
});