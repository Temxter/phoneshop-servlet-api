const inputs = document.querySelectorAll("input")
for (input of inputs) {
    input.addEventListener('input', inputListener)
}

function inputListener(event) {
    const inputId = event.target.id.substring(6)
    const value = event.target.value
    const button = document.querySelector(`#button-${inputId}`)
    let attribute = button.getAttribute('formaction')
    attribute = attribute.substring(0, attribute.lastIndexOf('=') + 1) + value
    button.setAttribute('formaction', attribute)
}