window.addEventListener('load', function(){

    let date = new Date();
    let nowYear = date.getFullYear() // 현재년도
    , nowMonth = date.getMonth() + 1 // 현재월
    , nowDay = date.getDate() // 오늘
    , fixYear = date.getFullYear()
    , fixMonth = date.getMonth() + 1
    , fixDay = date.getDate()
    , lastDateMonth = new Date(nowYear, nowMonth, 0).getDate() // 현재월의 마지막날
    , startDateMonth = new Date(nowYear, nowMonth-1, 1).getDay() // 현재달 시작요일


    let currDateYear = document.querySelector('#subYear');
    let currDateMonth = document.querySelector('#subMonth');
    let todaysTag = document.querySelector('.subDays');

    let inputStartDate = document.querySelector('input[name=takeDate]');
    let inputEndDate = document.querySelector('input[name=deliveryDate]');

    // 달력 넘기는 버튼
    let subPreBtn = document.querySelector('#subPreBtn');
    let subNextBtn = document.querySelector('#subNextBtn');
    currDateYear.innerHTML = nowYear;
    currDateMonth.innerHTML = String(nowMonth).padStart(2, '0');
    todaysTag.innerHTML = drawingStart(startDateMonth, lastDateMonth, nowYear, nowMonth, fixYear, fixMonth, fixDay);
    inputStartDate.value = nowYear + '-' + String(nowMonth).padStart(2, '0') + '-' +   String(nowDay).padStart(2,'0');
    let subDays = document.querySelector('.subDays');


    let rendersubCalendar = () => {
        currDateYear.innerHTML = nowYear;
        currDateMonth.innerHTML = String(nowMonth).padStart(2, '0');
        lastDateMonth = new Date(nowYear, nowMonth, 0).getDate(); // 변경된 월의 마지막 날
        startDateMonth = new Date(nowYear, nowMonth-1, 1).getDay(); // 변경된 월의 시작요일
        todaysTag.innerHTML = drawingStart(startDateMonth, lastDateMonth, nowYear, nowMonth, fixYear, fixMonth, fixDay);
    }

    subPreBtn.addEventListener('click', function(){

        if (nowYear == fixYear && nowMonth == fixMonth) return;
        checkedFalseStart();
        nowMonth = nowMonth - 1;
        if (nowMonth < 1){
            nowMonth = 12;
            nowYear = nowYear - 1;
        }
        rendersubCalendar();
    })

    subNextBtn.addEventListener('click', function(){

        checkedFalseStart();
        nowMonth = nowMonth + 1;
        if (nowMonth > 12){
            nowMonth = 1;
            nowYear = nowYear + 1;
        }
        rendersubCalendar();
    })

    subDays.addEventListener('click', function(event){

        if (event.target.className == 'subDay'){
            classClearStart();
            radioBox.forEach(radio => {
                radio.checked  = false;
            })
            event.target.classList.add('select');
            inputStartDate.value = nowYear + '-' + String(nowMonth).padStart(2, '0') + '-' +   String(event.target.textContent).padStart(2,'0');
            timeCheckStart(nowYear, nowMonth, Number(event.target.textContent), fixYear, fixMonth, fixDay);
            checkedFalseStart();
            document.querySelector('.subCalTime').classList.add('active');

        }
    })

    // 시간 Radio 설정
    let radioBox = document.querySelectorAll('input[name=takeDateTime]');
    radioBox.forEach(radio => {
        radio.addEventListener('click', function(){
            let radiosLabel = document.querySelectorAll('.subCalTime label[for^=T]');
            radiosLabel.forEach(label => {
                label.classList.remove('select');
            })
            document.querySelector(`label[for="${radio.id}"]`).classList.add('select');
            // 2023-08-23 12 형식의 정규식
            const regex = /^(\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]) (2[0-3]|[01]\d)$/;
            if (regex.test(String(inputStartDate.value))){
                inputStartDate.value = inputStartDate.value.substring(0, inputStartDate.value.indexOf(" "));
            }

            document.querySelector('#pickup').innerHTML = '수거 ' + inputStartDate.value + ' ' + radio.value + '시 부터'
            radio.checked = true;
            let calendar = document.querySelector('.start');
            let subCalendar = document.querySelector('.startChild');
            let subTime = document.querySelector('.startTime');
            let modal = document.querySelector('.startModal');

            calendar.classList.toggle('calActive');
            subCalendar.classList.toggle('active');
            modal.classList.toggle('hidden');
            subTime.classList.toggle('active');

            changeEndDate(inputStartDate.value, radio.value);

        })

    })


})


