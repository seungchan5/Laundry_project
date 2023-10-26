window.addEventListener('load',function(){

    let userPw = document.querySelector('#userPw');
    let userPwCheck = document.querySelector('#userPwCheck');
    let error = document.querySelector('.errors');
    let button_passwordChange = document.querySelector('#button_passwordChange');

    button_passwordChange.disabled = true;

    userPw.addEventListener('keyup',function(){
        // 문자열이 8~15자인 경우
        let regex1 = /^.{8,15}$/;

        // 대소문자, 숫자, 특수문자가 적어도 하나 이상 존재하는 경우
        let regex2 = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@#$%^&+=!]).*$/;

        if(regex1.test(userPw.value)){
            if(regex1.test(userPw.value) && regex2.test(userPw.value)){
                error.textContent = '';
                button_passwordChange.disabled = true;
            }else{
                error.textContent = '대소문자, 숫자, 특수문자가 적어도 하나씩 있어야 합니다.';
                button_passwordChange.disabled = true;
            }

        }else{
            error.textContent = '대소문자, 숫자, 특수문자를 포함해서 8자 이상 ~ 15자 이하로 설정해주세요.';
            button_passwordChange.disabled = true;
        }
    })

    userPwCheck.addEventListener('keyup',function(){

         // 문자열이 8~15자인 경우
         let regex1 = /^.{8,15}$/;

         // 대소문자, 숫자, 특수문자가 적어도 하나 이상 존재하는 경우
         let regex2 = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@#$%^&+=!]).*$/;

        if(regex1.test(userPw.value) && regex2.test(userPw.value) && userPw.value!=null && userPwCheck.value!=null &&
            userPw.value === userPwCheck.value){
            error.textContent = '';
            button_passwordChange.disabled = false;
        } else {
            error.textContent = '비밀번호가 일치하지 않습니다';
            button_passwordChange.disabled = true;
        }
    })

});
