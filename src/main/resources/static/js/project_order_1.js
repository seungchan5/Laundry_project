window.addEventListener('load', function(){

    // 일반 세탁, 빠른세탁 선택시 border 변경 로직
    let options = document.querySelectorAll('.option')[0];
    let labels = document.querySelectorAll('.option label');
    options.addEventListener('click', function(event) {

        if (event.target.tagName === 'INPUT' && event.target.type === 'radio') {
            labels.forEach(label => {
                label.classList.remove('select1');
            });

            let addLabel = document.querySelector(`label[for="${event.target.id}"]`);
            addLabel.classList.add('select1');

            let cal = document.querySelectorAll('.subCal_location');
            if (event.target.id == 'fast'){
                cal.forEach(nav => {
                    nav.style = 'display : none'
                })
                document.querySelector('.notice').classList.remove('none');
            } else {
                cal.forEach(nav => {
                    nav.style = 'display : inline-block'
                })
                document.querySelector('.notice').classList.add('none');
            }
        }
    });

    let checkboxs = document.querySelectorAll('.optionbox input[type=checkbox]');
    let boxs = document.querySelectorAll('.optionbox');


    let drycleaning = document.querySelector('#dryBtn');
    let common = document.querySelector('#commonBtn');
    let repair = document.querySelector('#repairBtn');

    drycleaning.addEventListener('click', function(){
        var options = 'width=600, height=400, top=100, left=100, resizable=yes, scrollbars=yes';
        window.open('/laundry/dry', '_blank');
    })

    common.addEventListener('click', function(){
        this.classList.toggle('select1');
        if (this.classList.contains('select1')){
            document.querySelector('#commonBtn svg').style.fill = 'var(--main-color)'
            document.querySelector('#common_laundry').checked = true;
        } else {
            document.querySelector('#commonBtn svg').style.fill = ''
            document.querySelector('#common_laundry').checked = true;
        }

    })
    repair.addEventListener('click', function(){
        var options = 'width=600, height=400, top=100, left=100, resizable=yes, scrollbars=yes';
        window.open('/laundry/repair', '_blank')
    })


    // submit 클릭시 예외처리
    let submitBtn = document.querySelector('#laundryForm');
    submitBtn.addEventListener('submit', function(event){

        let radioBox = document.querySelectorAll('input[name=takeDateTime]');
        let errorCal = document.querySelector('.error-cal');
        if (!radioBox[0].checked && !radioBox[1].checked){
            errorCal.innerHTML = '시간을 선택해주세요.'
            errorCal.scrollIntoView({ behavior : 'smooth'});
            event.preventDefault();
            return;
        }
        errorCal.innerHTML = '';

        let service = document.querySelectorAll('input[name=service]');
        let error = document.querySelector('.error');
        let cnt = 0;
        for (let i=0;i<service.length;i++){
           if (service[i].checked){
                error.innerHTML = '';
                submitBtn.submit();
                resolve();
           } else {
                cnt++;
           }
        }

        if (cnt != 0){
            error.innerHTML = '한가지 이상 선택해주세요.';
            error.scrollIntoView({ behavior : 'smooth'});
            event.preventDefault();
        }
        return;

    })

})
