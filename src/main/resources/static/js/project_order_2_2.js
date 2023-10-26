window.addEventListener('load', function(){
    let warp = document.querySelector('.warp');
    const categoryAdd = document.querySelector('#categoryAdd');
    const modal = this.document.querySelector('.modal');
    const modalBox = this.document.querySelector('.modalBox');
    const repairCategory = document.querySelectorAll('.repairCategory');
    categoryAdd.addEventListener('click', function(){
        modal.classList.toggle('hidden');
    })

    repairCategory.forEach(button => {
        button.addEventListener('click', function(){
            let categoryName = button.id;
            let categoryPrice = button.value;
            let categoryTitle = button.textContent;
            createBox(categoryName, categoryPrice, categoryTitle, document.querySelector('.warp').childElementCount + 1);
            modal.classList.add('hidden');
            checkEmpty(document.querySelector('#formTag').children); // 빈 항목이 존재하면 삭제
            addImg(); // 목록최신화를 위해 중복메소드 사용
            inputFile(); // 목록최신화를 위해 중복메소드 사용
            deleteBtn(); // 목록최신화를 위해 중복메소드 사용
        })
    })

    addImg(); // + 버튼을 누르면 input[type=file] 활성화
    inputFile(); // 사진추가를 누르면 사진인지 검증하고 최대 3개까지 사진 나열
    deleteBtn(); // 삭제버튼 클릭시 해당 .box 삭제

    let submitBtn = document.querySelector('#formTag');
    submitBtn.addEventListener('submit', function(event){
        event.preventDefault();

        // 이전 장바구니가 있는지 확인
        	try{
        		fetch('./repair/check')
        			.then(response => response.json())
        			.then(map => callback(map));
        	}catch(e){
        		console.log('fetchGet',e);
        	}

        var formData = new FormData(this);
        var newFormData = new FormData();

        var jsonObject = {};

        for (let [key, value] of formData.entries()) {
                const [name, index] = key.split('-');

                if (!jsonObject[index]){
                    jsonObject[index] = {};
                }
                if (name == 'request'){
                    jsonObject[index].request = value;
                } else {
                    jsonObject[index].title = name;
                }
         }
         console.log(jsonObject);

         for (let key in jsonObject){
            let getJson = {};
            getJson[key] = jsonObject[key];
            let newJson = new Blob([JSON.stringify(getJson)], {type : 'application/json'});
            let fileData = document.querySelectorAll('input[type=file]');
            let newForm = new FormData();

            let isError = [];
            for (let j = 0; j< fileData.length; j++){
                let num = fileData[j].name.split('-')[1];
                if (fileData[j].value == null || fileData[j].value == ''){
                    let errorDiv = findByErrorDiv(fileData[j]);
                    isError.push(errorDiv);
                } else {
                    let errorDiv = findByErrorDiv(fileData[j]);
                    errorDiv.innerHTML = '';
                }
                if (Number(key) == Number(num)) {
                    for (let i =0; i < fileData[j].files.length; i++){
                        newForm.append('files', fileData[j].files[i], fileData[j].files[i].name);
                    }
                }
            }

            if (isError.length != 0){
                errorResult(isError);
                return;
            }
            newForm.append('repairData', newJson);

            let result = apiFetchFile("./repair/order", newForm);
            console.log(result);
         }

    })
})



function errorResult(errorArray){
    for (let i = 0; i < errorArray.length; i++){
        if (errorArray != null) {
            errorArray[i].innerHTML = '사진을 첨부해주세요';
        }
    }
    errorArray[0].scrollIntoView({ behavior : 'smooth'});
}

// input[type=file] 태그를 넣으면 error태그 반환
function findByErrorDiv(fileInputTag){
    let boxChildNodes = fileInputTag.parentElement.parentElement.parentElement.children;
    for (let i = 0; i < boxChildNodes.length; i++){
        if (boxChildNodes[i].classList.contains('error')){
            return boxChildNodes[i];
        }
    }
}

