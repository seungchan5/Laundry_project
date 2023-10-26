window.addEventListener('load', function(){

    let submitBtn = document.querySelector('input[type=submit]');
    submitBtn.addEventListener('click', function(event){
        event.preventDefault();
        let radio = document.querySelector('input[name=location]:checked');
        let radioLabel = document.querySelector(`label[for=${radio.id}]`);
        if (radio.id == 'exception'){
            opener.document.querySelector('#location').innerHTML = document.querySelector('.details').value;
            opener.document.querySelector('input[name=location]').value = document.querySelector('.details').value;
        } else {
            opener.document.querySelector('#location').innerHTML = document.querySelector(`label[for=${radio.id}]`).textContent;
            opener.document.querySelector('input[name=location]').value = document.querySelector(`label[for=${radio.id}]`).textContent;
        }

        window.close();
    })


})

// 수거/배송위치 radio연동
function toogleRadio(element) {
    const radio = element.previousElementSibling;
    if (!radio.checked){
        radio.checked = !radio.checked;
    }
}