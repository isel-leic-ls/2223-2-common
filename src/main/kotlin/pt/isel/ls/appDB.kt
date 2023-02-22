package pt.isel.ls

import org.postgresql.ds.PGSimpleDataSource

fun main(){

    val dataSource = PGSimpleDataSource()
    val jdbcDatabaseURL = System.getenv("JDBC_DATABASE_URL")
    dataSource.setURL(jdbcDatabaseURL)
    //dataSource.setURL("jdbc:postgresql://localhost/postgres?user=postgres&password=postgres")

    dataSource.getConnection().use {
        val stm = it.prepareStatement("select * from students")
        val rs = stm.executeQuery()
        while (rs.next()) {
            println(rs.getString("name"))
        }
    }
}