package api

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import db.Course
import db.CourseRepository

class GetByNumberRequestHandler : RequestHandler<String, Course> {
    override fun handleRequest(number: String, context: Context): Course {
        val repo = CourseRepository()
        return repo.getByNumber(number)
    }
}
