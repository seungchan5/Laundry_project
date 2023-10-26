window.addEventListener('load', function(){

    if(document.querySelector('#couponPrice').value==''){
        document.querySelector('#coupon-row').style.display = 'none';
    }

    if(document.querySelector('#point').value==''){
        document.querySelector('#point-row').style.display = 'none';
    }



    let orders_Id = document.querySelector('#ordersId').textContent;

    let couponBtn = document.querySelector('#selectCoupon');
    couponBtn.addEventListener('click', function(){
        var options = 'width=600, height=400, top=100, left=100, resizable=yes, scrollbars=yes';
            window.open('/orders/' + orders_Id + '/members/' + memberId + '/coupons', '_blank');
    })









//    서버에서 계산해온 값
    let totalPriceFromServer = tPrice;
    let couponId = parseInt(document.querySelector('#coupon').value);

//    //숫자이외의 문자 입력 불가
//    document.querySelector('#point').addEventListener('keypress', function(event){
//
//        if(event.which < 48 || event.which > 57){
//            event.preventDefault();
//        }
//    })

    document.querySelector('#point').addEventListener('input', function(){

        let couponPrice = parseInt(document.querySelector('#couponPrice').value);

        let pointRow = document.querySelector('#point-row');

        if(document.querySelector('#point').value==''){
            document.querySelector('#point-row').style.display = 'none';
        }

            if(document.querySelector('#point').value != ''){
            pointRow.style.display = '';
            pointRow.querySelector('.content.discount').textContent = '- ' + Number(document.querySelector('#point').value).toLocaleString() + '원';
        }


//
        if(isNaN(couponPrice)){
            let pointValue = parseInt(document.querySelector('#point').value);
            if(totalPoint < pointValue){
               alert('보유한 포인트가 부족합니다.');
               document.querySelector('#point').value = '';
               document.querySelector('#point-row').style.display = 'none';
               document.querySelector('#totalPrice').innerHTML = Number(totalPriceFromServer).toLocaleString() + '원'
              }
        }
        else if(!isNaN(couponPrice)){
            let pointValue = parseInt(document.querySelector('#point').value);
            if(totalPoint < pointValue){
               alert('보유한 포인트가 부족합니다.');
               document.querySelector('#point').value = '';
               document.querySelector('#point-row').style.display = 'none';
               document.querySelector('#totalPrice').innerHTML = Number(totalPriceFromServer - couponPrice).toLocaleString() + '원';
               }
        }



//


        if(isNaN(couponPrice)){
            //입력되었을때 서버에서 계산해온 값으로 초기화
            document.querySelector('#totalPrice').innerHTML = Number(totalPriceFromServer).toLocaleString() + '원';
            let pointValue = parseInt(document.querySelector('#point').value);

            if(!isNaN(pointValue)){
                let temp = totalPriceFromServer - pointValue;
                document.querySelector('#totalPrice').innerHTML = Number(temp).toLocaleString() + '원';
            }
        }
        else if(!isNaN(couponPrice)){
            document.querySelector('#totalPrice').innerHTML = Number(totalPriceFromServer - couponPrice).toLocaleString() + '원';
            let pointValue = parseInt(document.querySelector('#point').value);

            if(!isNaN(pointValue)){
                let temp = totalPriceFromServer - couponPrice - pointValue;
                document.querySelector('#totalPrice').innerHTML = Number(temp).toLocaleString() + '원';
            }
        }










    })


// 전부사용 눌렀을때
    let pointBtn = document.querySelector('.pointbtn');
    pointBtn.addEventListener('click', function(){
        document.querySelector('#point').value = totalPoint;
        let couponPrice = parseInt(document.querySelector('#couponPrice').value);

        let pointRow = document.querySelector('#point-row');

        if(document.querySelector('#point').value==''){
            document.querySelector('#point-row').style.display = 'none';
        }

        if(document.querySelector('#point').value != ''){
            pointRow.style.display = '';
            pointRow.querySelector('.content.discount').textContent = '- ' + Number(document.querySelector('#point').value).toLocaleString() + '원';
        }

        if(isNaN(couponPrice)){
            //입력되었을때 서버에서 계산해온 값으로 초기화
            document.querySelector('#totalPrice').innerHTML = Number(totalPriceFromServer).toLocaleString() + '원';
            let pointValue = parseInt(document.querySelector('#point').value);

            if(!isNaN(pointValue)){
                let temp = totalPriceFromServer - pointValue;
                document.querySelector('#totalPrice').innerHTML = Number(temp).toLocaleString() + '원';
            }
        }
        else if(!isNaN(couponPrice)){
            document.querySelector('#totalPrice').innerHTML = Number(totalPriceFromServer - couponPrice).toLocaleString() + '원';
            let pointValue = parseInt(document.querySelector('#point').value);

            if(!isNaN(pointValue)){
                let temp = totalPriceFromServer - couponPrice - pointValue;
                document.querySelector('#totalPrice').innerHTML = Number(temp).toLocaleString() + '원';
            }
        }

    })











    var IMP = window.IMP;
    IMP.init("imp05521551");
});

function formatDate(date) {
        var year = date.getFullYear();
        var month = ('0' + (date.getMonth() + 1)).slice(-2);
        var day = ('0' + date.getDate()).slice(-2);
        var hours = ('0' + date.getHours()).slice(-2);
        var minutes = ('0' + date.getMinutes()).slice(-2);
        var seconds = ('0' + date.getSeconds()).slice(-2);
        var milliseconds = ('00' + date.getMilliseconds()).slice(-3);

        return year + '_' + month + '_' + day + '_' + hours + minutes + seconds + milliseconds;
    }

function requestPay() {

    let ordersId = document.querySelector('#ordersId').textContent;
    let couponListId = document.querySelector('input[name=coupon]').value;
    let couponPrice = document.querySelector('input[name=couponPrice]').value;
    let pointPrice = document.querySelector('input[name=point]').value;
    let totalPriceStr = document.querySelector('#totalPrice').textContent;
    let totalPrice = parseInt(totalPriceStr.replace(/[^0-9]/g, ''));


    var merchantUId = 'p'+ordersId+'_'+formatDate(new Date());

    var IMP = window.IMP;

    IMP.request_pay(
      {
        pg: "inicis",
        pay_method: "card",
        merchant_uid: merchantUId,
        name: "세탁",
        amount: totalPrice,
        buyer_name: memberName,
        buyer_tel: memberPhone,
        m_redirect_url: "http://localhost:8080/orders/" + ordersId
                        + "/payment/complete?"
                        + "couponListId=" + couponListId
                        + "&couponPrice=" + couponPrice
                        + "&pointPrice=" + pointPrice,
        notice_url : "http://localhost:8080/orders/"
                     + ordersId + "/payment/webhook?memberId=" + memberId + "&couponListId=" + couponListId + "&couponPrice=" + couponPrice + "&pointPrice=" + pointPrice

      },
        function (rsp) {
          console.log(rsp);
    }
  );
}
