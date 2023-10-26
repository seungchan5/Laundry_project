window.addEventListener('load', function(){

    let leftBtn = document.querySelector('.leftBtn');
    let rightBtn = document.querySelector('.rightBtn');

    let now = document.querySelector('#now');
    let complete = document.querySelector('#complete');

    leftBtn.addEventListener('click', function(){
        rightBtn.classList.remove('selected')
        leftBtn.classList.add('selected')

        complete.classList.add('hidden');
        now.classList.remove('hidden');
    })

    rightBtn.addEventListener('click', function(){
        leftBtn.classList.remove('selected')
        rightBtn.classList.add('selected')

        now.classList.add('hidden')
        complete.classList.remove('hidden');
    })

})