package pt.isel.ls.http

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.http4k.core.Method.GET
import org.http4k.core.Method.POST
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status.Companion.CREATED
import org.http4k.core.Status.Companion.OK
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.routing.singlePageApp
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.postgresql.ds.PGSimpleDataSource
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("pt.isel.ls.http.HTTPServer")

@Serializable
data class Student(val name: String, val number: Int)

val students = mutableListOf(
    Student("Filipe", 10),
    Student("Luis", 20),
    Student("Daniel", 30)
)

fun getStudents(request: Request): Response {
    logRequest(request)
    val limit = request.query("limit")?.toInt() ?: 5
    return Response(OK)
        .header("content-type", "application/json")
        .body(Json.encodeToString(students.take(limit)))
}

fun getStudent(request: Request): Response {
    logRequest(request)
    val stdNumber = request.path("number")?.toInt()
    return Response(OK)
        .header("content-type", "application/json")
        .body(Json.encodeToString(students.find { it.number == stdNumber }))
}

fun postStudent(request: Request): Response {
    logRequest(request)
    val std = Json.decodeFromString<Student>(request.bodyString())
    students.add(std)
    return Response(CREATED)
        .header("content-type", "application/json")
        .body(Json.encodeToString(std))
}

fun getDate(request: Request): Response {
    return Response(OK)
        .header("content-type", "text/plain")
        .body(Clock.System.now().toString())
}

fun getStudentsFromPostgres(request: Request): Response {
    logRequest(request)
    val dataSource = PGSimpleDataSource()
    val jdbcDatabaseURL = System.getenv("JDBC_DATABASE_URL") ?: "jdbc:postgresql://localhost/postgres?user=postgres&password=postgres"
    dataSource.setURL(jdbcDatabaseURL)

    val pStudents = mutableListOf<Student>()
    dataSource.connection.use {
        val stm = it.prepareStatement("select name,number from students")
        val rs = stm.executeQuery()
        while (rs.next()) {
            pStudents.add(Student(rs.getString("name"), rs.getInt("number")))
        }
    }
    return Response(OK)
        .header("content-type", "application/json")
        .body(Json.encodeToString(pStudents))
}

fun logRequest(request: Request) {
    logger.info(
        "incoming request: method={}, uri={}, content-type={} accept={}",
        request.method,
        request.uri,
        request.header("content-type"),
        request.header("accept")
    )
}

fun main() {
    val studentRoutes = routes(
        "students" bind GET to ::getStudents,
        "students/{number}" bind GET to ::getStudent,
        "students" bind POST to ::postStudent
    )

    val app = routes(
        studentRoutes,
        "date" bind GET to ::getDate,
        "postgres/students" bind GET to ::getStudentsFromPostgres,
        singlePageApp(ResourceLoader.Directory("static-content"))
    )

    val port = System.getenv("PORT")?.toIntOrNull() ?: 9000
    val jettyServer = app.asServer(Jetty(port)).start()
    logger.info("server started listening")

    readln()
    jettyServer.stop()

    logger.info("leaving Main")
}
