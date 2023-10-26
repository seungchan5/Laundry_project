window.addEventListener('load', function(){

    let unregist = document.querySelector('#unregist');

    unregist.addEventListener('click', function(e){
        e.preventDefault();

        if(window.confirm("탈퇴하시겠습니까?")){
            location.href = "./unregister";
        } else {
            location.href = "./update";
        }
    })
})