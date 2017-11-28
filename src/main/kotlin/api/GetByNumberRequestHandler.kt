package api

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import db.Course

class GetByNumberRequestHandler : RequestHandler<String, Course> {
    override fun handleRequest(number: String, context: Context): Course {
        Course.getConnection().use {
            return Course.getByNumber(number, it)
        }
    }
}
