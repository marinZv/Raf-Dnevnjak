package rs.raf.projekat_milan_marinkovic_7921rn.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Day {

    private int id;
    private LocalDate date;
    private List<DailyObligation> obligationList;
    //private MutableLiveData<List<DailyObligation>> obligations = new MutableLiveData<>();

    public Day(LocalDate date, int id) {
        this.date = date;
        this.id = id;
        obligationList = new ArrayList<>();
    }

    public LocalDate getDate() {
        return date;
    }

    public List<DailyObligation> getObligationList() {
        return obligationList;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setObligationList(List<DailyObligation> obligationList) {
        this.obligationList = obligationList;
    }
//
//    public MutableLiveData<List<DailyObligation>> getObligations() {
//        return obligations;
//    }
//
//    public void setObligations() {
//        ArrayList<DailyObligation> listToSubmit = new ArrayList<>(this.obligationList);
//        obligations.setValue(listToSubmit);
//    }


    public int getId() {
        return id;
    }
}
