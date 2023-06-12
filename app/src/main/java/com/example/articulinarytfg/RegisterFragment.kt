import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.view.isVisible
import com.example.articulinarytfg.*
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment(R.layout.fragment_register) {

    var UsernameUser = ""
    var MailUser = ""
    var PasswordUser = ""
    var UserImage = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ApiRest.initService()

        (activity as MainActivity).findViewById<NavigationView>(R.id.bottomNavigationView).isVisible =
            false

        (activity as MainActivity).findViewById<NavigationView>(R.id.bottomAppBar).isVisible =
            false

        (activity as MainActivity).findViewById<NavigationView>(R.id.fab).isVisible =
            false

        val ButtonToLogin = view.findViewById<Button>(R.id.movetologin_button)

        ButtonToLogin.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                ?.replace(R.id.container, LogInFragment())?.commit()
        }


        val ETUsername = view.findViewById<EditText>(R.id.regis_username)
        val ETEmail = view.findViewById<EditText>(R.id.regis_email)
        val ETPassword = view.findViewById<EditText>(R.id.regis_password)

        val ButtonRegistrarse = view.findViewById<Button>(R.id.btn_register)
        ButtonRegistrarse.setOnClickListener {
            UsernameUser = ETUsername.text.toString()
            MailUser = ETEmail.text.toString()
            PasswordUser = ETPassword.text.toString()
            UserImage =
                "https://firebasestorage.googleapis.com/v0/b/articullinary.appspot.com/o/notes%2Fimages%2Fuserimage.png?alt=media&token=dbf7c8dd-6759-4d20-a792-fc67eb9daea0&_gl=1*13s8vnk*_ga*NjczMjI0NTY4LjE2NzY1MzU5NjA.*_ga_CW55HF8NVT*MTY4NjQ5MzA3NC4yMC4xLjE2ODY0OTMzOTYuMC4wLjA."
            register(MailUser, PasswordUser, UsernameUser, UserImage)
        }
    }

    private fun register(email: String, password: String, username: String, UserImg: String) {
        val crearUser = ApiService.RegisterData(email, password, username, UserImg)
        val call = ApiRest.service.registerUser(crearUser)
        call.enqueue(object : Callback<ApiService.RegisterResponse> {
            override fun onResponse(
                call: Call<ApiService.RegisterResponse>,
                response: Response<ApiService.RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    // Cuenta creada exitosamente
                    activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(null)
                        ?.replace(R.id.container, LogInFragment())?.commit()
                } else {
                    Log.e("RegisterError", response.errorBody()?.string() ?: "Error")
                }
            }

            override fun onFailure(call: Call<ApiService.RegisterResponse>, t: Throwable) {
                Log.e("RegisterFailure", t.message ?: "Error")
            }
        })
    }
}
