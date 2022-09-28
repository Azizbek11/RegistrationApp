package admiral.group.registrationapp.ui.register

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import admiral.group.registrationapp.R
import admiral.group.registrationapp.databinding.FragmentLoginBinding
import admiral.group.registrationapp.databinding.FragmentRegisterBinding
import admiral.group.registrationapp.ui.login.LoginFragment
import admiral.group.registrationapp.ui.login.LoginViewModel
import admiral.group.registrationapp.ui.welcome.WelcomeViewModel
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() = RegisterFragment()
    }
    
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_register, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       

        binding.btnRegister.setOnClickListener {

            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.tvHavenAccount.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }


        // FullName Validation
        val nameStream= RxTextView.textChanges(binding.etFullname)
            .skipInitialValue()
            .map {
                    name ->
                name.isEmpty()
            }

        nameStream.subscribe{
            showNameExistAlert(it)
        }

        // Email validation

        val emailStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe{
            showEmailValidAlert(it)
        }

        //Username Validation

        val usernameStream= RxTextView.textChanges(binding.etUsername)
            .skipInitialValue()
            .map { username ->
                username.length < 6
            }

        usernameStream.subscribe{
            showTextMinimalAlert(it,"Username")
        }

        //Password Validation

        val passwordStream= RxTextView.textChanges(binding.etPassword)
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
                binding.btnRegister.backgroundTintList= ContextCompat.getColorStateList(requireActivity().applicationContext, R.color.primary_color)
            }else{
                binding.btnRegister.isEnabled=true
                binding.btnRegister.backgroundTintList=
                    ContextCompat.getColorStateList(requireActivity().applicationContext, android.R.color.darker_gray)

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