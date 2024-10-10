import com.bartekjobhunt.grazerproject.network.model.Auth
import com.bartekjobhunt.grazerproject.network.model.Data
import com.bartekjobhunt.grazerproject.network.model.LoginRequest
import com.bartekjobhunt.grazerproject.network.model.LoginResponse
import com.bartekjobhunt.grazerproject.network.model.Meta
import com.bartekjobhunt.grazerproject.network.model.User
import com.bartekjobhunt.grazerproject.network.model.Users
import com.bartekjobhunt.grazerproject.network.model.UsersData
import com.bartekjobhunt.grazerproject.network.service.GrazerService
import com.bartekjobhunt.grazerproject.repository.RetrofitGrazerRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doThrow
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
    fun `login should return true when it's successful`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val loginRequest = LoginRequest(email, password)
        val loginResponse = LoginResponse(200, "OK", Auth(Data("someTokenForThisTest")))

        whenever(grazerService.login(loginRequest)).thenReturn(loginResponse)

        val actual = repository.login(email, password)

        assertTrue(actual)
    }

    @Test
    fun `login should return false when it's unsuccessful`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val loginRequest = LoginRequest(email, password)
        val loginResponse = LoginResponse(401, "OK", Auth(Data("someTokenForThisTest")))

        whenever(grazerService.login(loginRequest)).thenReturn(loginResponse)

        val actual = repository.login(email, password)

        assertFalse(actual)
    }

    @Test
    fun `login should return false when there's no token`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val loginRequest = LoginRequest(email, password)
        val loginResponse = LoginResponse(200, "OK", null)

        whenever(grazerService.login(loginRequest)).thenReturn(loginResponse)

        val actual = repository.login(email, password)

        assertFalse(actual)
    }

    @Test
    fun `login should return false when call throws an exception`() = runTest {
        val email = "test@example.com"
        val password = "password"
        val loginRequest = LoginRequest(email, password)

        doThrow(RuntimeException("Network error")).whenever(grazerService).login(loginRequest)

        val actual = repository.login(email, password)

        assertFalse(actual)
    }

    @Test
    fun `getUsers should return a list of users when it's successful`() = runTest {
        val user1 = User("John Doe", 1234567890, "https://example.com/image1.jpg")
        val user2 = User("Jane Smith", 9876543210, "https://example.com/image2.jpg")
        val loginRequest = LoginRequest("email", "password")

        val loginResponse = LoginResponse(200, "OK", Auth(Data("someTokenForThisTest")))

        whenever(grazerService.login(loginRequest)).thenReturn(loginResponse)
        repository.login("email", "password")

        whenever(grazerService.getUsers("Bearer someTokenForThisTest"))
            .thenReturn(
                Users(200, "OK", UsersData(listOf(user1, user2), Meta(2, 1, 1)))
            )
        val expected = listOf(user1, user2)

        val actual = repository.getUsers()

        assertEquals(expected, actual)
    }

    @Test(expected = IllegalStateException::class)
    fun `getUsers should throw an exception when response is not OK`() = runTest {
        val user1 = User("John Doe", 1234567890, "https://example.com/image1.jpg")
        val user2 = User("Jane Smith", 9876543210, "https://example.com/image2.jpg")
        val loginRequest = LoginRequest("email", "password")

        val loginResponse = LoginResponse(200, "OK", Auth(Data("someTokenForThisTest")))

        whenever(grazerService.login(loginRequest)).thenReturn(loginResponse)
        whenever(grazerService.getUsers("Bearer someTokenForThisTest"))
            .thenReturn(
                Users(400, "OK", UsersData(listOf(user1, user2), Meta(2, 1, 1)))
            )

        repository.login("email", "password")

        repository.getUsers()
    }

    @Test(expected = IllegalStateException::class)
    fun `getUsers should throw an exception when token is not initialized`() = runTest {
        val loginRequest = LoginRequest("email", "password")
        val loginResponse = LoginResponse(400, "OK", null)

        whenever(grazerService.login(loginRequest)).thenReturn(loginResponse)
        repository.login("email", "password")
        repository.getUsers()
    }
}
