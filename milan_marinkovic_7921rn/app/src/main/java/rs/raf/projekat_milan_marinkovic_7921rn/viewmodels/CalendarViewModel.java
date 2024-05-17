package rs.raf.projekat_milan_marinkovic_7921rn.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import rs.raf.projekat_milan_marinkovic_7921rn.models.DailyObligation;
import rs.raf.projekat_milan_marinkovic_7921rn.models.Day;
import rs.raf.projekat_milan_marinkovic_7921rn.models.ObligationType;

public class CalendarViewModel extends ViewModel {

    private List<Day> dayList;

    private int tmp;
    private final MutableLiveData<List<Day>> days = new MutableLiveData();

    private final MutableLiveData<Day> selectedDay = new MutableLiveData();

    private final MutableLiveData<List<DailyObligation>> obligations = new MutableLiveData();
    private List<DailyObligation> savedStateObligations = new ArrayList<>();
    private MutableLiveData<DailyObligation> editSelectedDaily = new MutableLiveData<>();

    public CalendarViewModel(){
        createDays();
        System.out.println("DayList , item " + dayList.get(tmp).getDate() + " " + dayList.get(tmp).getObligationList() );
        System.out.println("");
        ArrayList<Day> dayListToSubmit = new ArrayList<>(dayList);
        days.setValue(dayListToSubmit);
        //System.out.println("Dan " + days.getValue().get(tmp).getDate()+ "Lista obaveza " +days.getValue().get(tmp).getObligationList());
    }

