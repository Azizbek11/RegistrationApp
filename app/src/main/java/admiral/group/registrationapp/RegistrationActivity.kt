package admiral.group.registrationapp


import admiral.group.registrationapp.databinding.ActivityRegistrationBinding
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.jakewharton.rxbinding2.widget.RxTextView
import java.util.Observable


class RegistrationActivity : AppCompatActivity() {

    private lateinit var  binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Clicks
        binding.btnRegister.setOnClickListener {
            if (binding.btnRegister.isEnabled){
            startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }
        }

        binding.tvHavenAccount.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

        // FullName Validation
        val nameStream=RxTextView.textChanges(binding.etFullname)
            .skipInitialValue()
            .map {
                    name ->
                name.isEmpty()
            }

        nameStream.subscribe{
            showNameExistAlert(it)
        }

        // Email validation

        val emailStream =RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe{
            showEmailValidAlert(it)
        }

        //Username Validation

        val usernameStream=RxTextView.textChanges(binding.etUsername)
            .skipInitialValue()
            .map { username ->
                username.length < 6
            }

        usernameStream.subscribe{
            showTextMinimalAlert(it,"Username")
        }

        //Password Validation

        val passwordStream=RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 8
            }

        passwordStream.subscribe{
            showTextMinimalAlert(it,"Password")
        }
        //Confirm Password Validation
        val passwordConfirmStream=io.reactivex.Observable.merge(
            RxTextView.textChanges(binding.etPassword)
                .skipInitialValue()
                .map { password ->
                    password.toString() !=binding.etConfirmPassword.text.toString()
                },
            RxTextView.textChanges(binding.etConfirmPassword)
                .skipInitialValue()
                .map { confirmPassword ->
                    confirmPassword.toString() !=binding.etPassword.text.toString()
                })
        passwordConfirmStream.subscribe{
            showPasswordConfirmAlert(it)
        }

        //ButtonEnable true or false
        val invalidFieldStream =io.reactivex.Observable.combineLatest(
            nameStream,
            emailStream,
            usernameStream,
            passwordStream,
            passwordConfirmStream
        ) { nameInvalid: Boolean, emailInvalid: Boolean, usernameInvalid: Boolean,
            passwordInvalid: Boolean, passwordConfirmInvalid: Boolean ->
            !nameInvalid && !emailInvalid && !usernameInvalid && !passwordInvalid
                    && !passwordConfirmInvalid
        }
        invalidFieldStream.subscribe{
                isValid->
            if (isValid){
                binding.btnRegister.isEnabled=true
                binding.btnRegister.backgroundTintList=ContextCompat.getColorStateList(this, R.color.primary_color)
            }else{
                binding.btnRegister.isEnabled=true
                binding.btnRegister.backgroundTintList=ContextCompat.getColorStateList(this, android.R.color.darker_gray)

            }
        }
    }


    private fun showNameExistAlert(isNotValid:Boolean){
        binding.etPassword.error=if (isNotValid) "Enter fullname" else null
    }

    private fun showTextMinimalAlert(isNotValid: Boolean, text:String){
        if (text=="Username"){
            binding.etUsername.error=if (isNotValid) "$text 6 ta belgidan ko'p bo'lishi kerak" else null
        }else if(text=="Password"){
            binding.etPassword.error=if (isNotValid) "$text 8 ta belgidan ko'p bo'lishi kerak" else null
        }
    }

    private fun showEmailValidAlert(isNotValid: Boolean){
        binding.etEmail.error=if (isNotValid) "Email kiriting" else null
    }

    private fun showPasswordConfirmAlert(isNotValid: Boolean){
        binding.etConfirmPassword.error=if (isNotValid) "Parol bir xil emas" else null
    }


}