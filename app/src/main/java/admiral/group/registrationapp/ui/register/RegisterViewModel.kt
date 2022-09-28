package admiral.group.registrationapp.ui.register

import admiral.group.registrationapp.data.SharedPref
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    var sharedPref: SharedPref
): ViewModel() {

    fun saveData(fullName:String, email:String, userName:String, password:String){
        sharedPref.setUserFullName(fullName)
        sharedPref.setEmail(email)
        sharedPref.setUserName(userName)
        sharedPref.setPassword(password)
    }

}