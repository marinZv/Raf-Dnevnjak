package rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.models.DailyObligation;
import rs.raf.projekat_milan_marinkovic_7921rn.view.fragments.EditDailyObligationFragment;
import rs.raf.projekat_milan_marinkovic_7921rn.viewmodels.CalendarViewModel;

public class DailyDetailAdapter extends ListAdapter<DailyObligation, DailyDetailAdapter.ViewHolder> {

    private Consumer<DailyObligation> onDailyItemClicked;
    private CalendarViewModel calendarViewModel;


    public DailyDetailAdapter(@NonNull DiffUtil.ItemCallback<DailyObligation> diffCalback, Consumer<DailyObligation> onDailyItemClicked, CalendarViewModel calendarViewModel){
        super(diffCalback);
        this.onDailyItemClicked = onDailyItemClicked;
        this.calendarViewModel = calendarViewModel;
    }

    @NonNull
    @Override
    public DailyDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_detail_item, parent, false);

        return new DailyDetailAdapter.ViewHolder(view, parent.getContext(), position -> {
            DailyObligation dailyObligation = getItem(position);
            onDailyItemClicked.accept(dailyObligation);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull DailyDetailAdapter.ViewHolder holder, int position) {
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


            TextView detailDayMonthYearTv = (TextView) itemView.findViewById(R.id.detailDayMonthYearTv);
            TextView detailDailyTimeTv = (TextView) itemView.findViewById(R.id.detailDailyTimeTv);
            TextView detailDailyNameTv = (TextView) itemView.findViewById(R.id.detailDailyNameTv);
            TextView detailDailyContentTv = (TextView) itemView.findViewById(R.id.detailDailyContentTv);
            Button editDetailButton = itemView.findViewById(R.id.detailEditButton);
            Button deleteDetailButton = itemView.findViewById(R.id.detailDeleteButton);


            DateTimeFormatter dayMonthTimeYearFormatter =  DateTimeFormatter.ofPattern("MMMM dd. yyyy.");
            String dmyFormattedString =dailyObligation.getStartTime().format(dayMonthTimeYearFormatter);
            detailDayMonthYearTv.setText(dmyFormattedString);

            StringBuilder stringBuilder = new StringBuilder();
            DateTimeFormatter dayHourMinuteFormatter =  DateTimeFormatter.ofPattern("HH:mm");
            String formattedStringStart =dailyObligation.getStartTime().format(dayHourMinuteFormatter);
            String formattedStringEnd = dailyObligation.getEndTime().format(dayHourMinuteFormatter);

            stringBuilder.append(formattedStringStart);
            stringBuilder.append("-");
            stringBuilder.append(formattedStringEnd);

            detailDailyTimeTv.setText(stringBuilder.toString());
            detailDailyContentTv.setText(dailyObligation.getObligationContent());
            detailDailyNameTv.setText(dailyObligation.getObligationName());

            editDetailButton.setOnClickListener(v -> {
                calendarViewModel.setEditSelectedDaily(dailyObligation);
                Fragment fragment = new EditDailyObligationFragment();
                ((FragmentActivity)v.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainFcv, fragment)
                        .addToBackStack(null)
                        .commit();
            });
            deleteDetailButton.setOnClickListener(v -> {
                calendarViewModel.removeObligationFromObligations(dailyObligation.getId());
            });

        }
    }

}
