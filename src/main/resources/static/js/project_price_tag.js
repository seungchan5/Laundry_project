window.addEventListener('load',function(){

    let drycleaningBtn = document.querySelector('.drycleaning');
    let repairBtn = document.querySelector('.repair');
    let drycleaning = document.querySelector('#drycleaning');
    let repair = document.querySelector('#repair');

    drycleaningBtn.addEventListener('click', function() {
        drycleaning.classList.remove('hidden');
        drycleaningBtn.classList.add('select');
        repair.classList.add('hidden');
        repairBtn.classList.remove('select');

    })

    repairBtn.addEventListener('click', function() {
        repair.classList.remove('hidden');
        repairBtn.classList.add('select');
        drycleaning.classList.add('hidden');
        drycleaningBtn.classList.remove('select');
    })

    let clothes = document.querySelector('.clothes');
    let bedding = document.querySelector('.bedding');
    let shoes = document.querySelector('.shoes');
    let categoryName = document.querySelectorAll('.categoryFocus');
    clothes.addEventListener('click', function(){
        for (let i=0;i<categoryName.length;i++){
            if (categoryName[i].textContent == clothes.textContent){
                categoryName[i].scrollIntoView({ behavior : 'smooth'});
            }
        }
    })

    bedding.addEventListener('click', function(){
        for (let i=0;i<categoryName.length;i++){
            if (categoryName[i].textContent == bedding.textContent){
                categoryName[i].scrollIntoView({ behavior : 'smooth'});
            }
        }
    })

    shoes.addEventListener('click', function(){
        for (let i=0;i<categoryName.length;i++){
            if (categoryName[i].textContent == shoes.textContent){
                categoryName[i].scrollIntoView({ behavior : 'smooth'});
            }
        }
    })

})