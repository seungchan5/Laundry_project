window.addEventListener('load', function(){

let sendSmsBtn = document.querySelector('#sendSmsBtn');
let button_phoneChange = document.querySelector('#button_phoneChange');

sendSmsBtn.disabled = true;

let phone = document.querySelector('#phone');
phone.addEventListener('keyup', function(){

    let errors = document.querySelectorAll('.errors');
    let sendSmsBtn = document.querySelector('#sendSmsBtn');
    let button_phoneChange = document.querySelector('#button_phoneChange');
    if(phone.value==''){
        sendSmsBtn.disabled = true;
        button_phoneChange.disabled = false;
    }
    if(!phone_check(phone.value)){
        errors[0].textContent = '휴대폰 번호 형식으로 숫자만 입력해주세요';
        sendSmsBtn.disabled = true;
        button_phoneChange.disabled = false;
    }
    if(phone_check(phone.value) && phone.value!=''){
        errors[0].textContent = '';
        sendSmsBtn.disabled = false;
        button_phoneChange.disabled = false;
    }
});

button_phoneChange.addEventListener('click', function(){

    let errors = document.querySelectorAll('.errors');
    let phoneCheckRes = document.querySelector('#phoneCheckRes');
    let sendSmsBtn = document.querySelector('#sendSmsBtn');
    let button_phoneChange = document.querySelector('#button_phoneChange');

    if(phoneCheckRes.value=='0'){
        errors[1].textContent = '휴대폰 인증절차를 진행해주세요';
        sendSmsBtn.disabled = true;
        button_phoneChange.disabled = true;
    }

});

//sendSmsBtn.addEventListener('click',function(e){
//
//    e.preventDefault();
//
//    let number = document.querySelector('#phone').value;
//    let phonenumber = number.replace(/-/gi,'');
//    let phoneCheckRes = document.querySelector('#phoneCheckRes');
//    let button_phoneChange = document.querySelector('#button_phoneChange');
//
//    fetch("/sendSms?phonenumber="+phonenumber)
//    .then(response => response.json())
//    .then(map => {
//    		alert("인증번호가 전송되었습니다.");
//    		phone_check_number = map.number;
//    		//document.querySelector('.phonenumber-check-input').disabled = false;
//    		document.querySelector('#smsValueCheck').addEventListener('click', function(){
//    			let inputCode = document.querySelector('#phoneCheck').value;
//                let errors = document.querySelectorAll('.errors');
//    			if(phone_check_number == inputCode){
//    		        alert('인증되었습니다.');
//    		        phoneCheckRes.value='1';
//                    button_phoneChange.disabled = false;
//    		        errors[1].textContent = '';
//    			}else{
//    				alert('인증 실패하였습니다. 인증번호를 확인하시기 바랍니다');
//    			}
//    		})
//    	})
//    })
//});

sendSmsBtn.addEventListener('click',function(e){

    e.preventDefault();

    let number = document.querySelector('#phone').value;
    let phonenumber = number.replace(/-/gi,'');
    let phoneCheckRes = document.querySelector('#phoneCheckRes');
    let button_phoneChange = document.querySelector('#button_phoneChange');

    fetch("/check/sendSMS?to="+phonenumber)
    .then(response => response.json())
    .then(map => {
    		alert("인증번호가 전송되었습니다.");
    		phone_check_number = map;
    		//document.querySelector('.phonenumber-check-input').disabled = false;
    		document.querySelector('#smsValueCheck').addEventListener('click', function(){
    			let inputCode = document.querySelector('#phoneCheck').value;
                let errors = document.querySelectorAll('.errors');
    			if(phone_check_number == inputCode){
    		        alert('인증되었습니다.');
    		        phoneCheckRes.value='1';
                    button_phoneChange.disabled = false;
    		        errors[1].textContent = '';
    			}else{
    				alert('인증 실패하였습니다. 인증번호를 확인하시기 바랍니다');
    			}
    		})
    	})
    })
});

function phone_check(memberPhone){

	var regEx = /^(01[0|1|6|7|8|9]{1})-?[0-9]{3,4}-?[0-9]{4}$/;

	return regEx.test(memberPhone);
};

function phoneNum(){
    let num = document.querySelector('#phone');

    if (num.value.length == 3 || num.value.length == 8){
        num.value += "-";
    }
}
