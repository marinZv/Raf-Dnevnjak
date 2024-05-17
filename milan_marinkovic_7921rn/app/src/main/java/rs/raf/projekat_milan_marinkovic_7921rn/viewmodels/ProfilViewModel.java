package rs.raf.projekat_milan_marinkovic_7921rn.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import rs.raf.projekat_milan_marinkovic_7921rn.models.User;

public class ProfilViewModel extends ViewModel {

    private MutableLiveData<User> userProfileMutableLiveData = new MutableLiveData<>();

    public LiveData<User> getUserProfileMutableLiveData() {
        return userProfileMutableLiveData;
    }

    public void setUserProfileMutableLiveData(User user) {
        userProfileMutableLiveData.setValue(user);
    }
}
