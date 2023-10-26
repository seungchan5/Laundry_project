window.addEventListener('load', function(){
    const category = document.querySelector('#category1');
    const category2 = document.querySelector('#category2');
    const warp = document.querySelector('.warp');

    changeCategory(category.options[category.selectedIndex].textContent);

    category.addEventListener('change', function(){
        changeCategory(category.options[category.selectedIndex].textContent);
    })

    let addBtn = document.querySelector('#categoryAdd');

    addBtn.addEventListener('click', function(){
        let check = document.querySelectorAll('.classification1');
        let error = document.querySelector('.error');
        for (let i = 0; i < check.length; i++){
            if (check[i].textContent == category2.options[category2.selectedIndex].textContent){
                error.innerHTML = '이미 해당 카테고리가 존재합니다.';
                return;
            }
        }
        error.innerHTML = '';
        createList();
    })

    let allCheck = document.querySelector('#allDelete');
    allCheck.addEventListener('change', function(){
        let dryCheckBox = document.querySelectorAll('input[name=dry]');
        if (this.checked){
            dryCheckBox.forEach(check => {
                check.checked = true;
            })
        } else {
            dryCheckBox.forEach(check => {
                check.checked = false;
            })
        }
    })

    let deleteBtn = document.querySelector('.allDeleteBtn');
    deleteBtn.addEventListener('click', function(){
        let dryCheckBox = document.querySelectorAll('input[name=dry]:checked');
        dryCheckBox.forEach(check => {
                check.parentElement.parentElement.parentElement.remove();
        })
        document.querySelector('#allDelete').checked = false;
    })

    let submitBtn = document.querySelector('#dryForm');
    submitBtn.addEventListener('submit', function(event){
        event.preventDefault();

        var formData = new FormData(this);

        var jsonObject = {};

        formData.forEach(function(value, key){
            if (key != 'dry'){
                jsonObject[key] = value;
            }
        })

        try{
            fetch("./dry/order" , { method : 'post'
                        , headers : {'Content-Type' : 'application/json'}
                        , body : JSON.stringify(jsonObject)
                    })
                .then(response => response.json())
                .then(map => callback(map));
        }catch(e){
            console.log('fetchPost', e);
        }

    })

})
function callback(map){
    console.log(map.result);
    if (map.result){

        // 비어있을때 선택해제
        if (map.empty){
            opener.document.querySelector('#drycleaning').checked = true;
            opener.document.querySelector('#dryBtn svg').style.fill = ''
            opener.document.querySelector('#dryBtn').classList.remove('select1');
        } else {
            opener.document.querySelector('#dryBtn svg').style.fill = 'var(--main-color)'
            opener.document.querySelector('#dryBtn').classList.add('select1');
            opener.document.querySelector('#drycleaning').checked = true;
        }
        window.close();
    } else {
        alert('잘못된 접근입니다. 다시 시도해주세요.');
        location.reload();
    }
}

function values(div){
    let inp = div.parentElement.children.item(1);
    let passPrice = div.parentElement.parentElement.children.item(1).children.item(1);
    let commonPrice = div.parentElement.parentElement.children.item(1).children.item(2);
    if (div.classList.contains('minus')){
        if (inp.value <= 0) return;
        inp.value = Number(inp.value) - 1;
    } else {
        inp.value = Number(inp.value) + 1;
    }
}

function changeCategory(option){
    console.log(category[option]);
    let result = category[option];
    let temp =  ''
    for (let key in result){
        temp += '<option value=' + result[key] + '>' + key + '</option>'
    }

    category2.innerHTML = temp;
}

function createList(){
    let warp = document.querySelector('.warp');
    let element = document.createElement('div');
    element.classList.add('box');

    let label = document.createElement('label');
    label.setAttribute('for', category2.options[category2.selectedIndex].textContent);

    let sec1 = document.createElement('div');
    sec1.classList.add('sec1');

    let checkbox = document.createElement('input')
    checkbox.setAttribute('type', 'checkbox');
    checkbox.setAttribute('name', 'dry');
    checkbox.setAttribute('id', category2.options[category2.selectedIndex].textContent);
    checkbox.classList.add('custom-checkbox');
    checkbox.classList.add('custom-label');
    checkbox.classList.add('myCheckbox');

    let info = document.createElement('div')
    info.classList.add('info');

    let classification1 = document.createElement('div');
    classification1.classList.add('classification1');
    classification1.innerText = category2.options[category2.selectedIndex].textContent;
    info.appendChild(classification1);

    let classification2 = document.createElement('div');
    classification2.classList.add('classification2');
    classification2.innerText = category1.options[category1.selectedIndex].textContent;
    info.appendChild(classification2);

    sec1.appendChild(checkbox);
    sec1.appendChild(info);

    label.appendChild(sec1);

    let sec2 = document.createElement('div')
    sec2.classList.add('sec2')

    let countCheck = document.createElement('div')
    countCheck.classList.add('countCheck');

    let minus = document.createElement('div')
    minus.classList.add('cntBox');
    minus.classList.add('minus');
    minus.setAttribute('onclick', 'values(this)')
    minus.innerText = '-';
    countCheck.appendChild(minus);

    let amount = document.createElement('input')
    amount.setAttribute('type', 'text');
    amount.setAttribute('name', category2.options[category2.selectedIndex].textContent)
    amount.setAttribute('value', 1)
    amount.setAttribute('readonly', true);
    amount.classList.add('cntValue');
    countCheck.appendChild(amount);

    let plus = document.createElement('div')
    plus.classList.add('cntBox');
    plus.classList.add('plus');
    plus.setAttribute('onclick', 'values(this)')
    plus.innerText = '+';
    countCheck.appendChild(plus);

    let checkPrice = document.createElement('div')
    checkPrice.classList.add('checkPrice');
    let commonPrice = document.createElement('span');
    commonPrice.classList.add('commonPrice');
    if (memberShip) {
        let passPrice = document.createElement('span');
        passPrice.classList.add('passPrice');
        passPrice.innerText = (Number(category2.options[category2.selectedIndex].value) * Number(percent)).toLocaleString() + '원';
        commonPrice.classList.add('line-through');
        checkPrice.appendChild(passPrice);
    }


    commonPrice.innerText = Number(category2.options[category2.selectedIndex].value).toLocaleString() + '원';
    checkPrice.appendChild(commonPrice);

    sec2.appendChild(countCheck);
    sec2.appendChild(checkPrice);

    element.appendChild(label);
    element.appendChild(sec2);


    warp.appendChild(element);
}

// JSON포맷 GET방식 전달 함수
function fetchGet(url){
    try{
        fetch(url)
            .then(response => response.json())
    }catch(e){
        console.log('fetchGet',e);
    }
}



//JSON포맷 POST방식 전달 함수
function fetchPost(url, obj, callback){
    try{
        fetch(url , { method : 'post'
                    , headers : {'Content-Type' : 'application/json'}
                    , body : JSON.stringify(obj)
                })
            .then(response => response.json())
            .then(map => callback(map));
    }catch(e){
        console.log('fetchPost', e);
    }
}