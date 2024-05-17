package rs.raf.projekat_milan_marinkovic_7921rn.view.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.models.Day;
import rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager.DayAdapter;
import rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager.DayDiffItemCallback;
import rs.raf.projekat_milan_marinkovic_7921rn.viewmodels.CalendarViewModel;
import rs.raf.projekat_milan_marinkovic_7921rn.viewmodels.DailyViewModel;

public class CalendarFragment extends Fragment {

    private CalendarViewModel calendarViewModel;
    private RecyclerView recyclerView;
    private DayAdapter dayAdapter;
    private LinearLayout calendarContainer;
    private DailyViewModel dailyViewModel;
    private TextView monthYearTv;

    public CalendarFragment(){
        super(R.layout.fragment_calendar);
        System.out.println("Pozvao sam se u CalendarFragmenut");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        calendarViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);

        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);

        calendarContainer = view.findViewById(R.id.calendarContainer);
        recyclerView = view.findViewById(R.id.calendarRecycler);

        initTv(view);
        initObserver();
        initRecycler(view);
    }

    private void initObserver(){
        calendarViewModel.getDays().observe(this, days -> {
            dayAdapter.submitList(days);
            dayAdapter.notifyDataSetChanged();
        });
    }

    private void initTv(View view){
        monthYearTv = view.findViewById(R.id.monthYearView);
    }

    private void initRecycler(View view){
        dayAdapter = new DayAdapter(new DayDiffItemCallback(), day -> {

            System.out.println("Kliknuo sam na dan " + day.getDate());
            //System.out.println(day.getObligations().getValue());
            System.out.println("Sa listom obaveza + " + day.getObligationList());

            calendarViewModel.setSelectedDay(day);
            calendarViewModel.setObligations(day.getObligationList());
            getActivity().findViewById(R.id.bottomNavigation).findViewById(R.id.navigation_2).performClick();

        });

        LocalDate dateForTv = null;
        int firstPosition = 0;
        List<Day> tmpList = calendarViewModel.getDays().getValue();
        for(Day t: tmpList){
            if(t.getDate().getMonth() == LocalDate.now().getMonth()){
                dateForTv = t.getDate();
                break;
            }
            firstPosition++;
        }


        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 7));
        recyclerView.setAdapter(dayAdapter);
        recyclerView.scrollToPosition(firstPosition);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        // Formatiranje LocalDate objekta u string
        String formattedString = dateForTv.format(formatter);
        monthYearTv.setText(formattedString);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //System.out.println(dy);
                int firstVisibleItemPosition = ((GridLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                //System.out.println(firstVisibleItemPosition);
                //System.out.println( calendarViewModel.getDays().getValue().get(firstVisibleItemPosition).getDate());
                int counter = 1;
                LocalDate date = null;
                for(int i = firstVisibleItemPosition, j = i + 1; i < firstVisibleItemPosition + 42; i++, j++){
                    if(calendarViewModel.getDays().getValue().get(i).getDate().getMonth().equals(calendarViewModel.getDays().getValue().get(j).getDate().getMonth())){
                        counter++;
                    }else{
                        if(counter >= calendarViewModel.getDays().getValue().get(i).getDate().lengthOfMonth()){
                            date = calendarViewModel.getDays().getValue().get(i).getDate();
                            break;
                        }else{
                            counter = 1;
                        }

                    }
                }


                if(date != null){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
                    // Formatiranje LocalDate objekta u string
                    String formattedString = date.format(formatter);
                    monthYearTv.setText(formattedString);
                }

            }
        });
    }
}
