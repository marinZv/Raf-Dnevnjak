package rs.raf.projekat_milan_marinkovic_7921rn.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.models.ObligationType;
import rs.raf.projekat_milan_marinkovic_7921rn.viewmodels.CalendarViewModel;

public class EditDailyObligationFragment extends Fragment {

    private CalendarViewModel calendarViewModel;

    private Button editButtonLow;
    private Button editButtonMid;
    private Button editButtonHigh;
    private EditText editTimeEt;
    private EditText editNameEt;

    private Button editSaveButton;
    private Button editCancelButton;

    private String obligationName;
    private ObligationType obligationType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String obligationContent;

    private EditText editContentEt;
    private TextView editDayTimeView;

    public EditDailyObligationFragment(){
        super(R.layout.fragment_edit_daily_obligation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        initView(view);
        initEditText();
        initListenerOnButtons();
    }

    private void initView(View view){
        editDayTimeView = view.findViewById(R.id.editDayTimeView);
        editButtonLow = (Button)view.findViewById(R.id.editButtonLow);
        editButtonMid = (Button) view.findViewById(R.id.editButtonMid);
        editButtonHigh = (Button)view.findViewById(R.id.editButtonHigh);
        editTimeEt = (EditText) view.findViewById(R.id.editDailyTimeEt);
        editNameEt = (EditText) view.findViewById(R.id.editDailyObligationNameEt);
        editContentEt = (EditText) view.findViewById(R.id.editDailyContentEt);
        editSaveButton = (Button) view.findViewById(R.id.editSaveButton);
        editCancelButton = (Button)view.findViewById(R.id.editCancelButton);
    }

    private void initEditText(){

        editNameEt.setText(calendarViewModel.getEditSelectedDaily().getValue().getObligationName());
        StringBuilder stringBuilder = new StringBuilder();

        DateTimeFormatter dayMonthTimeYearFormatter =  DateTimeFormatter.ofPattern("MMMM dd. yyyy");
        String dmyFormattedString = calendarViewModel.getEditSelectedDaily().getValue().getStartTime().format(dayMonthTimeYearFormatter);
        editDayTimeView.setText(dmyFormattedString);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // Formatirajte LocalDateTime objekat u string koristeći DateTimeFormatter
        String startFormattedString = calendarViewModel.getEditSelectedDaily().getValue().getStartTime().format(formatter);
        String endFormattedString = calendarViewModel.getEditSelectedDaily().getValue().getEndTime().format(formatter);
        stringBuilder.append(startFormattedString);
        stringBuilder.append("-");
        stringBuilder.append(endFormattedString);
        editTimeEt.setText(stringBuilder.toString());
        editContentEt.setText(calendarViewModel.getEditSelectedDaily().getValue().getObligationContent());
    }

    private void initListenerOnButtons(){
        editButtonLow.setOnClickListener(v -> {
            obligationType = ObligationType.LOW;
        });
        editButtonMid.setOnClickListener(v -> {
            obligationType = ObligationType.MID;
        });
        editButtonLow.setOnClickListener(v -> {
            obligationType = ObligationType.HIGH;
        });
        editSaveButton.setOnClickListener(v -> {
            //System.out.println(editNameEt.getText().toString());

            obligationContent = editContentEt.getText().toString();
            obligationName = editNameEt.getText().toString();

            // String koji predstavlja vreme u formatu koji odgovara formatter-u
            String vremeString = editTimeEt.getText().toString().trim();
            String[] vremeNiz = vremeString.split("-");

            String startString = vremeNiz[0].trim();
            String endString = vremeNiz[1].trim();

            String[] startNiz = startString.split(":");
            String[] endNiz = endString.split(":");

            //LocalDateTime startTime = LocalDateTime.of()
            int yearStart = calendarViewModel.getEditSelectedDaily().getValue().getStartTime().getYear();
            int monthStart = calendarViewModel.getEditSelectedDaily().getValue().getStartTime().getMonth().getValue();
            int dayStart = calendarViewModel.getEditSelectedDaily().getValue().getStartTime().getDayOfMonth();
            int hourStart = Integer.parseInt(startNiz[0]);
            int minuteStart = Integer.parseInt(startNiz[1]);
            int secondStart = 0;

            startTime = LocalDateTime.of(yearStart, monthStart, dayStart, hourStart, minuteStart, secondStart);

            int yearEnd = calendarViewModel.getEditSelectedDaily().getValue().getStartTime().getYear();
            int monthEnd = calendarViewModel.getEditSelectedDaily().getValue().getStartTime().getMonth().getValue();
            int dayEnd = calendarViewModel.getEditSelectedDaily().getValue().getStartTime().getDayOfMonth();
            int hourEnd = Integer.parseInt(endNiz[0]);
            int minuteEnd = Integer.parseInt(endNiz[1]);
            int secondEnd = 0;

            endTime = LocalDateTime.of(yearEnd, monthEnd, dayEnd, hourEnd, minuteEnd, secondEnd);

            if(obligationName != null && obligationContent != null && obligationType != null
                && startTime != null && endTime != null) {

                int id = calendarViewModel.getEditSelectedDaily().getValue().getId();
                calendarViewModel.editObligationInObligations(id, obligationName, obligationType, startTime, endTime,obligationContent);
            }

            //getActivity().findViewById(R.id.bottomNavigation).findViewById(R.id.navigation_2).performClick();
            //getActivity().getSupportFragmentManager().popBackStack();

//
//            getActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.mainFcv, new BottomNavigationFragment())
//                    .addToBackStack(null)
//                    .commit();
        });
    }

}
