window.onload = function() {
            var orderStatus = document.querySelector('#orderStatus').value;
            // status에 따른 지도 띄우기 조건
            if (orderStatus == 2 || orderStatus == 3 || orderStatus == 4 || orderStatus == 10 || orderStatus == 11) {
                initTmap();
            }



            $("#fileInput").change(function() {
                $("#selectedImage").css("display", "");
                var file = this.files[0]; // 선택한 파일 가져오기
                if (file) {
                    var reader = new FileReader();

                    reader.onload = function(e) {
                        // 선택한 파일의 내용을 이미지로 표시
                        $("#selectedImage").attr("src", e.target.result).css({ width: "100%", height: "100%" });
                        $('#complete_btn').css("display", "");
                        $('.imgbox').css("display", "none");

                    };

                    reader.readAsDataURL(file);
                } else {
                    // 파일이 선택되지 않았을 때 이미지 제거
                    $("#selectedImage").attr("src", "");
                }
            });

                     var map;
                     var markerInfo;
                     var marker_s, marker_e, marker_p;
                     var drawInfoArr = [];
                     var drawInfoArr2 = [];

                     var chktraffic = [];
                     var resultdrawArr = [];
                     var resultMarkerArr = [];

                     var fullAddressElements;
                     var endX;
                     var endY;

                     var startX;
                     var startY;


                     function initTmap() {
                        map = new Tmapv2.Map("map_div", {
                           center : new Tmapv2.LatLng(37.54882398563218, 126.99276886231617),
                           width : "100%",
                           height : "400px",
                           zoom : 10,
                           zoomControl : true,
                           scrollwheel : true
                        });

//                        navigator.geolocation.getCurrentPosition((position) => {
//                        marker_s = new Tmapv2.Marker(
//                                {
//                                 position : new Tmapv2.LatLng(position.coords.latitude, position.coords.longitude),
//                                 icon : "https://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png",
//                                 iconSize : new Tmapv2.Size(24, 38),
//                                 map : map
//                              });
//                                });

//                        marker_e = new Tmapv2.Marker(
//                              {
//                                 position : new Tmapv2.LatLng(37.403049076341794,
//                                       127.10331814639885),
//                                 icon : "https://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png",
//                                 iconSize : new Tmapv2.Size(24, 38),
//                                 map : map
//                              });

                        $(document).ready(function(){
                                       navigator.geolocation.getCurrentPosition((position) => {

                                          console.log(position.coords.latitude);
                                          console.log(position.coords.longitude);

//                                       resettingMap();


            <!-- ----------------------------------------------------------------------------------------------- -->
            var extractedAddress = '';
            fullAddressElements = document.querySelector('#addressValue').value;
            console.log(fullAddressElements);

//                    var fullAddress = fullAddressElements.value;
//                    var indexOfDong = fullAddress.indexOf("동");
//
//                    if (indexOfDong !== -1) {
//                        extractedAddress = fullAddress.substring(0, indexOfDong-4); // "동"을 포함하기 전까지 추출
//                    }

            <!-- ----------------------------------------------------------------------------------------------- -->


           var searchOption = "12";

           var trafficInfochk = "N";

           var headers = {};
              headers["appKey"]="5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2";

            <!-- ----------------------------------------------------------------------------------------------- -->

                    $.ajax({
                        method: "GET",
                        headers: headers,
                        url: "https://apis.openapi.sk.com/tmap/geo/fullAddrGeo?version=1&format=json",
                        async: false,
                        data: {
                            "coordType": "WGS84GEO",
                            "fullAddr": fullAddressElements
                        },
                        success: function(response) {
                            var resultInfo = response.coordinateInfo;
                            console.log(resultInfo);
                            console.log("위도: " + resultInfo.coordinate[0].newLatEntr);
                            console.log("경도: " + resultInfo.coordinate[0].newLonEntr);
                             console.log($('#orderStatus').val());
                            console.log($('#orderStatus').val() == 10);
                            if($('#orderStatus').val() == 10){
                                 startX = 126.97371230861432;
                                 startY = 37.564496383758915;
                            }else{
                                startX = position.coords.longitude;
                                startY = position.coords.latitude;
                            }

                            console.log("===================" + startX);
                            console.log("===================" + startY);


                            console.log(resultInfo.coordinate[0].adminDong);

                                if (resultInfo.coordinate[0].adminDong === "" || resultInfo.coordinate[0].adminDong === null) {
//                                    새주소
                                    endX = resultInfo.coordinate[0].newLonEntr;
                                    endY = resultInfo.coordinate[0].newLatEntr;
                                }else {
//                                    구주수
                                    endX = resultInfo.coordinate[0].lon;
                                    endY = resultInfo.coordinate[0].lat;
                                }
                        },
                        error: function(xhr, status, error) {
                            console.error(error);
                        }
                    });
                    console.log($('#orderStatus').val());
                    if($('#orderStatus').val() == 4 || $('#orderStatus').val() == 11){
                        endX = 126.97371230861432
                        endY = 37.564496383758915
                    }
                    console.log(endX);
                    console.log(endY);


//                    $('#start-navigation').click(function(e){
//                        e.preventDefault();
//
//                        console.log(endX);
//                        console.log(endY);
//                        console.log(fullAddressElements);
//
//                        Kakao.Navi.start({
//                            name: fullAddressElements,
////                 //              x: 127.11205203011632,
////                 //              y: 37.39279717586919,
//                            x : 126.919905,
//                            y : 37.576905,
//                            coordType: 'wgs84',
//                        });
//                    });
            <!-- ----------------------------------------------------------------------------------------------- -->




                                       $.ajax({
                                          type : "POST",
                                          headers : headers,
                                          url : "https://apis.openapi.sk.com/tmap/routes?version=1&format=json&callback=result&appKey=5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2",
                                          async : false,
                                          data : {
                                             "startX" : startX,
                                             "startY" : startY,
                                             "endX" : endX,
                                             "endY" : endY,
//                                           "endX" : 126.97548410130028,
//                                           "endY" : 37.5632286126255,
//                                              "endX" : position.coords.longitude,
//                                              "endY" : position.coords.latitude,
                                             "reqCoordType" : "WGS84GEO",
                                             "resCoordType" : "EPSG3857",
                                             "searchOption" : searchOption,
                                             "trafficInfo" : trafficInfochk
                                          },
                                          success : function(response) {

                                             var resultData = response.features;

                                             var tDistance = "총 거리 : "
                                                   + (resultData[0].properties.totalDistance / 1000)
                                                         .toFixed(1) + "km";
                                                        document.getElementById('distance').innerHTML = tDistance;

                                             var tTime = " 총 시간 : "
                                                   + (resultData[0].properties.totalTime / 60)
                                                         .toFixed(0) + "분";
                                                        document.getElementById('time').innerHTML = tTime;

                                             var tFare = " 총 요금 : "
                                                   + resultData[0].properties.totalFare
                                                   + "원,";
                                             var taxiFare = " 예상 택시 요금 : "
                                                   + resultData[0].properties.taxiFare
                                                   + "원";

                                             $("#result").text(
                                                   tDistance + tTime + tFare
                                                         + taxiFare);

                                             if (trafficInfochk == "Y") {
                                                for ( var i in resultData) {
                                                   var geometry = resultData[i].geometry;
                                                   var properties = resultData[i].properties;

                                                   if (geometry.type == "LineString") {
                                                      chktraffic
                                                            .push(geometry.traffic);
                                                      var sectionInfos = [];
                                                      var trafficArr = geometry.traffic;

                                                      for ( var j in geometry.coordinates) {
                                                         var latlng = new Tmapv2.Point(
                                                               geometry.coordinates[j][0],
                                                               geometry.coordinates[j][1]);
                                                         var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                               latlng);

                                                         sectionInfos
                                                               .push(convertPoint);
                                                      }

                                                      drawLine(sectionInfos,
                                                            trafficArr);
                                                   } else {

                                                      var markerImg = "";
                                                      var pType = "";

                                                      if (properties.pointType == "S") {
                                                         markerImg = "https://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png";
                                                         pType = "S";
                                                      } else if (properties.pointType == "E") {
                                                         markerImg = "https://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png";
                                                         pType = "E";
                                                      } else {
                                                         markerImg = "http://topopen.tmap.co.kr/imgs/point.png";
                                                         pType = "P"
                                                      }

                                                      var latlon = new Tmapv2.Point(
                                                            geometry.coordinates[0],
                                                            geometry.coordinates[1]);
                                                      var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                            latlon);

                                                      var routeInfoObj = {
                                                         markerImage : markerImg,
                                                         lng : convertPoint._lng,
                                                         lat : convertPoint._lat,
                                                         pointType : pType
                                                      };
                                                      addMarkers(routeInfoObj);
                                                   }
                                                }

                                             } else {

                                                for ( var i in resultData) {
                                                   var geometry = resultData[i].geometry;
                                                   var properties = resultData[i].properties;

                                                   if (geometry.type == "LineString") {
                                                      for ( var j in geometry.coordinates) {

                                                         var latlng = new Tmapv2.Point(
                                                               geometry.coordinates[j][0],
                                                               geometry.coordinates[j][1]);

                                                         var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                               latlng);

                                                         var convertChange = new Tmapv2.LatLng(
                                                               convertPoint._lat,
                                                               convertPoint._lng);

                                                         drawInfoArr
                                                               .push(convertChange);
                                                      }
                                                      drawLine(drawInfoArr, "0");
                                                   } else {

                                                      var markerImg = "";
                                                      var pType = "";

                                                      if (properties.pointType == "S") {
                                                         markerImg = "https://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_s.png";
                                                         pType = "S";
                                                      } else if (properties.pointType == "E") {
                                                         markerImg = "https://tmapapi.sktelecom.com/upload/tmap/marker/pin_r_m_e.png";
                                                         pType = "E";
                                                      } else {
                                                         markerImg = "http://topopen.tmap.co.kr/imgs/point.png";
                                                         pType = "P"
                                                      }


                                                      var latlon = new Tmapv2.Point(
                                                            geometry.coordinates[0],
                                                            geometry.coordinates[1]);

                                                      var convertPoint = new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                                            latlon);

                                                      var routeInfoObj = {
                                                         markerImage : markerImg,
                                                         lng : convertPoint._lng,
                                                         lat : convertPoint._lat,
                                                         pointType : pType
                                                      };


                                                      addMarkers(routeInfoObj);
                                                   }
                                                }
                                             }
                                          },
                                          error : function(request, status, error) {
                                             console.log("code:"
                                                   + request.status + "\n"
                                                   + "message:"
                                                   + request.responseText
                                                   + "\n" + "error:" + error);
                                          }
                                       });
                                    });
                              });
                     }


                     function addComma(num) {
                        var regexp = /\B(?=(\d{3})+(?!\d))/g;
                        return num.toString().replace(regexp, ',');
                     }


                     function addMarkers(infoObj) {
                        var size = new Tmapv2.Size(24, 38);

                        if (infoObj.pointType == "P") {
                           size = new Tmapv2.Size(8, 8);
                        }

                        marker_p = new Tmapv2.Marker({
                           position : new Tmapv2.LatLng(infoObj.lat, infoObj.lng),
                           icon : infoObj.markerImage,
                           iconSize : size,
                           map : map
                        });

                        resultMarkerArr.push(marker_p);
                     }


                     function drawLine(arrPoint, traffic) {
                        var polyline_;

                        if (chktraffic.length != 0) {
                           var lineColor = "";
                           if (traffic != "0") {
                              if (traffic.length == 0) {

                                 lineColor = "#06050D";

                                 polyline_ = new Tmapv2.Polyline({
                                    path : arrPoint,
                                    strokeColor : lineColor,
                                    strokeWeight : 6,
                                    map : map
                                 });
                                 resultdrawArr.push(polyline_);

                              } else {

                                 if (traffic[0][0] != 0) {
                                    var trafficObject = "";
                                    var tInfo = [];

                                    for (var z = 0; z < traffic.length; z++) {
                                       trafficObject = {
                                          "startIndex" : traffic[z][0],
                                          "endIndex" : traffic[z][1],
                                          "trafficIndex" : traffic[z][2],
                                       };
                                       tInfo.push(trafficObject)
                                    }

                                    var noInfomationPoint = [];

                                    for (var p = 0; p < tInfo[0].startIndex; p++) {
                                       noInfomationPoint.push(arrPoint[p]);
                                    }

                                    polyline_ = new Tmapv2.Polyline({
                                       path : noInfomationPoint,
                                       strokeColor : "#06050D",
                                       strokeWeight : 6,
                                       map : map
                                    });

                                    resultdrawArr.push(polyline_);

                                    for (var x = 0; x < tInfo.length; x++) {
                                       var sectionPoint = [];

                                       for (var y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                                          sectionPoint.push(arrPoint[y]);
                                       }

                                       if (tInfo[x].trafficIndex == 0) {
                                          lineColor = "#06050D";
                                       } else if (tInfo[x].trafficIndex == 1) {
                                          lineColor = "#61AB25";
                                       } else if (tInfo[x].trafficIndex == 2) {
                                          lineColor = "#FFFF00";
                                       } else if (tInfo[x].trafficIndex == 3) {
                                          lineColor = "#E87506";
                                       } else if (tInfo[x].trafficIndex == 4) {
                                          lineColor = "#D61125";
                                       }


                                       polyline_ = new Tmapv2.Polyline({
                                          path : sectionPoint,
                                          strokeColor : lineColor,
                                          strokeWeight : 6,
                                          map : map
                                       });

                                       resultdrawArr.push(polyline_);
                                    }
                                 } else {

                                    var trafficObject = "";
                                    var tInfo = [];

                                    for (var z = 0; z < traffic.length; z++) {
                                       trafficObject = {
                                          "startIndex" : traffic[z][0],
                                          "endIndex" : traffic[z][1],
                                          "trafficIndex" : traffic[z][2],
                                       };
                                       tInfo.push(trafficObject)
                                    }

                                    for (var x = 0; x < tInfo.length; x++) {
                                       var sectionPoint = [];

                                       for (var y = tInfo[x].startIndex; y <= tInfo[x].endIndex; y++) {
                                          sectionPoint.push(arrPoint[y]);
                                       }

                                       if (tInfo[x].trafficIndex == 0) {
                                          lineColor = "#06050D";
                                       } else if (tInfo[x].trafficIndex == 1) {
                                          lineColor = "#61AB25";
                                       } else if (tInfo[x].trafficIndex == 2) {
                                          lineColor = "#FFFF00";
                                       } else if (tInfo[x].trafficIndex == 3) {
                                          lineColor = "#E87506";
                                       } else if (tInfo[x].trafficIndex == 4) {
                                          lineColor = "#D61125";
                                       }


                                       polyline_ = new Tmapv2.Polyline({
                                          path : sectionPoint,
                                          strokeColor : lineColor,
                                          strokeWeight : 6,
                                          map : map
                                       });

                                       resultdrawArr.push(polyline_);
                                    }
                                 }
                              }
                           } else {

                           }
                        } else {
                           polyline_ = new Tmapv2.Polyline({
                              path : arrPoint,
                              strokeColor : "#DD0000",
                              strokeWeight : 6,
                              map : map
                           });
                           resultdrawArr.push(polyline_);
                        }

                     }


                     function resettingMap() {

                        marker_s.setMap(null);
                        marker_e.setMap(null);

                        if (resultMarkerArr.length > 0) {
                           for (var i = 0; i < resultMarkerArr.length; i++) {
                              resultMarkerArr[i].setMap(null);
                           }
                        }

                        if (resultdrawArr.length > 0) {
                           for (var i = 0; i < resultdrawArr.length; i++) {
                              resultdrawArr[i].setMap(null);
                           }
                        }

                        chktraffic = [];
                        drawInfoArr = [];
                        resultMarkerArr = [];
                        resultdrawArr = [];
                     }
                };

                      function navi() {
                          var xx, yy;
                          var aa = document.querySelector('#addressValue').value;
                          console.log(aa);

                          var searchOption = "12";

                          var trafficInfochk = "N";

                          var headers = {};
                          headers["appKey"]="5xEXKwOOBa1ZkF3EBC9nc6DcYaOAwO858xW0ZDd2";


                          $.ajax({
                              method: "GET",
                              headers: headers,
                              url: "https://apis.openapi.sk.com/tmap/geo/fullAddrGeo?version=1&format=json",
                              async: false,
                              data: {
                                  "coordType": "WGS84GEO",
                                  "fullAddr": aa
                              },
                              success: function(response) {
                                  var resultInfo = response.coordinateInfo;

                                      if (resultInfo.coordinate[0].adminDong === "" || resultInfo.coordinate[0].adminDong === null) {
                                          xx = resultInfo.coordinate[0].newLonEntr;
                                          yy = resultInfo.coordinate[0].newLatEntr;
                                      }else {
                                          xx = resultInfo.coordinate[0].lon;
                                          yy = resultInfo.coordinate[0].lat;
                                      }

                                if($('#orderStatus').val() == 4 || $('#orderStatus').val() == 11){
                                    goEnterpriseNavi();
                                }else{
                                    startNavigation(xx, yy, aa);
                                }
                              },
                              error: function(xhr, status, error) {
                                  console.error(error);
                              }
                          });
                          console.log(xx);
                          console.log(yy);
                    }

                    function startNavigation(xx, yy, address){
                    console.log(xx);
                    console.log(yy);
                    console.log(address);
                    if(xx != null && yy != null && address != null){
                        Kakao.Navi.start({
                              name: address,
                              x : Number(xx),
                              y : Number(yy),
                              vehicleType : 7,
                              rpOption : 2,
                            coordType: 'wgs84',
                          });
                        }
                    }

                    function goEnterpriseNavi(){
                    Kakao.Navi.start({
                          name: '서울특별시 중구 세종대로 110',
                          x : 126.97371230861432,
                          y : 37.564496383758915,
                          vehicleType : 7,
                          rpOption : 2,
                        coordType: 'wgs84',
                      });
                    }

//                    function goHomeNavi(xx, yy, address){
//                    console.log(xx);
//                    console.log(yy);
//                    console.log(address);
//                    if(xx != null && yy != null && address != null){
//                        Kakao.Navi.start({
//                              name: address,
//                              x : Number(xx),
//                              y : Number(yy),
//                              vehicleType : 7,
//                              rpOption : 2,
//                              sX : 126.97371230861432,
//                              sY : 37.564496383758915,
//                            coordType: 'wgs84',
//                          });
//                        }
//                    }

