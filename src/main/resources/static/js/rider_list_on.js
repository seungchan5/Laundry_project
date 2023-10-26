 window.addEventListener('load', function() {

            getList();

            // 주문 완료 메세지 받기
            const socket = new SockJS('/websocket');
            const stompClient = Stomp.over(socket);
            stompClient.connect({}, function() {

//            const storeId = '63';

//            stompClient.subscribe('/topic/order-complete/'+ordersId, function(message) {
            stompClient.subscribe('/topic/order-complete', function(message) {

            const messageBody = JSON.parse(message.body);

            const orders = messageBody.a;
            const orderId = orders.ordersId;
<!--  ------------------------------------------------------------------------------------------ -->

            <!-- 배달 지역 -->
            const address = orders.ordersAddress;
            console.log(address);
            var parts = address.split(" ")[1];
            console.log(parts);

            <!-- 라이더 희망지역 -->
            const riderArea = $("#workingArea").val();

            var headers = {};
            headers["appKey"]="5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2";

            var endX;
            var endY;
            var dis;

            if(riderArea === parts){
            $.ajax({
            method: "GET",
            headers: headers,
            url: "https://apis.openapi.sk.com/tmap/geo/fullAddrGeo?version=1&format=json",
            async: false,
            data: {
                "coordType": "WGS84GEO",
                "fullAddr": address
            },
            success: function(response) {
            if(orders.ordersStatus === 10){
                 endX = 126.97371230861432
                  endY = 37.564496383758915
            }else{
                var resultInfo = response.coordinateInfo;
                console.log(resultInfo);
                console.log("위도: " + resultInfo.coordinate[0].newLatEntr);
                console.log("경도: " + resultInfo.coordinate[0].newLonEntr);

                console.log(resultInfo.coordinate[0].lon);
                console.log(resultInfo.coordinate[0].lat);

                console.log(resultInfo.coordinate[0].adminDong);

                    if (resultInfo.coordinate[0].adminDong === "" || resultInfo.coordinate[0].adminDong === null) {
                        endX = resultInfo.coordinate[0].newLonEntr;
                        endY = resultInfo.coordinate[0].newLatEntr;
                    }else {
                        endX = resultInfo.coordinate[0].lon;
                        endY = resultInfo.coordinate[0].lat;
                    }
            }
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
//        console.log(endY);
//        console.log(endX);
//        console.log("=================================" + orders.ordersStatus);
//        if($('#orderStatus').val() === 10){
//              endX = 126.97371230861432
//              endY = 37.564496383758915
//        }
                console.log("변경 : " + endY);
                console.log("변경 : " + endX);

        navigator.geolocation.getCurrentPosition((position) => {

                    console.log(position.coords.latitude);
                    console.log(position.coords.longitude);
                    var searchOption = "12";

                    var trafficInfochk = "N";

                    var headers = {};
                    headers["appKey"]="5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2";

                    $.ajax({
                    type : "POST",
                    headers : headers,
                    url : "https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result&appKey=5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2",
                    async : false,
                    data : {
                         "startX" : position.coords.longitude,
                         "startY" : position.coords.latitude,
//                         "startX" : 126.94561942060331,
//                         "startY" : 37.55652422737718,
                         "endX" : endX,
                         "endY" : endY,
                         "reqCoordType" : "WGS84GEO",
                         "resCoordType" : "EPSG3857",
                         "searchOption" : searchOption,
                         "trafficInfo" : trafficInfochk
                    },
                    success : function(response) {

                     var resultData = response.features;

                     var tDistance = "거리 : " + (resultData[0].properties.totalDistance / 1000)
                                 .toFixed(1) + "km";
                    console.log(tDistance);
//                    $('.delivery_dis').eq(0).html(tDistance);

                    dis = (resultData[0].properties.totalDistance / 1000).toFixed(1);

                    if(10 >= dis ){
                        $(".drive_none").hide();

                        var listItem = '' ;

                        if(orders == null){
                            listItem =`
                                <li class='drive_none'>
                                    <span>현재 배달 요청이 없습니다!</span>
                                </li>`;
                        }else if(riderArea === parts){
                            var count = parseInt($(".ride_num").text(), 10);
                            count++;
                            $(".ride_num").text(count + "건");

                            var rideNumString = $(".ride_num").html(); // "2건" 또는 다른 값

                            // 정규 표현식을 사용하여 숫자만 추출
                            var rideNum = parseInt(rideNumString.match(/\d+/)[0], 10);
                            console.log(rideNum);
                            $('.active').html('대기중('+ rideNum + ')');

                            listItem = `
                            <a href='/ride/orders/${orders.ordersId}'>
                                 <li style="${orders.ordersStatus == '2' ? 'background-color: #00ffff38;' : ''}">
                                    <span class='order_num'>주문번호 :&nbsp;</span><span class='order_num' name="ordersId" >${orders.ordersId} (${orders.ordersStatus == '2' ? '집' : '업체'})</span><br>
                                    <span class='delivery_address'>${orders.ordersAddress} ${orders.ordersAddressDetails}</span>
                                    <div class='space'>
                                        <span class='delivery_time'>${orders.ordersDate}</span>
                                        <span class='delivery_dis'>${tDistance}</span>
                                    </div>
                                </li>
                            </a>`;
                        }
                        $("#orderList").prepend(listItem);
                        $('.delivery_dis').eq(0).html(tDistance);
                    }
                    }
                });
            });
        }



<!--  ------------------------------------------------------------------------------------------ -->
//            $(".drive_none").hide();
//
//            var listItem = '' ;
//
//            if(orders == null){
//            listItem =`
//                <li class='drive_none'>
//                    <span>현재 배달 요청이 없습니다!</span>
//                </li>`;
//            }else if(riderArea === parts){
//                var count = parseInt($(".ride_num").text(), 10);
//                count++;
//                $(".ride_num").text(count + "건");
//
//                listItem = `
//                <a href='/ride/orders/${orders.ordersId}'>
//                    <li>
//                        <span class='order_num'>주문번호 :&nbsp;</span><span class='order_num' name="ordersId" >${orders.ordersId}</span><br>
//                        <span class='delivery_address'>${orders.ordersAddress} ${orders.ordersAddressDetails}</span>
//                        <div class='space'>
//                            <span class='delivery_time'>${orders.ordersDate}</span>
//                            <span class='delivery_dis'>100km</span>
//                        </div>
//                    </li>
//                </a>`;
//            }
//            $("#orderList").prepend(listItem);
            });
        });
    });


//        function distance(endY, endX){
//            navigator.geolocation.getCurrentPosition((position) => {
//
//                    console.log(position.coords.latitude);
//                    console.log(position.coords.longitude);
//                    var searchOption = "12";
//
//                    var trafficInfochk = "N";
//
//                    var headers = {};
//                    headers["appKey"]="5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2";
//
//                    $.ajax({
//                    type : "POST",
//                    headers : headers,
//                    url : "https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result&appKey=5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2",
//                    async : false,
//                    data : {
//                         "startX" : position.coords.longitude,
//                         "startY" : position.coords.latitude,
//                         "endX" : endX,
//                         "endY" : endY,
//                         "reqCoordType" : "WGS84GEO",
//                         "resCoordType" : "EPSG3857",
//                         "searchOption" : searchOption,
//                         "trafficInfo" : trafficInfochk
//                    },
//                    success : function(response) {
//
//                     var resultData = response.features;
//
//                     var tDistance = "거리 : " + (resultData[0].properties.totalDistance / 1000)
//                                 .toFixed(1) + "km";
//                    console.log(tDistance);
//                    $('.delivery_dis').eq(0).html(tDistance);
//
//                     var tTime = " 총 시간 : "
//                           + (resultData[0].properties.totalTime / 60)
//                                 .toFixed(0) + "분";
//                    }
//                });
//            });
//        }

function getList(){
    var riderId = $('#quickRiderId').val();
    console.log(riderId);
    var ordersStatus = "대기중";
    console.log(ordersStatus);
    var workingArea = $('#workingArea').val();

    fetch('/ride/getList/'+ riderId + '/' + ordersStatus + '/' + workingArea)
    .then(response => response.json())
    .then(map => {
        var info = map.info;
        var cnt = map.cnt;

        console.log(cnt[1]['RESULT']);
//        $('.active').html().split('(')[1] = cnt[1]['RESULT'] + ')';

        var page = '';
        var cnt = 0;
        if(info.length != 0){
            info.forEach((list, index)=>{
            var page = '';

            var endX;
            var endY;
            var dis;
            var tDistance;

            var headers = {};
            headers["appKey"]="5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2";

            $.ajax({
            method: "GET",
            headers: headers,
            url: "https://apis.openapi.sk.com/tmap/geo/fullAddrGeo?version=1&format=json",
            async: false,
            data: {
                "coordType": "WGS84GEO",
                "fullAddr": list.ordersAddress
            },
            success: function(response) {
                var resultInfo = response.coordinateInfo;
                    if (resultInfo.coordinate[0].adminDong === "" || resultInfo.coordinate[0].adminDong === null) {
                        endX = resultInfo.coordinate[0].newLonEntr;
                        endY = resultInfo.coordinate[0].newLatEntr;
                    }else {
                        endX = resultInfo.coordinate[0].lon;
                        endY = resultInfo.coordinate[0].lat;
                    }
                    console.log("!!!!!!!!!!!!!" + list.ordersStatus);
                    if(list.ordersStatus == '세탁완료'){
                        endX = 126.97371230861432
                        endY = 37.564496383758915
                    }
                           navigator.geolocation.getCurrentPosition((position) => {

                                        console.log(position.coords.latitude);
                                        console.log(position.coords.longitude);
                                        var searchOption = "12";

                                        var trafficInfochk = "N";

                                        var headers = {};
                                        headers["appKey"]="5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2";

                                        $.ajax({
                                        type : "POST",
                                        headers : headers,
                                        url : "https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result&appKey=5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2",
                                        async : false,
                                        data : {
                                             "startX" : position.coords.longitude,
                                             "startY" : position.coords.latitude,
                                             "endX" : endX,
                                             "endY" : endY,
                                             "reqCoordType" : "WGS84GEO",
                                             "resCoordType" : "EPSG3857",
                                             "searchOption" : searchOption,
                                             "trafficInfo" : trafficInfochk
                                        },
                                        success : function(response) {

                                         var resultData = response.features;

                                         tDistance = "거리 : " + (resultData[0].properties.totalDistance / 1000)
                                                     .toFixed(1) + "km";
                                        console.log(tDistance);

                                        dis = (resultData[0].properties.totalDistance / 1000).toFixed(1);

//                                        if(5 > dis){

//                                        page += `
//                                            <a href='/ride/orders/${list.ordersId}'>
//                                              <li>
//                                                  <span class='order_num'>주문번호 :&nbsp;</span><span class='order_num' name="ordersId" >${list.ordersId}</span><br>
//                                                  <span class='delivery_address'>${list.ordersAddress} ${list.ordersAddressDetails}</span>
//                                                  <div class='space'>
//                                                      <span class='delivery_time'>${list.ordersDate}</span>
//                                                      <span class='delivery_dis'>${tDistance}</span>
//                                                  </div>
//                                              </li>
//                                            </a>`;

//}
                                        },
                                        error: function(xhr, status, error) {
                                            console.error(error);
                                        }
                                });
                                if(10 >= dis){
                                cnt++;
                                 page += `
                                    <a href='/ride/orders/${list.ordersId}'>
                                        <li style="${list.ordersStatus == '주문완료' ? 'background-color: #00ffff38;' : ''}">
                                          <span class='order_num'>주문번호 :&nbsp;</span><span class='order_num' name="ordersId" >${list.ordersId} (${list.ordersStatus == '주문완료' ? '집' : '업체'})</span><br>
                                          <span class='delivery_address'>${list.ordersAddress} ${list.ordersAddressDetails}</span>
                                          <div class='space'>
                                              <span class='delivery_time'>${list.ordersDate}</span>
                                              <span class='delivery_dis'>${tDistance}</span>
                                          </div>
                                      </li>
                                    </a>`;
                                $('.delivery_list ul').prepend(page);
                                }
                                console.log(info.length);
                                console.log("cnt : " + cnt);

                                $('.active').html('대기중(' + cnt + ')');
                                $(".ride_num").text(cnt + "건");
                            });
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
        });
        }else{
            page =`<li class='drive_none'><span>현재 배달 요청이 없습니다!</span></li>`;
        }
        $('.delivery_list ul').prepend(page);
    });
}