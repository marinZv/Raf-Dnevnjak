package rs.raf.projekat_milan_marinkovic_7921rn.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.projekat_milan_marinkovic_7921rn.models.User;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();

    public LiveData<User> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public void setUserMutableLiveData(User user) {
        userMutableLiveData.setValue(user);
    }
}
