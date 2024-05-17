package rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import rs.raf.projekat_milan_marinkovic_7921rn.R;
import rs.raf.projekat_milan_marinkovic_7921rn.models.DailyObligation;
import rs.raf.projekat_milan_marinkovic_7921rn.models.Day;
import rs.raf.projekat_milan_marinkovic_7921rn.models.ObligationType;

public class DayAdapter extends ListAdapter<Day, DayAdapter.ViewHolder> {

    private Consumer<Day> onDayClicked;

    public DayAdapter(@NonNull DiffUtil.ItemCallback<Day> diffCallback, Consumer<Day> onDayClicked) {
        super(diffCallback);
        this.onDayClicked = onDayClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);

        return new ViewHolder(view, parent.getContext(), position -> {
            Day day = getItem(position);
            onDayClicked.accept(day);
        });
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Day day = getItem(position);
        holder.bind(day);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

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

        public void bind(Day day){


            ((TextView)itemView.findViewById(R.id.dayTv)).setText(String.valueOf(day.getDate().getDayOfMonth()));

            int[] niz = new int[3];
            for(int i = 0; i < niz.length; i++){
                niz[i] = 0;
            }
            for(DailyObligation obligation : day.getObligationList()){
                if(obligation.getObligationType() == ObligationType.HIGH){
                    niz[2] = 3;
                } else if (obligation.getObligationType() == ObligationType.MID) {
                    niz[1] = 2;
                }else if (obligation.getObligationType() == ObligationType.LOW){
                    niz[0] = 1;
                }
            }

            int max = -1;
            for(int  n : niz){
                if(n > max){
                    max = n;
                }
            }

            ConstraintLayout calendarItem = itemView.findViewById(R.id.calendarItem);
            if(max == 1){
                calendarItem.setBackgroundResource(R.color.yellow);
            }else if(max == 2){
                calendarItem.setBackgroundResource(R.color.yellow);
            } else if (max == 3) {
                calendarItem.setBackgroundResource(R.color.orange);
            }

        }
    }

}
