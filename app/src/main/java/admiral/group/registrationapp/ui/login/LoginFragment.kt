package admiral.group.registrationapp.ui.login


import admiral.group.registrationapp.R
import admiral.group.registrationapp.databinding.FragmentLoginBinding
import admiral.group.registrationapp.ui.main.MainViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.jakewharton.rxbinding2.widget.RxTextView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment @Inject constructor() : Fragment() {


    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: FragmentLoginBinding

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        var userName=""
        var email=""
        var password=""

        viewModel.getUserName().observe(viewLifecycleOwner) {
            if (it!=null){
                userName = it
            }
        }

        viewModel.getEmail().observe(viewLifecycleOwner) {
            if (it!=null){
                email = it
            }
        }

        viewModel.getPassword().observe(viewLifecycleOwner) {
            if (it!=null){
                password = it
            }
        }

        navHostFragment =
            activity?.supportFragmentManager?.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.btnLogin.setOnClickListener {
            if ((binding.atEmail.text.toString().trim()==
                email||binding.atEmail.text.toString().trim()==userName)&&binding.etPassword.text.toString().trim()==
                    password){
                Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show()
                navController.navigate(R.id.action_loginFragment_to_welcomeFragment)

            }else{
                Toast.makeText(requireActivity(), "User don't found", Toast.LENGTH_SHORT).show()
            }

        }

        binding.tvHaventAccount.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registerFragment)
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
                binding.btnLogin.backgroundTintList= ContextCompat.getColorStateList(context!!,
                    R.color.primary_color
                )
            }else{
                binding.btnLogin.isEnabled=true
                binding.btnLogin.backgroundTintList=
                    ContextCompat.getColorStateList(context!!, android.R.color.darker_gray)
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