package rs.raf.projekat_milan_marinkovic_7921rn.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import rs.raf.projekat_milan_marinkovic_7921rn.models.DailyObligation;

public class DailyViewModel extends ViewModel {

    public static List<DailyObligation> obligationList;
    private MutableLiveData<List<DailyObligation>> obligations = new MutableLiveData<>();

    public DailyViewModel(){


        System.out.println("------------------------------------------------");
        System.out.println("U dailyViewModel konstruktoru "  + obligationList);
        System.out.println("-------------------------------------------------");

//        if(obligationList != null){
//            ArrayList<DailyObligation> submitList = new ArrayList<>(obligationList);
//            obligations.setValue(obligationList);
//        }

    }

    public MutableLiveData<List<DailyObligation>> getObligations() {
        return obligations;
    }

    public void setObligations(){
        if(obligationList != null){
            ArrayList<DailyObligation> submitList = new ArrayList<>(obligationList);
            obligations.setValue(obligationList);
        }
    }

}
