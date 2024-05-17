package rs.raf.projekat_milan_marinkovic_7921rn.view.viewpager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import rs.raf.projekat_milan_marinkovic_7921rn.models.DailyObligation;

public class DailyDiffItemCallback extends DiffUtil.ItemCallback<DailyObligation> {
    @Override
    public boolean areItemsTheSame(@NonNull DailyObligation oldItem, @NonNull DailyObligation newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull DailyObligation oldItem, @NonNull DailyObligation newItem) {
        return (oldItem.getObligationContent().equals(newItem.getObligationContent())
            && oldItem.getStartTime().equals(newItem.getStartTime())
                && oldItem.getEndTime().equals(newItem.getEndTime())
                    && oldItem.getObligationType().equals(newItem.getObligationType()));
    }
}
