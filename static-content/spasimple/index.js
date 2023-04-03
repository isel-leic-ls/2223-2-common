window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler(){
    hashChangeHandler()
}

function hashChangeHandler(){

    const mainContent = document.getElementById("mainContent")

    switch(window.location.hash){
        case '#home' : return home()
        case '#students' : return students()
        case '#students/10' : return studentDetails()
        default : return defaultRoute()
    }

    // Routes handler functions

    function home() {
        const h1 = document.createElement("h1")
        const text = document.createTextNode("Home");
        h1.appendChild(text)
        mainContent.replaceChildren(h1)
    }

    function students() {
        fetch("http://localhost:9000/students")
            .then(res => res.json())
            .then(students => {
                const text = document.createTextNode(JSON.stringify(students));
                mainContent.replaceChildren(text)
            })
    }

    function studentDetails() {
        fetch("http://localhost:9000/students/10")
            .then(res => res.json())
            .then(student => {
                console.log(student)
                const text = document.createTextNode(JSON.stringify(student));
                mainContent.replaceChildren(text)
            })
    }

    function defaultRoute() {
        window.location.hash = "home"
    }
}