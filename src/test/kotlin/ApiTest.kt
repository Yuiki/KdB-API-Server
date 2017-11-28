import api.GetByNumberRequestHandler
import com.amazonaws.services.lambda.runtime.Context
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import java.sql.SQLException

class ApiTest {
    private lateinit var getByNumberSut: GetByNumberRequestHandler
    private val context = mock(Context::class.java)

    @Before
    fun setUp() {
        getByNumberSut = GetByNumberRequestHandler()
    }

    @Test
    fun testGetByNumber() {
        val res = getByNumberSut.handleRequest("GB10804", context)

        assertThat("論理回路").isEqualTo(res.name)
    }

    @Test(expected = SQLException::class)
    fun testGetByIllegalNumber() {
        getByNumberSut.handleRequest("ILLEGAL", context)
    }
}