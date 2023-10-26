window.addEventListener('load', function(){

    let startCalendar = document.querySelector('.start');
    let startSubCalendar = document.querySelector('.startChild');
    let startModal = document.querySelector('.startModal');

    startModal.addEventListener('click', function(){

        startCalendar.classList.toggle('calActive');
        startSubCalendar.classList.toggle('active');
        startModal.classList.toggle('hidden');
    })


})