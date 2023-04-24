/*
This example creates the students views using directly the DOM Api
But you can create the views in a different way, for example, for the student details you can:
    createElement("ul",
        createElement("li", "Name : " + student.name),
        createElement("li", "Number : " + student.number)
    )
or
    ul(
        li("Name : " + student.name),
        li("Number : " + student.name)
    )
Note: You have to use the DOM Api, but not directly
*/

const API_BASE_URL = "http://localhost:9000/"

function getHome(mainContent){

    const h1 = document.createElement("h1")
    const text = document.createTextNode("Home")
    h1.appendChild(text)
    mainContent.replaceChildren(h1)
}

function getStudents(mainContent){
    fetch(API_BASE_URL + "students")
        .then(res => res.json())
        .then(students => {
            const div = document.createElement("div")

            const h1 = document.createElement("h1")
            const text = document.createTextNode("Students")
            h1.appendChild(text)
            div.appendChild(h1)

            const aCreateStudent = document.createElement("a")
            const createText = document.createTextNode("New Student" );
            aCreateStudent.appendChild(createText)
            aCreateStudent.href="#students/create"
            div.appendChild(aCreateStudent)

            students.forEach(s => {
                const p = document.createElement("p")
                const a = document.createElement("a")
                const aText = document.createTextNode("Link Example to students/" + s.number);
                a.appendChild(aText)
                a.href="#students/" + s.number
                p.appendChild(a)
                div.appendChild(p)
            })
            mainContent.replaceChildren(div)
        })
}

function getStudent(mainContent){
    fetch(API_BASE_URL + "students/10")
        .then(res => res.json())
        .then(student => {
            const ulStd = document.createElement("ul")

            const liName = document.createElement("li")
            const textName = document.createTextNode("Name : " + student.name)
            liName.appendChild(textName)

            const liNumber = document.createElement("li")
            const textNumber = document.createTextNode("Number : " + student.number)
            liNumber.appendChild(textNumber)

            ulStd.appendChild(liName)
            ulStd.appendChild(liNumber)

            mainContent.replaceChildren(ulStd)
    })
}

/*<form id = "createStudent" >
    <label>Name</label>
    <input type="text" id="idName" >
    <label>Number</label>
    <input type="text" id="idNumber" >
    <input type="submit" >
</form>*/

function createStudent(mainContent){
        const form = document.createElement("form")

        const labelName = document.createElement("label")
        const textName = document.createTextNode("Name")
        labelName.appendChild(textName)
        const inputName = document.createElement("input")
        inputName.type = "text"
        inputName.id = "idName"

        const labelNumber = document.createElement("label")
        const textNumber = document.createTextNode("Number")
        labelNumber.appendChild(textNumber)
        const inputNumber= document.createElement("input")
        inputNumber.type = "text"
        inputNumber.id = "idNumber"

        const inputSubmit = document.createElement("input")
        inputSubmit.type = "submit"

        form.appendChild(labelName)
        form.appendChild(inputName)
        form.appendChild(labelNumber)
        form.appendChild(inputNumber)
        form.appendChild(inputSubmit)

        form.addEventListener('submit', handleSubmit)

        function handleSubmit(e) {
            e.preventDefault()
            const inputName = document.querySelector("#idName")
            const inputNumber = document.querySelector("#idNumber")
            const options = {
                    method : "POST",
                    headers : {
                        "Content-Type" : "application/json",
                        "Accept" : "application/json"
                    },
                    body : JSON.stringify({
                        name : inputName.value,
                        number : parseInt(inputNumber.value)
                    })
                }
                fetch(API_BASE_URL + "students", options)
                    .then(res => res.json())
                    .then(student => {
                        console.log(student)
                        window.location.hash = "students"
                    })
        }
        mainContent.replaceChildren(form)
}

export const handlers = {
    getHome,
    getStudent,
    getStudents,
    createStudent
}

export default handlers