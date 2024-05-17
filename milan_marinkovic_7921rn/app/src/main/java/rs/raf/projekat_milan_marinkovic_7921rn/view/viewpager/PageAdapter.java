package rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import rs.raf.projekat_milan_marinkovic_7921rn.view.fragments.CalendarFragment;
import rs.raf.projekat_milan_marinkovic_7921rn.view.fragments.DailyPlanFragment;
import rs.raf.projekat_milan_marinkovic_7921rn.view.fragments.ProfilFragment;

public class PageAdapter extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 3;
    public static final int FRAGMENT_CALENDAR = 0;
    public static final int FRAGMENT_DAILY = 1;
    public static final int FRAGMENT_PROFILE = 2;

    private CalendarFragment calendarFragment;
    private DailyPlanFragment dailyPlanFragment;
    private ProfilFragment profilFragment;



    public PageAdapter(@NonNull FragmentManager fragmentManager){
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case FRAGMENT_CALENDAR: fragment = new CalendarFragment();
                System.out.println("Pozvao sam se u getItemPageAdapter --- CalendarFragment");
                break;
            case FRAGMENT_DAILY: fragment = new DailyPlanFragment();
                System.out.println("Pozvao sam se u getItemPageAdapter --- DailyPlanFragment");
                break;
            case FRAGMENT_PROFILE:fragment = new ProfilFragment();
                System.out.println("Pozvao sam se u getItemPageAdapter --- ProfilFragment");
            default:
                System.out.println("ispisujem se nakon klika na item kalendara");
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    public void setFragmentAtPosition(Fragment fragment, int position) {
        switch (position) {
            case FRAGMENT_CALENDAR:
                calendarFragment = (CalendarFragment) fragment;
                break;
            case FRAGMENT_DAILY:
                dailyPlanFragment = (DailyPlanFragment) fragment;
                break;
            case FRAGMENT_PROFILE:
                profilFragment = (ProfilFragment) fragment;
                break;
        }
        notifyDataSetChanged();
    }

    public CalendarFragment getCalendarFragment() {
        return calendarFragment;
    }

    public DailyPlanFragment getDailyPlanFragment() {
        return dailyPlanFragment;
    }

    public ProfilFragment getProfilFragment() {
        return profilFragment;
    }

    public void setCalendarFragment(CalendarFragment calendarFragment) {
        this.calendarFragment = calendarFragment;
    }

    public void setDailyPlanFragment(DailyPlanFragment dailyPlanFragment) {
        this.dailyPlanFragment = dailyPlanFragment;
    }

    public void setProfilFragment(ProfilFragment profilFragment) {
        this.profilFragment = profilFragment;
    }
}
