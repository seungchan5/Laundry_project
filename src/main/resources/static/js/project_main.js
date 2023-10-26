window.addEventListener('load', function(){
    const modal = document.querySelector('.modal');
    const cancelBtn = document.querySelector('.modalBox .cancel');

    cancelBtn.addEventListener('click', function(){
        modal.classList.add('hidden');
    })
})




let currentSlide = 0;
const slides = document.querySelectorAll('.slide');
const slideCount = slides.length;

function showSlide(n) {
    slides.forEach(slide => slide.style.display = 'none');
    slides[n].style.display = 'block';
}

function nextSlide() {
    currentSlide = (currentSlide + 1) % slideCount;
    showSlide(currentSlide);
}

function prevSlide() {
    currentSlide = (currentSlide - 1 + slideCount) % slideCount;
    showSlide(currentSlide);
}


document.addEventListener('DOMContentLoaded', () => {
    showSlide(currentSlide);
    setInterval(nextSlide, 3000); // 5초마다 자동 슬라이드
});
