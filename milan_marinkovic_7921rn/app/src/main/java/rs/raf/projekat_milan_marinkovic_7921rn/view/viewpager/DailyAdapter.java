package rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.models.DailyObligation;
import rs.raf.projekat_milan_marinkovic_7921rn.models.ObligationType;
import rs.raf.projekat_milan_marinkovic_7921rn.view.fragments.DetailsDailyObligationFragment;
import rs.raf.projekat_milan_marinkovic_7921rn.view.fragments.EditDailyObligationFragment;
import rs.raf.projekat_milan_marinkovic_7921rn.viewmodels.CalendarViewModel;

public class DailyAdapter extends ListAdapter<DailyObligation, DailyAdapter.ViewHolder> {


    private Consumer<DailyObligation> onDailyItemClicked;
    private CalendarViewModel calendarViewModel;

    public DailyAdapter(@NonNull DiffUtil.ItemCallback<DailyObligation> diffCalback, Consumer<DailyObligation> onDailyItemClicked, CalendarViewModel calendarViewModel){
        super(diffCalback);
        this.onDailyItemClicked = onDailyItemClicked;
        this.calendarViewModel = calendarViewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_list_item, parent, false);

        return new ViewHolder(view, parent.getContext(), position -> {
            DailyObligation dailyObligation = getItem(position);
            onDailyItemClicked.accept(dailyObligation);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyObligation dailyObligation = getItem(position);
        holder.bind(dailyObligation, calendarViewModel);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        public ViewHolder(@NonNull View itemView, Context context, Consumer<Integer> onItemClicked) {
            super(itemView);
            this.context = context;

            itemView.setOnClickListener(v -> {
                if(getBindingAdapterPosition() != RecyclerView.NO_POSITION){
                    onItemClicked.accept(getBindingAdapterPosition());
                }
            });

        }

        public void bind(DailyObligation dailyObligation, CalendarViewModel calendarViewModel){

            ImageView imageView = (ImageView) itemView.findViewById(R.id.daily_image_view);
            if(dailyObligation.getObligationType() == ObligationType.HIGH){
                imageView.setBackgroundResource(R.color.orange);
            }else if(dailyObligation.getObligationType() == ObligationType.MID){
                imageView.setBackgroundResource(R.color.yellow);
            } else if (dailyObligation.getObligationType() == ObligationType.LOW) {
                imageView.setBackgroundResource(R.color.green);
            }


            imageView.setOnClickListener(v -> {
                Fragment fragment = new DetailsDailyObligationFragment();
                ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFcv, fragment)
                        .addToBackStack(null)
                        .commit();
            });

            TextView textViewName = itemView.findViewById(R.id.daily_name_tv);
            textViewName.setText(dailyObligation.getObligationName());

            TextView dailyTime = itemView.findViewById(R.id.daily_time_tv);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dailyObligation.getStartTime().getHour());
            stringBuilder.append(":");
            stringBuilder.append(dailyObligation.getStartTime().getMinute());
            stringBuilder.append(" - ");
            stringBuilder.append(dailyObligation.getEndTime().getHour());
            stringBuilder.append(":");
            stringBuilder.append(dailyObligation.getEndTime().getMinute());

            dailyTime.setText(stringBuilder.toString());

            ImageView deleteObligationButton = (ImageView) itemView.findViewById(R.id.delete_daily_image_view);
            deleteObligationButton.setOnClickListener(v -> {
                calendarViewModel.removeObligationFromObligations(dailyObligation.getId());
            });

            ImageView editObligationButton = (ImageView) itemView.findViewById(R.id.edit_daily_image_view);
            editObligationButton.setOnClickListener(v -> {
                calendarViewModel.setEditSelectedDaily(dailyObligation);
                Fragment fragment = new EditDailyObligationFragment();
                ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFcv, fragment)
                        .addToBackStack(null)
                        .commit();

            });

        }
    }


}
