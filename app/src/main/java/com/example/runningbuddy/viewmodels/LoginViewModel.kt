import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.runningbuddy.repositories.LoginRepository

@Suppress("RedundantVisibilityModifier")
class LoginViewModel (application: Application) : AndroidViewModel(application) {

    private var loginRepository: LoginRepository = LoginRepository(application)
    var email = ""
    var password = ""

    fun postUser(){
        loginRepository.postUser(email, password)
    }
}