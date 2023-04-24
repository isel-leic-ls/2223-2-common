import router from "./router.js";
import handlers from "./handlers.js";

window.addEventListener('load', loadHandler)
window.addEventListener('hashchange', hashChangeHandler)

function loadHandler(){

    router.addRouteHandler("home", handlers.getHome)
    router.addRouteHandler("students", handlers.getStudents)
    router.addRouteHandler("students/create", handlers.createStudent)
    router.addRouteHandler("students/10", handlers.getStudent)
    router.addDefaultNotFoundRouteHandler(() => window.location.hash = "home")

    hashChangeHandler()
}

function hashChangeHandler(){

    const mainContent = document.getElementById("mainContent")
    const path = window.location.hash.replace("#", "")

    const handler = router.getRouteHandler(path)
    handler(mainContent)
}