    private void createDays(){

        LocalDate current = LocalDate.now();
        LocalDate firstDayOfCurrent = current.withDayOfMonth(1);
        LocalDate previous = firstDayOfCurrent.minusMonths(1);
        LocalDate previousPrevious = firstDayOfCurrent.minusMonths(2);
        LocalDate next = firstDayOfCurrent.plusMonths(1);
        LocalDate nextNext = firstDayOfCurrent.plusMonths(2);

        int idCounter = 0;


        dayList = new ArrayList<>();
        for (int i = 0; i < previousPrevious.lengthOfMonth(); i++){
            LocalDate date = previousPrevious.plusDays(i);
            dayList.add(new Day(date, idCounter));
            idCounter++;
        }

        for(int i = 0; i < previous.lengthOfMonth(); i++){
            LocalDate date = previous.plusDays(i);
            dayList.add(new Day(date, idCounter));
            idCounter++;
        }

        for(int i = 0; i < firstDayOfCurrent.lengthOfMonth(); i++){
            LocalDate date = firstDayOfCurrent.plusDays(i);
            Day day = new Day(date, idCounter);
            if(i == 5){
                LocalDateTime startDate1 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 10, 30, 0);
                LocalDateTime endDate1 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 12, 0, 0);
                DailyObligation dailyObligation1 = new DailyObligation(0,"Algebra", ObligationType.HIGH, startDate1, endDate1, "Grupe i podgrupe\nKolarac\nPonesi svesku");
                addObligationToObligationListPerDay(day, dailyObligation1);

                LocalDateTime startDate2 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 8, 0, 0);
                LocalDateTime endDate2 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 10, 15, 0);
                DailyObligation dailyObligation2 = new DailyObligation(1, "RMA", ObligationType.MID, startDate2, endDate2, "Grupe i podgrupe\nKolarac\nPonesi svesku");
                addObligationToObligationListPerDay(day, dailyObligation2);

                LocalDateTime startDate3 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 17, 30, 0);
                LocalDateTime endDate3 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 19, 0, 0);
                DailyObligation dailyObligation3 = new DailyObligation(2, "Web Programiranje", ObligationType.LOW, startDate3, endDate3, "Grupe i podgrupe\nKolarac\nPonesi svesku");
                addObligationToObligationListPerDay(day, dailyObligation3);

                tmp = idCounter;

            }else if(i == 8){
                LocalDateTime startDate2 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 8, 0, 0);
                LocalDateTime endDate2 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 10, 15, 0);
                DailyObligation dailyObligation2 = new DailyObligation(0, "RMA", ObligationType.MID, startDate2, endDate2, "Grupe i podgrupe\nKolarac\nPonesi svesku");
                addObligationToObligationListPerDay(day, dailyObligation2);

                LocalDateTime startDate3 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 17, 30, 0);
                LocalDateTime endDate3 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 19, 0, 0);
                DailyObligation dailyObligation3 = new DailyObligation(1,"Web Programiranje", ObligationType.LOW, startDate3, endDate3, "Grupe i podgrupe\nKolarac\nPonesi svesku");
                addObligationToObligationListPerDay(day, dailyObligation3);
            }else if(i == 10){
                LocalDateTime startDate3 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 17, 30, 0);
                LocalDateTime endDate3 = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 19, 0, 0);
                DailyObligation dailyObligation3 = new DailyObligation(0, "Web Programiranje", ObligationType.LOW, startDate3, endDate3, "Grupe i podgrupe\nKolarac\nPonesi svesku");
                addObligationToObligationListPerDay(day, dailyObligation3);
            }

            dayList.add(day);
            idCounter++;
        }

        for(int i = 0; i < next.lengthOfMonth(); i++){
            LocalDate date = next.plusDays(i);
            dayList.add(new Day(date, idCounter));
            idCounter++;
        }

        for (int i = 0; i < nextNext.lengthOfMonth(); i++){
            LocalDate date = nextNext.plusDays(i);
            dayList.add(new Day(date, idCounter));
            idCounter++;
        }

    }

    public MutableLiveData<List<DailyObligation>> getObligations() {
        return obligations;
    }

    public void setObligations(List<DailyObligation> obligationList){
        ArrayList<DailyObligation> submitList = new ArrayList<>(obligationList);
        obligations.setValue(submitList);
    }

    public LiveData<List<Day>> getDays() {
        return days;
    }

    public void setSelectedDay(Day day){
        selectedDay.setValue(day);
    }

    public LiveData<Day> getSelectedDay() {
        return selectedDay;
    }

    public void addObligationToObligationListPerDay(Day day, DailyObligation dailyObligation){
        day.getObligationList().add(dailyObligation);
        //System.out.println("Dodao sam obavezu za dan " + day.getDate());
        System.out.println("Klasa:CalendarViewModel --> ObligationList za dan " + day.getDate() + " " + day.getObligationList());

    }

    public boolean addObligationToObligations(String obligationName, ObligationType obligationType, LocalDateTime startTime,
                                              LocalDateTime endTime, String obligationContent){


        for(DailyObligation obligation : obligations.getValue()){
            if(startTime.isBefore(obligation.getEndTime()) && endTime.isAfter(obligation.getStartTime())){
                return false;
            }
        }

        int id = obligations.getValue().size();

        DailyObligation dailyObligation = new DailyObligation(id, obligationName, obligationType, startTime,
                endTime, obligationContent);

        obligations.getValue().add(dailyObligation);
        ArrayList<DailyObligation> submitList = new ArrayList<>(obligations.getValue());
        obligations.setValue(submitList);

        System.out.println(obligations.getValue());

        return true;
    }

    public void removeObligationFromObligations(int id){
        Optional<DailyObligation> dailyObligationObject = obligations.getValue().stream().filter(dailyObligation -> dailyObligation.getId() == id).findFirst();
        DailyObligation dailyObligation = dailyObligationObject.get();
        if(dailyObligationObject.isPresent()){
            List<DailyObligation> tmpList = obligations.getValue();
//            tmpList.remove(id);
            removeDailyById(tmpList, id);
            ArrayList<DailyObligation> submitList = new ArrayList<>(tmpList);
            setObligations(submitList);

            for(Day d : dayList){
                if(d.getDate().getMonth() == dailyObligation.getStartTime().getMonth() &&
                        d.getDate().getDayOfMonth() == dailyObligation.getStartTime().getDayOfMonth()){
                    d.getObligationList().remove(dailyObligation);
                    break;
                }
            }
            ArrayList<Day> submitDayList = new ArrayList<>(dayList);
            days.setValue(submitDayList);
        }
    }

    private void removeDailyById(List<DailyObligation> list, int id){
        DailyObligation dailyObligation = null;
        for(DailyObligation l : list){
            if(l.getId() == id){
                dailyObligation = l;
                break;
            }
        }
        if(dailyObligation != null)
            list.remove(dailyObligation);
    }

    public void filterObligationsByName(String filterString){
        //savedStateObligations = obligations.getValue();
        List<DailyObligation> filteredList = obligations.getValue().stream().filter(dailyObligation -> dailyObligation.getObligationName().toLowerCase().startsWith(filterString.toLowerCase())).collect(Collectors.toList());
        setObligations(filteredList);
    }

    public void showPastObligations(){
        savedStateObligations = obligations.getValue();
        LocalDateTime now = LocalDateTime.now();
        List<DailyObligation> filteredList = obligations.getValue().stream().filter(dailyObligation -> dailyObligation.getEndTime().isBefore(now)).collect(Collectors.toList());
        savedStateObligations.remove(filteredList);
        ArrayList<DailyObligation> submitList = new ArrayList<>(filteredList);
        obligations.setValue(submitList);
    }

    public void hidePastObligations(){
        ArrayList<DailyObligation> submitList = new ArrayList<>(savedStateObligations);
        obligations.setValue(submitList);
    }


    public void sortObligationsByPriority(ObligationType obligationType){
        List<DailyObligation> lista = obligations.getValue(); // Vaša lista objekata klase DailyObligation

        Collections.sort(lista, new Comparator<DailyObligation>() {
            @Override
            public int compare(DailyObligation o1, DailyObligation o2) {
                if (obligationType == ObligationType.LOW) {
                    if (o1.getObligationType() == ObligationType.HIGH && o2.getObligationType() != ObligationType.HIGH) {
                        return -1;
                    } else if (o1.getObligationType() != ObligationType.HIGH && o2.getObligationType() == ObligationType.HIGH) {
                        return 1;
                    } else if (o1.getObligationType() == ObligationType.MID && o2.getObligationType() == ObligationType.LOW) {
                        return -1;
                    } else if (o1.getObligationType() == ObligationType.LOW && o2.getObligationType() == ObligationType.MID) {
                        return 1;
                    } else {
                        return o1.getObligationType().compareTo(o2.getObligationType());
                    }
                } else if (obligationType == ObligationType.MID) {
                    if (o1.getObligationType() == ObligationType.MID && o2.getObligationType() != ObligationType.MID) {
                        return -1;
                    } else if (o1.getObligationType() == ObligationType.HIGH && o2.getObligationType() == ObligationType.LOW) {
                        return -1;
                    } else if (o1.getObligationType() == ObligationType.LOW && o2.getObligationType() == ObligationType.HIGH) {
                        return 1;
                    } else if (o1.getObligationType() != ObligationType.MID && o2.getObligationType() == ObligationType.MID) {
                        return 1;
                    } else {
                        return o1.getObligationType().compareTo(o2.getObligationType());
                    }
                } else if (obligationType == ObligationType.HIGH) {

                    if (o1.getObligationType() == ObligationType.LOW && o2.getObligationType() != ObligationType.LOW) {
                        return -1;
                    } else if (o1.getObligationType() == ObligationType.MID && o2.getObligationType() == ObligationType.HIGH) {
                        return -1;
                    } else if (o1.getObligationType() == ObligationType.HIGH && o2.getObligationType() == ObligationType.MID) {
                        return 1;
                    } else if (o1.getObligationType() != ObligationType.LOW && o2.getObligationType() == ObligationType.LOW) {
                        return 1;
                    } else {
                        return o1.getObligationType().compareTo(o2.getObligationType());
                    }
                }
                return 0;
            }
        });

        ArrayList<DailyObligation> submitList = new ArrayList<>(lista);
        setObligations(submitList);
    }

    public void setEditSelectedDaily(DailyObligation editSelectedDaily) {
        this.editSelectedDaily.setValue(editSelectedDaily);
    }

    public MutableLiveData<DailyObligation> getEditSelectedDaily() {
        return editSelectedDaily;
    }

    public void editObligationInObligations(int id, String obligationName, ObligationType obligationType, LocalDateTime startTime,
                                            LocalDateTime endTime, String obligationContent){

        ArrayList<DailyObligation> obligationArrayList = new ArrayList<>(obligations.getValue());

        for(DailyObligation obligation : obligationArrayList){
            if(obligation.getId() == id){


                boolean flag = true;
                for(DailyObligation o : obligationArrayList){
                    if(!o.equals(obligation) && obligation.getStartTime().isBefore(o.getEndTime()) && obligation.getEndTime().isAfter(o.getStartTime())){
                        flag = false;
                    }
                }

                if(flag){
                    obligation.setObligationName(obligationName);
                    obligation.setObligationType(obligationType);
                    obligation.setObligationContent(obligationContent);
                    obligation.setStartTime(startTime);
                    obligation.setEndTime(endTime);
                    break;
                }

//
//                obligation.setObligationName(obligationName);
//                obligation.setObligationType(obligationType);
//                obligation.setObligationContent(obligationContent);
//                obligation.setStartTime(startTime);
//                obligation.setEndTime(endTime);
//                break;
            }
        }

        ArrayList<DailyObligation> obligationSubmitList = new ArrayList<>(obligationArrayList);
        obligations.setValue(obligationSubmitList);

        System.out.println("Nakon klika na Save u Editu");
        System.out.println(obligations.getValue());

    }
}
