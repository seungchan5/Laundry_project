document.querySelector('#memberPhonenumber').addEventListener('input',function(){
    document.querySelector('.validation-phonenumber').value='';
})

function phoneNum(){
    let num = document.querySelector('#memberPhonenumber');
    if (num.value.length == 3 || num.value.length == 8){
        num.value += "-";
        document.querySelector('#sendSmsBtn').disabled = false;
    }
}

// 인증번호 보내기
//function sendSms(){
//    let subPhonenumber = document.querySelector('#memberPhonenumber');
//    let phonenumber = subPhonenumber.value.replace(/-/gi,'');
//    let sendSmsBtn = document.querySelector('#sendSmsBtn');
//    let inputCode = document.querySelector('.phonenumber-check-input');
//    let resultMsg = document.querySelector('#phonenumber-check-warn');
//    let regex = /^\d{3}-\d{4}-\d{4}$/;
//
//    if(regex.test(subPhonenumber.value)){
//         fetch("/sendSms?phonenumber="+phonenumber)
//            .then(response => response.json())
//            .then(map => {
//            		alert("인증번호가 전송되었습니다.");
//            		// 인증번호가 전송되면 readOnly 해제
//            		inputCode.readOnly = false;
//
//            		// 인증번호가 전송되면 발송버튼 막기
//            		sendSmsBtn.disabled = true;
//
//            		// 인증번호 검증
//            		phone_check_number = map.number;
//            		document.querySelector('.phonenumber-check-button').addEventListener('click', function(){
//            			if(phone_check_number == inputCode.value){
//            		        alert('인증되었습니다.');
//            		        document.querySelector('.validation-phonenumber').value=1;
//            		        sendSmsBtn.disabled = false;
//            		        inputCode.readOnly = true;
//            			}else{
//            				alert('인증번호가 불일치 합니다. 다시 확인해주세요!');
//            		        resultMsg.style.color = 'red';
//            		        sendSmsBtn.disabled = false;
//            			}
//            		})
//            	})
//    }else{
//        alert("휴대폰 번호를 다시 확인해주세요.");
//    }
//
//}

// 인증번호 보내기
function sendSms(){
    let subPhonenumber = document.querySelector('#memberPhonenumber');
    let phonenumber = subPhonenumber.value.replace(/-/gi,'');
    let sendSmsBtn = document.querySelector('#sendSmsBtn');
    let resultMsg = document.querySelector('#phonenumber-check-warn');

    if(subPhonenumber.value != null && subPhonenumber.value !== ''){
        fetch("/check/sendSMS?to="+phonenumber)
        .then(response => response.json())
        .then(map => {
        		alert("인증번호가 전송되었습니다.");
        		phone_check_number = map;
        		//document.querySelector('.phonenumber-check-input').disabled = false;
        		document.querySelector('.phonenumber-check-button').addEventListener('click', function(){
        			let inputCode = document.querySelector('.phonenumber-check-input');

        			if(phone_check_number == inputCode.value){
        		        alert('인증되었습니다.');
        		        document.querySelector('.validation-phonenumber').value=1;


        			}else{
        				resultMsg.innerHTML = '인증번호가 불일치 합니다. 다시 확인해주세요!';
        		        resultMsg.style.color = 'red';
        			}
        		})
        	})
    }else{
        alert('전화번호를 입력하세요.');
    }

}

    function validationSubmit(){
    let validationPhonenumber = document.querySelector('.validation-phonenumber').value;
    let memberName = document.querySelector('#memberName').value;
    let memberAccount = document.querySelector('#memberAccount').value;
    let memberPhonenumber = document.querySelector('#memberPhonenumber').value;

    fetch("/login/find-pw", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
         },
        body: JSON.stringify({
            memberName: memberName,
            memberAccount: memberAccount,
            memberPhone: memberPhonenumber
        })
    })
    .then((response) => response.json())
    .then(map => {
        if(map.list.length > 0 && validationPhonenumber == 1 && memberName !== ''){
            document.querySelector('.find_Id_form').submit();
        }else{
           alert("아이디, 이름, 전화번호를 다시 확인해주세요.");
        }
    })
    .catch(() => {
        alert("아이디, 이름, 전화번호를 다시 확인해주세요.");
    })


}

