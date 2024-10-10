import com.bartekjobhunt.grazerproject.network.model.Auth
import com.bartekjobhunt.grazerproject.network.model.Data
import com.bartekjobhunt.grazerproject.network.model.LoginRequest
import com.bartekjobhunt.grazerproject.network.model.LoginResponse
import com.bartekjobhunt.grazerproject.network.service.GrazerService
import com.bartekjobhunt.grazerproject.repository.RetrofitGrazerRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever


class RetrofitGrazerRepositoryTest {

    @Mock
    private lateinit var grazerService: GrazerService

    private lateinit var repository: RetrofitGrazerRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = RetrofitGrazerRepository(grazerService)
    }

    @Test
    fun `login should return LoginResponse from service`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val loginRequest = LoginRequest(email, password)
        val expectedLoginResponse = LoginResponse(200, "OK", Auth(Data("someTokenForThisTest")))

        whenever(grazerService.login(loginRequest)).thenReturn(expectedLoginResponse)

        val actualLoginResponse = repository.login(email, password)

        assertEquals(expectedLoginResponse, actualLoginResponse)
    }
}
