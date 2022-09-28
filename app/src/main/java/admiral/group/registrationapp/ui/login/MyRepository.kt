package admiral.group.registrationapp.ui.login

import admiral.group.registrationapp.data.SharedPref
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject


class MyRepository @Inject constructor(
    var sharedPref: SharedPref
) {

    private val fullName= MutableLiveData<String>()
    private val email= MutableLiveData<String>()
    private val password= MutableLiveData<String>()

    fun getMyName():MutableLiveData<String>{
        fullName.value=sharedPref.getUserName()
        return fullName
    }

    fun getEmail():MutableLiveData<String>{
        email.value=sharedPref.getEmail()
        return email
    }

    fun getPassword():MutableLiveData<String>{
        password.value=sharedPref.getPassword()
        return password
    }

}