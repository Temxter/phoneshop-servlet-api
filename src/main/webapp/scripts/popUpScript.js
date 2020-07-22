const popUps = document.querySelectorAll(".popup")

for (const popUp of popUps) {
    popUp.addEventListener('click', popupHandler)
}

let lastPopUp;

function popupHandler(event) {
    const popUp = event.currentTarget.querySelector('span')
    popUp.classList.toggle('show')
    if (lastPopUp !== undefined && lastPopUp !== popUp) {
        if (lastPopUp.classList.contains('show')) {
            lastPopUp.classList.remove('show')
        }
    }
    lastPopUp = popUp
}