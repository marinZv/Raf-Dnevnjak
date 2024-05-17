package rs.raf.projekat_milan_marinkovic_7921rn.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager.DailyDetailAdapter;
import rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager.DailyDiffItemCallback;
import rs.raf.projekat_milan_marinkovic_7921rn.viewmodels.CalendarViewModel;

public class DetailsDailyObligationFragment extends Fragment {

    private ConstraintLayout dailyDetailContainer;
    private CalendarViewModel calendarViewModel;
    private RecyclerView recyclerView;
    private DailyDetailAdapter dailyDetailAdapter;

    public DetailsDailyObligationFragment(){
        super(R.layout.fragment_daily_detail);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        dailyDetailContainer = view.findViewById(R.id.dailyDetailContainer);

        recyclerView = view.findViewById(R.id.dailyDetailRecycler);

        initRecycler();
        initObserver();
    }

    private void initRecycler(){
        dailyDetailAdapter = new DailyDetailAdapter(new DailyDiffItemCallback(), daily -> {

        }, calendarViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(dailyDetailAdapter);
    }

    private void initObserver(){
        calendarViewModel.getObligations().observe(requireActivity(), obligations -> {
            dailyDetailAdapter.submitList(obligations);
            dailyDetailAdapter.notifyDataSetChanged();
        });
    }
}