function apiFetchFile(url, params) {

    return new Promise( (resolve, reject) => {
        fetch(url, {
            method: "POST",
            headers: {"X-Requested-With": "XMLHttpRequest"},
            body: params
        })
        .then(res =>{
            if (res.redirected) { // 리다이렉트가 있을 경우 (에러 발생 시 화면 이동을 위해)
                window.location.href = res.url;
                res.redirect(res.url)
            }
            // 응답 데이터를 JSON 형태로 받아서 다음 then으로 넘김
            return res.json()
        })
        .then(res => {
            callback(res); // 함수 실행
            resolve();
        })
        .catch((error) => {
            console.log(error);
//            alert("에러가 발생했습니다. \r\n관리자에게 문의해주십시오.");
            reject();
        });

    })

}

function callback(map){
    return new Promise((resolve, reject) => {
            // 비어있을때 선택해제
            if (map.empty){
                opener.document.querySelector('#repair').checked = true;
                opener.document.querySelector('#repairBtn svg').style.fill = ''
                opener.document.querySelector('#repairBtn').classList.remove('select1');
            } else {
                opener.document.querySelector('#repairBtn svg').style.fill = 'var(--main-color)'
                opener.document.querySelector('#repairBtn').classList.add('select1');
                opener.document.querySelector('#repair').checked = true;
            }
            window.close();
        resolve();
    })

}

function deleteBtn() {
    return new Promise( (resolve, reject) => {

        document.querySelectorAll('.deleteBtn').forEach(button => {
            button.addEventListener('click', function(){
                button.parentElement.parentElement.remove();
            })
        })
        resolve();
    })
}

function checkEmpty(children) {
    for (let i=0;i<children.length;i++){
        if (children.item(i).classList.contains('empty')){
            children.item(i).remove();
        }
    }
}

function addImg() {
    let addImg = document.querySelectorAll('.imgbox.fixbox');
    addImg.forEach(svg => {
        svg.addEventListener('click', function(event){
            let children = svg.children;

            for (let i = 0; i < children.length; i++){
                if (children.item(i).tagName == 'INPUT'){
                    children.item(i).click();
                    break;
                }
            }

            event.stopImmediatePropagation(); // 버블링 막는코드...
        })
    })


}

function inputFile(){
    let inputFiles = document.querySelectorAll('input[type=file]');


    inputFiles.forEach(input => {
        input.addEventListener('change', function(){
            // 선택된 파일 목록 가져오기
            const files = input.files;

            // 이미지 담을 배열 생성
            const imageFiles = [];

            // 최대 3장까지만 처리
            const maxImages = 3;

            for (let i=0;i<Math.min(files.length, maxImages);i++){
                const file = files[i];

                if (String(file.type).startsWith('image/')){
                    imageFiles.push(file);
                } else {
                    alert('사진만 추가할 수 있습니다.')
                    input.value = '';
                    return;
                }
            }
            showImagePreviews(input.parentElement.parentElement, imageFiles);
        })
    })
}

