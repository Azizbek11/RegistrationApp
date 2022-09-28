package admiral.group.registrationapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    var myRepository: MyRepository,
): ViewModel() {


    fun getUserName():LiveData<String>{

        return myRepository.getMyName()
    }


     fun getEmail():LiveData<String>{
       return myRepository.getEmail()
    }

     fun getPassword():LiveData<String>{
      return myRepository.getPassword()
    }
}