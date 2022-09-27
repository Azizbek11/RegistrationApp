package admiral.group.registrationapp


import admiral.group.registrationapp.databinding.ActivityLoginBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding2.widget.RxTextView


class LoginActivity : AppCompatActivity() {


    private lateinit var  binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        binding.tvHaventAccount.setOnClickListener {
            startActivity(Intent(this,RegistrationActivity::class.java))
            finish()
        }


        //Username Validation

        val usernameStream= RxTextView.textChanges(binding.atEmail)
            .skipInitialValue()
            .map { username ->
                username.isEmpty()
            }

        usernameStream.subscribe{
            showTextMinimalAlert(it,"Username")
        }

        //Password Validation

        val passwordStream= RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }

        passwordStream.subscribe{
            showTextMinimalAlert(it,"Password")
        }

        //ButtonEnable true or false
        val invalidFieldStream =io.reactivex.Observable.combineLatest(
            usernameStream,
            passwordStream,
        ) { usernameInvalid: Boolean,
            passwordInvalid: Boolean ->
            !usernameInvalid && !passwordInvalid

        }
        invalidFieldStream.subscribe{
                isValid->
            if (isValid){
                binding.btnLogin.isEnabled=true
                binding.btnLogin.backgroundTintList= ContextCompat.getColorStateList(this, R.color.primary_color)
            }else{
                binding.btnLogin.isEnabled=true
                binding.btnLogin.backgroundTintList=
                    ContextCompat.getColorStateList(this, android.R.color.darker_gray)
            }
        }

    }


    private fun showTextMinimalAlert(isNotValid: Boolean, text:String){
        if (text=="Email/Username"){
            binding.atEmail.error=if (isNotValid) "$text mavjud emas" else null
        }else if(text=="Password"){
            binding.etPassword.error=if (isNotValid) "$text mavjud emas" else null
        }
    }
}