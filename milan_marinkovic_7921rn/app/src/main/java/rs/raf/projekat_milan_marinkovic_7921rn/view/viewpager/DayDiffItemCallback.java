package rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat_milan_marinkovic_7921rn.models.Day;

public class DayDiffItemCallback extends DiffUtil.ItemCallback<Day> {


    @Override
    public boolean areItemsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Day oldItem, @NonNull Day newItem) {
        return oldItem.getDate().equals(newItem.getDate());
    }
}