function createBox(name, price, title, count) {
    let box = document.createElement('div');
    box.classList.add('box');

        let categoryInfo = document.createElement('div')
        categoryInfo.classList.add('categoryInfo');

            let categoryTitle = document.createElement('h4');
            categoryTitle.classList.add('categoryTitle');
            categoryTitle.innerText = title;
            categoryInfo.appendChild(categoryTitle);

            let categoryPriceBox = document.createElement('div');
            categoryPriceBox.classList.add('categoryPriceBox');

                let commonPrice = document.createElement('span');
                commonPrice.classList.add('commonPrice');
                commonPrice.innerText = Number(price).toLocaleString() + '원';

            if (memberShip){
                commonPrice.classList.add('line-through');
                let passPrice = document.createElement('span');
                passPrice.classList.add('passPrice');
                passPrice.innerText = (Number(price) * percent).toLocaleString() + '원';
                categoryPriceBox.appendChild(passPrice);
            }
                categoryPriceBox.appendChild(commonPrice);

            categoryInfo.appendChild(categoryPriceBox);
        box.appendChild(categoryInfo);

        let hr = document.createElement('hr');
        box.appendChild(hr);

        let imgTop = document.createElement('span');
        imgTop.classList.add('imgTop');
        imgTop.innerText = '사진 최대 3장이내';

        let message = document.createElement('span');
        message.classList.add('message')
        message.innerHTML = '1장 이상 필수'
        imgTop.appendChild(message);

        box.appendChild(imgTop);

        let imglist = document.createElement('div')
        imglist.classList.add('imglist');

            let imgbox = document.createElement('div');
            imgbox.classList.add('imgbox');
            imgbox.classList.add('fixbox');

                imgbox.innerHTML += '<svg xmlns="http://www.w3.org/2000/svg" class="addImg" viewBox="0 0 448 512"><path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32V224H48c-17.7 0-32 14.3-32 32s14.3 32 32 32H192V432c0 17.7 14.3 32 32 32s32-14.3 32-32V288H400c17.7 0 32-14.3 32-32s-14.3-32-32-32H256V80z"/></svg>'

                let spans = document.createElement('span');
                spans.innerText = '사진';
                imgbox.appendChild(spans);

                let inputFile = document.createElement('input');
                inputFile.setAttribute('type', 'file');
                inputFile.setAttribute('name', name + '-' + count);
                inputFile.setAttribute('id', name + '-' + count);
                inputFile.setAttribute('multiple', true);
                inputFile.setAttribute('hidden', true);
                inputFile.setAttribute('accept', '.jpg,.png');

                imgbox.appendChild(inputFile);

            imglist.appendChild(imgbox);

        box.appendChild(imglist);

        let error = document.createElement('div')
        error.classList.add('error')
        box.appendChild(error);

        let required = document.createElement('div');
        required.classList.add('required');

            let requiredInput = document.createElement('input');
            requiredInput.setAttribute('type', 'text');
            requiredInput.setAttribute('name', 'request' + '-' + count);
            requiredInput.setAttribute('id', 'request' + '-' + count);
            requiredInput.setAttribute('placeholder', '요청사항을 작성해주세요.')
            required.appendChild(requiredInput);

            let deleteBtn = document.createElement('button');
            deleteBtn.setAttribute('type', 'button');
            deleteBtn.classList.add('deleteBtn');
            deleteBtn.innerText = '삭제';
            required.appendChild(deleteBtn);
        box.appendChild(required);

    document.querySelector('.warp').append(box);

}

function showImagePreviews(input, imageFiles) {

    return new Promise((resolve, reject) => {
        clearImageList(input.children); // 추가하는 박스 제외하고 나머지 사진들 모두 삭제
        imageFiles.forEach((file, index) => {
            let imgbox = document.createElement('div');
            imgbox.classList.add('imgbox');

            let img = document.createElement('img');
            img.src = URL.createObjectURL(file);

            imgbox.appendChild(img);
            // 오른쪽위 X표시
            // imgbox.innerHTML += '<svg xmlns="http://www.w3.org/2000/svg" class="deleteImg" viewBox="0 0 384 512"><path d="M376.6 84.5c11.3-13.6 9.5-33.8-4.1-45.1s-33.8-9.5-45.1 4.1L192 206 56.6 43.5C45.3 29.9 25.1 28.1 11.5 39.4S-3.9 70.9 7.4 84.5L150.3 256 7.4 427.5c-11.3 13.6-9.5 33.8 4.1 45.1s33.8 9.5 45.1-4.1L192 306 327.4 468.5c11.3 13.6 31.5 15.4 45.1 4.1s15.4-31.5 4.1-45.1L233.7 256 376.6 84.5z"/></svg>'
            input.appendChild(imgbox);

            // 모든 이미지가 표시되면 Promise를 해결
            if (index === imageFiles.length - 1) {
                resolve();
            }
        });
    });
}

function clearImageList(imglist){
    return new Promise((resolve, reject) => {
        for (let i=0;i<imglist.length;i++){
            if (!imglist.item(i).classList.contains('fixbox')){
                imglist.item(i).remove();
            }
        }
        resolve();
    })
}