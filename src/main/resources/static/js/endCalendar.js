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


    let currDateYear = document.querySelector('#subYearEnd');
    let currDateMonth = document.querySelector('#subMonthEnd');
    let todaysTag = document.querySelector('.subDaysEnd');

    let inputStartDate = document.querySelector('input[name=deliveryDate]');

    // 달력 넘기는 버튼
    let subPreBtn = document.querySelector('.subPreBtnEnd');
    let subNextBtn = document.querySelector('.subNextBtnEnd');
    currDateYear.innerHTML = nowYear;
    currDateMonth.innerHTML = String(nowMonth).padStart(2, '0');
    todaysTag.innerHTML = drawing(startDateMonth, lastDateMonth, nowYear, nowMonth, fixYear, fixMonth, fixDay);
    inputStartDate.value = nowYear + '-' + String(nowMonth).padStart(2, '0') + '-' +   String(nowDay).padStart(2,'0');
    let subDaysEnd = document.querySelector('.subDaysEnd');

    let rendersubCalendar = () => {
        currDateYear.innerHTML = nowYear;
        currDateMonth.innerHTML = String(nowMonth).padStart(2, '0');
        lastDateMonth = new Date(nowYear, nowMonth, 0).getDate(); // 변경된 월의 마지막 날
        startDateMonth = new Date(nowYear, nowMonth-1, 1).getDay(); // 변경된 월의 시작요일
        todaysTag.innerHTML = drawing(startDateMonth, lastDateMonth, nowYear, nowMonth, fixYear, fixMonth, fixDay);
    }

    subPreBtn.addEventListener('click', function(){

        if (nowYear == fixYear && nowMonth == fixMonth) return;
        checkedFalse();
        nowMonth = nowMonth - 1;
        if (nowMonth < 1){
            nowMonth = 12;
            nowYear = nowYear - 1;
        }

        rendersubCalendar();
    })

    subNextBtn.addEventListener('click', function(){

        checkedFalse();
        nowMonth = nowMonth + 1;
        if (nowMonth > 12){
            nowMonth = 1;
            nowYear = nowYear + 1;
        }
        rendersubCalendar();
    })

    subDaysEnd.addEventListener('click', function(event){
        if (event.target.className == 'subDay end'){
            classClear();
            event.target.classList.add('select');
            inputStartDate.value = nowYear + '-' + String(nowMonth).padStart(2, '0') + '-' +   String(event.target.textContent).padStart(2,'0');
            timeCheck(nowYear, nowMonth, Number(event.target.textContent), fixYear, fixMonth, fixDay);
            checkedFalse();
            document.querySelector('.endTime').classList.add('active');

        }
    })

    // 시간 Radio 설정
    let radioBox = document.querySelectorAll('input[name=subTimeEnd]');
    radioBox.forEach(radio => {
        radio.addEventListener('click', function(){
            let radiosLabel = document.querySelectorAll('.endTime label[for^=T]');
            radiosLabel.forEach(label => {
                label.classList.remove('select');
            })
            document.querySelector(`label[for="${radio.id}"]`).classList.add('select');
            // 2023-08-23 12 형식의 정규식
            const regex = /^(\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\d|3[0-1]) (2[0-3]|[01]\d)$/;
            if (regex.test(String(inputStartDate.value))){
                inputStartDate.value = inputStartDate.value.substring(0, inputStartDate.value.indexOf(" "));
            }
            inputStartDate.value += ' ' + radio.value;
            document.querySelector('#returns').innerHTML = '반납 ' + inputStartDate.value + '시 까지'
            let endCalendar = document.querySelector('nav.end');
            let endSubCalendar = document.querySelector('.endChild');
            let endModal = document.querySelector('.endModal');
            let endSubTime = document.querySelector('.endTime');
            endCalendar.classList.toggle('calActive');
            endSubCalendar.classList.toggle('active');
            endModal.classList.toggle('hidden');
            endSubTime.classList.toggle('active');
        })

    })



})

function checkedFalse(){
    let radios = document.querySelectorAll('input[name=subTimeEnd]');
    let radiosLabel = document.querySelectorAll('.endTime label[for^=E]');
    radios.forEach(radio => {
        radio.checked = false;
    })
    radiosLabel.forEach(label => {
        label.classList.remove('select');
    })

}

function timeCheck(year, month, day, fixYear, fixMonth, fixDay){
    if (year == fixYear && month == fixMonth && day == fixDay){
        let date = new Date();
        if (date.getHours() >= 6){
            let rd = document.querySelector('#E07'); // 라디오 버튼
            let label = document.querySelector('label[for=E07]'); // 라벨
            setDisabled(rd, label);
        }
        if (date.getHours() >= 17) {
            let rd = document.querySelector('#E18'); // 라디오 버튼
            let label = document.querySelector('label[for=E18]'); // 라벨
            setDisabled(rd, label);
        }
    } else {
        setAbled(document.querySelector('#E07'), document.querySelector('label[for=E07]'));
        setAbled(document.querySelector('#E18'), document.querySelector('label[for=E18]'));

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


function classClear(){
    let subDay = document.querySelectorAll('.subDaysEnd .end');
    subDay.forEach(li => {
        li.classList.remove('select');
    })
}

function drawing(startDateMonth, lastDateMonth, nowYear, nowMonth, fixYear, fixMonth, fixDay){
    let liTag = '';
    for (let i=0;i<startDateMonth;i++){
        liTag += `<li class='empty' disabled></li>`
    }
    if (nowYear == fixYear && nowMonth == fixMonth){
        for (let i=1; i<fixDay;i++){
            liTag += `<li class='subDay end calDisabled' disabled>${i}</li>`
        }
        for (let i=fixDay; i<=lastDateMonth;i++){
            if (i == fixDay){
                liTag += `<li class='subDay end'>${i}</li>`
            } else {
                liTag += `<li class='subDay end'>${i}</li>`
            }
        }
    } else{
        for (let i=1;i<=lastDateMonth;i++){
            liTag += `<li class='subDay end'>${i}</li>`
        }

    }

    return liTag;
}