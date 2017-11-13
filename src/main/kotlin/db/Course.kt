package db

import java.sql.Connection

data class Course @JvmOverloads constructor(
        var number: String? = null,
        var name: String? = null,
        var classMethod: String? = null,
        var credits: String? = null,
        var year: String? = null,
        var term: String? = null,
        var weekdayAndPeriod: String? = null,
        var classRoom: String? = null,
        var instructor: String? = null,
        var overview: String? = null,
        var remarks: String? = null) {

    companion object {
        fun createTableIfNotExists(conn: Connection) =
                conn.createStatement().use {
                    it.execute("CREATE TABLE IF NOT EXISTS Courses(number TEXT, name TEXT, classMethod TEXT, credits TEXT, year TEXT, term TEXT, weekdayAndPeriod TEXT, classRoom TEXT, instructor TEXT, overview TEXT, remarks TEXT)")
                }

        fun insert(courses: List<Course>, conn: Connection): Unit =
                conn.prepareStatement("INSERT INTO Courses VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").use { statement ->
                    courses.map {
                        arrayOf(it.number, it.name, it.classMethod, it.credits, it.year, it.term,
                                it.weekdayAndPeriod, it.classRoom, it.instructor, it.overview, it.remarks)
                    }.forEach {
                        for ((idx, field) in it.withIndex()) {
                            statement.setString(idx + 1, field)
                        }
                        statement.addBatch()
                    }
                    statement.executeBatch()
                }
    }
}