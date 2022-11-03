import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.runningbuddy.repositories.LoginRepository

@Suppress("RedundantVisibilityModifier")
class LoginViewModel (application: Application) : AndroidViewModel(application) {

    private var loginRepository: LoginRepository = LoginRepository(application)
    var email = "e1234567@site.com"
    var password = "e1234567"

    fun postUser(){
        loginRepository.postUser(email, password)
    }
}