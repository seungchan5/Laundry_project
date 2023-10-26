window.addEventListener('load', function() {

<!--        const pathArr = location.pathname.split("/");-->
<!--        const storeId = pathArr[pathArr.length-1];-->
<!--        const storeId = $('#orderId').val();-->
<!--        console.log(pathArr);-->
<!--        console.log(storeId);-->

        $(document).ready(function(){


            $(".btn").click(function(){
<!--                    const pathArr = location.pathname.split("/");-->
<!--                    const storeId = pathArr[pathArr.length-1];-->
                        const ordersId = $('#orderId').val();
            <!-------------------------------------------------------------------------------------------->

                    var formData = new FormData(document.getElementById("orderForm"));

                      fetch("/rider/test", {
                        method : 'post',
                        headers : {
                          'Content-Type': 'application/json'
                        },
                        body : JSON.stringify(formData)
                      })
                      .then(response => response.json())
                      .then(map => console.log(map));
<!-------------------------------------------------------------------------------------------->


                alert('주문이 완료되었습니다.');
<!--                location.href = "/oo/kk/" + storeId;-->

                let socket = new SockJS('/websocket');

                let stompClient = Stomp.over(socket);

                stompClient.connect({}, function() {
                    const message = {
                        message : "새 주문이 들어왔습니다"
                }
                    stompClient.send("/message/order-complete-message/" + ordersId, {}, JSON.stringify(message));
                    stompClient.disconnect();
                });
            });



            var formData = '';
            $('#orderForm').submit(function() {
//<!--           event.preventDefault(); // 폼 제출 기본 동작 방지-->

               var formData = new FormData(document.getElementById("orderForm"));
               console.log(formData);

                $.ajax({
                    url: "/websocket",
                    type: "POST",
                    data: formData,
                    processData: false,
                    contentType: false,
                    success: function(response) {
                        alert('주문이 완료되었습니다.');
                    },
                    error: function(error) {
                        console.error(error);
                    }
                });
            });

//            $('#sub').click(function(){
//                $('#orderForm').submit();
//                console.log(formData);
//
//                let socket = new SockJS('/websocket');
//
//                let stompClient = Stomp.over(socket);
//
//                stompClient.connect({}, function() {
//                    const message = {
//                        message : "새 주문 등록"
//                    }
//                    stompClient.send("/message/order-complete-message/" + storeId, {}, JSON.stringify(message));
//                    stompClient.disconnect();
//                });
//            });
        });

        // 관리자 페이지로 주문요청 메세지
        function messageSend() {
            let socket = new SockJS('/websocket');

            let stompClient = Stomp.over(socket);

            stompClient.connect({}, function() {
                const message = {
                    message : "새 주문이 들어왔습니다"
                }
                stompClient.send("/message/order-complete-message/" + storeId, {}, JSON.stringify(message));

                 const messageBody = JSON.parse(message.body);

                // 여기서 원하는 처리를 수행
                const orders = messageBody.a;
                const orderId = orders.ordersId; // 서버에서 받은 주문 ID

                // HTML 요소에 값을 적용 (예시)
                $("#orderStatus").text("주문 완료 - 주문 ID: " + orderId);
                   $("#aa").text(storeId);

		        stompClient.disconnect();
            });
        }
      });