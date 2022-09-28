package admiral.group.registrationapp.database

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Singleton
class PrefRepository(@ApplicationContext context: Context) {

    private val pref: SharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private val editor = pref.edit()


     fun setUserFullName(fullName: String) {
        editor.putString("fullName", fullName)
        editor.commit()
    }

     fun setEmail(email:String) {
        editor.putString("email", email)
        editor.commit()
    }

     fun setUserName(userName:String) {
        editor.putString("userName", userName)
        editor.commit()
    }
     fun setPassword(password:String) {
        editor.putString("password", password)
        editor.commit()
    }

     fun getUserName(): String? {
        return pref.getString("userName",null)
    }

     fun getEmail(): String? {
        return pref.getString("email",null)
    }

     fun getPassword(): String? {
        return pref.getString("password",null)
    }

}