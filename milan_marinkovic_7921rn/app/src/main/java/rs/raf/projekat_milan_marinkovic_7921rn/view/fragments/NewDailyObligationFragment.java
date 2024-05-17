package rs.raf.projekat_milan_marinkovic_7921rn.view.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.models.ObligationType;
import rs.raf.projekat_milan_marinkovic_7921rn.viewmodels.CalendarViewModel;

public class NewDailyObligationFragment extends Fragment {

    private CalendarViewModel calendarViewModel;

    private Button createButtonLow;
    private Button createButtonMid;
    private Button createButtonHigh;
    private EditText createTimeEt;
    private EditText createNameEt;

    private Button createCreateButton;
    private Button createCancelButton;

    private String obligationName;
    private ObligationType obligationType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String obligationContent;

    private EditText createContentEt;
    private TextView createDayTimeView;
    private String stringDate;


    public NewDailyObligationFragment(){
        super(R.layout.fragment_create_daily_obligation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        initView(view);
        initEditTvDayMonthYear();
        initEditText();
        initListeners();
    }

    private void initView(View view){
        createDayTimeView = view.findViewById(R.id.createDayTimeView);
        createButtonLow = (Button)view.findViewById(R.id.createButtonLow);
        createButtonMid = (Button) view.findViewById(R.id.createButtonMid);
        createButtonHigh = (Button)view.findViewById(R.id.createButtonHigh);
        createTimeEt = (EditText) view.findViewById(R.id.createDailyTimeEt);
        createNameEt = (EditText) view.findViewById(R.id.createDailyObligationNameEt);
        createContentEt = (EditText) view.findViewById(R.id.createDailyContentEt);
        createCreateButton = (Button) view.findViewById(R.id.createCreateButton);
        createCancelButton = (Button)view.findViewById(R.id.createCancelButton);
    }

    private void initEditTvDayMonthYear(){
        DateTimeFormatter dayMonthTimeYearFormatter =  DateTimeFormatter.ofPattern("MMMM dd. yyyy");
        String dmyFormattedString = calendarViewModel.getSelectedDay().getValue().getDate().format(dayMonthTimeYearFormatter);
        createDayTimeView.setText(dmyFormattedString);
    }


    private void initEditText(){

        createNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                obligationName = createNameEt.getText().toString();
            }
        });

        createTimeEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                stringDate = createTimeEt.getText().toString().trim();
            }
        });

        createContentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                obligationContent = createContentEt.getText().toString();
            }
        });
    }

    private void initListeners(){
        createButtonLow.setOnClickListener(v -> {
            obligationType = ObligationType.LOW;
        });
        createButtonMid.setOnClickListener(v -> {
            obligationType = ObligationType.MID;
        });
        createButtonHigh.setOnClickListener(v -> {
            obligationType = ObligationType.HIGH;
        });
        createCancelButton.setOnClickListener(v -> {
//            getActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.mainFcv, new BottomNavigationFragment())
//                    .addToBackStack(null)
//                    .commit();
        });
        createCreateButton.setOnClickListener(v -> {

            obligationContent = createContentEt.getText().toString();
            obligationName = createNameEt.getText().toString();

            // String koji predstavlja vreme u formatu koji odgovara formatter-u
            //String vremeString = createTimeEt.getText().toString().trim();
            String vremeString = stringDate;
            System.out.println(stringDate);
            String[] vremeNiz = vremeString.split("-");

            String startString = vremeNiz[0].trim();
            String endString = vremeNiz[1].trim();

            String[] startNiz = startString.split(":");
            String[] endNiz = endString.split(":");

            //LocalDateTime startTime = LocalDateTime.of()
            int yearStart = calendarViewModel.getSelectedDay().getValue().getDate().getYear();
            int monthStart = calendarViewModel.getSelectedDay().getValue().getDate().getMonth().getValue();
            int dayStart = calendarViewModel.getSelectedDay().getValue().getDate().getDayOfMonth();
            int hourStart = Integer.parseInt(startNiz[0]);
            int minuteStart = Integer.parseInt(startNiz[1]);
            int secondStart = 0;

            startTime = LocalDateTime.of(yearStart, monthStart, dayStart, hourStart, minuteStart, secondStart);

            int yearEnd = calendarViewModel.getSelectedDay().getValue().getDate().getYear();
            int monthEnd = calendarViewModel.getSelectedDay().getValue().getDate().getMonth().getValue();
            int dayEnd = calendarViewModel.getSelectedDay().getValue().getDate().getDayOfMonth();
            int hourEnd = Integer.parseInt(endNiz[0]);
            int minuteEnd = Integer.parseInt(endNiz[1]);
            int secondEnd = 0;

            endTime = LocalDateTime.of(yearEnd, monthEnd, dayEnd, hourEnd, minuteEnd, secondEnd);

            if(obligationName != null && obligationContent != null && obligationType != null
                    && startTime != null && endTime != null) {

                boolean isCreated = calendarViewModel.addObligationToObligations(obligationName, obligationType, startTime,
                        endTime, obligationContent);
                if(!isCreated){
                    Toast.makeText(requireActivity(), "Niste kreirali novu obavezu, poklapa vam se postojecim!\nMolimo unesite drugacije vreme obaveze",
                            Toast.LENGTH_SHORT).show();
                }else{
                    //TODO nakon uspesnog kreiranja
                }
            }
        });
    }

}
