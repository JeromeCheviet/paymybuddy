const targetDiv = document.querySelector(".description-row-container")
const btn = document.getElementById("toggle");

btn.addEventListener("click", (event) => {
    event.preventDefault();
    targetDiv.classList.add('active');
    event.target.setAttribute('disabled', true);
});