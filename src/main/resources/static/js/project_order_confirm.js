window.addEventListener('load', function(){

    let form = document.querySelector('form');
    let error = document.querySelector('.error');

    form.addEventListener('submit', function(event){
        let radioPw = document.querySelector('input[name=isPw]:checked');
        if (radioPw == null) {
            event.preventDefault();
            document.querySelector('input[name=isPw]').parentElement.scrollIntoView({ behavior : 'smooth'});
            error.innerHTML = '공동현관 비밀번호 유무를 선택해주세요.'
            error.classList.remove('disabled');
            return;
        }
        if (radioPw.id == 'O'){
            let pw = document.querySelector('input[name=password]').value;
            if (pw == '') {
                event.preventDefault();
                document.querySelector('input[name=isPw]').parentElement.scrollIntoView({ behavior : 'smooth'});
                error.innerHTML = '현관 비밀번호를 입력해주세요';
                error.classList.remove('disabled');
            }

        }


    })

    document.querySelector('#changeLocation').addEventListener('click', function(){
        var options = 'width=600, height=400, top=100, left=100, resizable=yes, scrollbars=yes';
        window.open('/laundry/order/pickup', '_blank')
    })


    document.querySelector('#selectCoupon').addEventListener('click', function(){
        var options = 'width=600, height=400, top=100, left=100, resizable=yes, scrollbars=yes';
        window.open('/laundry/order/coupons/select', '_blank')
    })

    let changeAddress = document.querySelector('#changeAddress');
    changeAddress.addEventListener('click', function(event){
        event.preventDefault();
        searchAddress();
    })

    let couponInput  = document.querySelector('input[name=coupon]');
    couponInput.addEventListener('change', function(e){
        console.log('onInput', e.target.value);
    })


})

// 공동현관 비밀번호 radio 버튼 연동
function toogleRadio(element) {
    const radio = element.previousElementSibling;
    if (!radio.checked){
        radio.checked = !radio.checked;
    }
    if (element.id == 'pwX'){
        document.querySelector('input[name=password]').disabled = true;
    } else {
        document.querySelector('input[name=password]').disabled = false;
    }
}



// MutationObserver 콜백 함수
function handleMutation(mutationsList, observer) {
    mutationsList.forEach(function (mutation) {
        if (mutation.type === 'childList' && mutation.target.nodeName === 'INPUT' && mutation.target.type === 'text') {
            // input:text 요소의 내용이 변경되었을 때 실행할 코드를 여기에 작성
            console.log('input:text 값이 변경되었습니다.');
        }
    });
}

function searchAddress(){
    new daum.Postcode({
        oncomplete: function(data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수


            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if(data.userSelectedType === 'R'){
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraAddr !== ''){
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 조합된 참고항목을 해당 필드에 넣는다.

            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            document.querySelector('#zipcode').value = data.zonecode;
            document.querySelector("#addressInput").value = addr + extraAddr;
            document.querySelector("#address").textContent = addr + extraAddr;
            // 커서를 상세주소 필드로 이동한다.
//                    document.querySelector("#detailAddress").focus();
        }
    }).open();

} // 주소 API

