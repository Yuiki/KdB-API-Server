package db

import org.supercsv.cellprocessor.ift.CellProcessor
import org.supercsv.io.CsvBeanReader
import org.supercsv.prefs.CsvPreference
import java.io.File

class DbMain

fun main(args: Array<String>) {
    val header = arrayOf("number", "name", "classMethod", "credits", "year",
            "term", "weekdayAndPeriod", "classRoom", "instructor", "overview",
            "remarks", null, null, null, null, null, null, null, null)
    val courses = readCsv("kdb.csv", Course::class.java, header)

    val repo = CourseRepository()
    repo.initialize(courses)
}

fun <T> readCsv(fileName: String, clazz: Class<T>, header: Array<String?>): List<T> {
    val csv = File(DbMain::class.java.classLoader.getResource(fileName).file)
    val reader = CsvBeanReader(csv.bufferedReader(), CsvPreference.STANDARD_PREFERENCE)
    val items = mutableListOf<T>()
    reader.use {
        val processors = arrayOfNulls<CellProcessor>(header.size)
        var item: T?
        while (true) {
            item = reader.read(clazz, header, *processors)
            if (item != null) {
                items += item
            } else {
                break
            }
        }
    }
    return items
}