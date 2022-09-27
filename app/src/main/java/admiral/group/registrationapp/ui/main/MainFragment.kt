package admiral.group.registrationapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import admiral.group.registrationapp.R
import admiral.group.registrationapp.databinding.FragmentMainBinding
import admiral.group.registrationapp.ui.register.RegisterViewModel

import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment()  {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: FragmentMainBinding

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_main, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        super.onViewCreated(view, savedInstanceState)
        navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.btnLogin.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_loginFragment)
        }

        binding.btnRegister.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_registerFragment)
        }


    }
}