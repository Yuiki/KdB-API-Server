package db

import java.sql.Connection
import java.sql.DriverManager

class CourseRepository {
    private val connection: Connection by lazy {
        Class.forName("org.sqlite.JDBC")
        DriverManager.getConnection("jdbc:sqlite::resource:kdb.db")
    }

    fun initialize(courses: List<Course>) {
        connection.createStatement().use {
            it.execute("DROP TABLE IF EXISTS Courses")
            it.execute("CREATE TABLE Courses(number TEXT, name TEXT, classMethod TEXT, credits TEXT, year TEXT, term TEXT, weekdayAndPeriod TEXT, classRoom TEXT, instructor TEXT, overview TEXT, remarks TEXT)")
        }

        connection.prepareStatement("INSERT INTO Courses VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)").use { statement ->
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

    fun getByNumber(number: String): Course =
            connection.prepareStatement("SELECT * FROM Courses WHERE number = ?").use {
                it.setString(1, number)
                val result = it.executeQuery()
                return Course(
                        number = result.getString("number"),
                        name = result.getString("name"),
                        classMethod = result.getString("classMethod"),
                        credits = result.getString("credits"),
                        year = result.getString("year"),
                        term = result.getString("term"),
                        weekdayAndPeriod = result.getString("weekdayAndPeriod"),
                        classRoom = result.getString("classRoom"),
                        instructor = result.getString("instructor"),
                        overview = result.getString("overview"),
                        remarks = result.getString("remarks")
                )
            }
}