function changeEndDate(startDate, radio){
    let temp = startDate.split('-');
    let year = Number(temp[0]);
    let month = Number(temp[1]);
    let day = Number(temp[2]);
    let time = Number(radio);
    let createDate = new Date(year, month, day + 2); // 배송시간은 수거시간 + 2일로 계산

    let deliveryTime = 'NaN';
    if (time == 23) {
        document.querySelector('#E07').checked = true;
        deliveryTime = '07';
    } else if (time == 10){
        document.querySelector('#E18').checked = true;
        deliveryTime = '18';
    }
    let delivery = createDate.getFullYear() + '-' + String(createDate.getMonth()).padStart(2, '0') + '-' + String(createDate.getDate()).padStart(2, '0');
    document.querySelector('input[name=deliveryDate]').value = delivery;

    document.querySelector('#returns').innerHTML = '배송 ' + delivery + ' ' + deliveryTime + '시 까지'

}

function checkedFalseStart(){
    let radios = document.querySelectorAll('input[name=subTime]');
    let radiosLabel = document.querySelectorAll('.subCalTime label[for^=T]');
    radios.forEach(radio => {
        radio.checked = false;
    })
    radiosLabel.forEach(label => {
        label.classList.remove('select');
    })

}

function timeCheckStart(year, month, day, fixYear, fixMonth, fixDay){
    if (year == fixYear && month == fixMonth && day == fixDay){
        let date = new Date();
        if (date.getHours() >= 10){
            let rd = document.querySelector('#T10'); // 라디오 버튼
            let label = document.querySelector('label[for=T10]'); // 라벨
            setDisabled(rd, label);
        }
        if (date.getHours() >= 22) {
            let rd = document.querySelector('#T23'); // 라디오 버튼
            let label = document.querySelector('label[for=T23]'); // 라벨
            setDisabled(rd, label);
        }
    } else {
        setAbled(document.querySelector('#T10'), document.querySelector('label[for=T10]'));
        setAbled(document.querySelector('#T23'), document.querySelector('label[for=T23]'));

    }
}

function setDisabled(rd, label){
    rd.setAttribute('disabled', true);
    label.setAttribute('disabled', true);
    rd.classList.add('disabled');
    label.classList.add('disabled');
}

function setAbled(rd, label) {
    rd.removeAttribute('disabled');
    label.removeAttribute('disabled');
    rd.classList.remove('disabled');
    label.classList.remove('disabled');
}


function classClearStart(){
    let subDay = document.querySelectorAll('.subDay');
    subDay.forEach(li => {
        li.classList.remove('select');
    })
}

function drawingStart(startDateMonth, lastDateMonth, nowYear, nowMonth, fixYear, fixMonth, fixDay){
    let liTag = '';
    for (let i=0;i<startDateMonth;i++){
        liTag += `<li class='empty' disabled></li>`
    }
    if (nowYear == fixYear && nowMonth == fixMonth){
        for (let i=1; i<fixDay;i++){
            liTag += `<li class='subDay calDisabled' disabled>${i}</li>`
        }
        for (let i=fixDay; i<=lastDateMonth;i++){
            if (i == fixDay){
                liTag += `<li class='subDay'>${i}</li>`
            } else {
                liTag += `<li class='subDay'>${i}</li>`
            }
        }
    } else{
        for (let i=1;i<=lastDateMonth;i++){
            liTag += `<li class='subDay'>${i}</li>`
        }

    }

    return liTag;
}