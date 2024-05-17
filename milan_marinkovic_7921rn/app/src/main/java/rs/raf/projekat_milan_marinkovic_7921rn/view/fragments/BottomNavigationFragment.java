package rs.raf.projekat_milan_marinkovic_7921rn.view.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager.PageAdapter;

public class BottomNavigationFragment extends Fragment {

    public static ViewPager viewPager;
    public PageAdapter pageAdapter;

    public BottomNavigationFragment(){
        super(R.layout.fragment_bottom_navigation);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.viewPager);
        //viewPager.setAdapter(new PageAdapter(requireActivity().getSupportFragmentManager()));
        pageAdapter = new PageAdapter(requireActivity().getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);

        ((BottomNavigationView)view.findViewById(R.id.bottomNavigation)).setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.navigation_1: viewPager.setCurrentItem(PageAdapter.FRAGMENT_CALENDAR, false);
                    System.out.println("pozvao sam se u BottomNavigationFragmentu case1");
                    break;
                case R.id.navigation_2: viewPager.setCurrentItem(PageAdapter.FRAGMENT_DAILY, false);
                    System.out.println("pozvao sam se u BottomNavigationFragmentu case2");
                    break;
                case R.id.navigation_3: viewPager.setCurrentItem(PageAdapter.FRAGMENT_PROFILE, false);
                    System.out.println("pozvao sam se u BottomNavigationFragmentu case3");
                    break;
            }
            return true;

        });

    }

    public void setPageAdapter(PageAdapter pageAdapter) {
        this.pageAdapter = pageAdapter;
        viewPager.setAdapter(this.pageAdapter);
    }
}
