package rs.raf.projekat_milan_marinkovic_7921rn.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.models.ObligationType;
import rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager.DailyAdapter;
import rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager.DailyDiffItemCallback;
import rs.raf.projekat_milan_marinkovic_7921rn.viewmodels.CalendarViewModel;

public class DailyPlanFragment extends Fragment {

    private RecyclerView recyclerView;
    private DailyAdapter dailyAdapter;
    private ConstraintLayout dailyContainer;
    private CalendarViewModel calendarViewModel;
    private Button lowSortButton;
    private Button midSortButton;
    private Button highSortButton;
    private SearchView dailyObligationFilterByName;
    private TextView dayMonthYearTv;
    private FloatingActionButton addFloatingButton;


    public DailyPlanFragment(){
        super(R.layout.fragment_daily_plan);
        System.out.println("Pozvao sam se u DailyPlanFragmentu");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);

        dailyContainer = view.findViewById(R.id.dailyContainer);
        recyclerView = view.findViewById(R.id.dailyPlanRecycler);

        initObserver();
        initRecycler();
        initButtonView(view);
        initButtonListeners();
        initEditTextView(view);
        initEditTextListener();
        initDayMonthYearTv(view);


    }

    private void initObserver(){
        calendarViewModel.getObligations().observe(requireActivity(), obligations -> {
            dailyAdapter.submitList(obligations);
            dailyAdapter.notifyDataSetChanged();
        });
    }

    private void initRecycler(){
        dailyAdapter = new DailyAdapter(new DailyDiffItemCallback(), daily -> {
//            Fragment fragment = new DetailsDailyObligationFragment();
//            requireActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.mainFcv, fragment)
//                    .addToBackStack(null)
//                    .commit();
        }, calendarViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(dailyAdapter);
    }

    public void initButtonView(View view){
        lowSortButton = (Button)view.findViewById(R.id.lowSortButton);
        midSortButton = (Button)view.findViewById(R.id.midSortButton);
        highSortButton = (Button)view.findViewById(R.id.highSortButton);
        addFloatingButton = view.findViewById(R.id.addFloatingButton);
    }

    private void initButtonListeners(){
        lowSortButton.setOnClickListener(v -> {
            calendarViewModel.sortObligationsByPriority(ObligationType.HIGH);
        });
        midSortButton.setOnClickListener(v -> {
            calendarViewModel.sortObligationsByPriority(ObligationType.MID);
        });
        highSortButton.setOnClickListener(v -> {
            calendarViewModel.sortObligationsByPriority(ObligationType.LOW);
        });
        addFloatingButton.setOnClickListener(v -> {


            Fragment fragment = new NewDailyObligationFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFcv, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    public CalendarViewModel getCalendarViewModel() {
        return calendarViewModel;
    }


    public void setCalendarViewModel(CalendarViewModel calendarViewModel) {
        this.calendarViewModel = calendarViewModel;
    }

    private void initEditTextView(View view){
        dailyObligationFilterByName = (SearchView) view.findViewById(R.id.dailyObligationFilter);
    }

    private void initDayMonthYearTv(View view){
        dayMonthYearTv = (TextView) view.findViewById(R.id.dayMonthYearTv);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd. yyyy.");
        if(calendarViewModel != null && calendarViewModel.getSelectedDay().getValue() != null){
            String dateString = calendarViewModel.getSelectedDay().getValue().getDate().format(formatter);
            dayMonthYearTv.setText(dateString);
        }

    }

    private void initEditTextListener(){
        dailyObligationFilterByName.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                calendarViewModel.filterObligationsByName(newText.toString());
                return false;
            }
        });
    }
}
