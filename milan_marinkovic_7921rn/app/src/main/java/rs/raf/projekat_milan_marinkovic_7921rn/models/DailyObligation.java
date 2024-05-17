package rs.raf.projekat_milan_marinkovic_7921rn.models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.LocalDateTime;

public class DailyObligation {

    private int id;
    private String obligationName;
    private ObligationType obligationType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String obligationContent;

    public DailyObligation(int id, String obligationName, ObligationType obligationType, LocalDateTime startTime,
                           LocalDateTime endTime, String obligationContent) {
        this.id = id;
        this.obligationName = obligationName;
        this.obligationType = obligationType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.obligationContent = obligationContent;
    }

    public String getObligationName() {
        return obligationName;
    }

    public ObligationType getObligationType() {
        return obligationType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getObligationContent() {
        return obligationContent;
    }

    public void setObligationName(String obligationName) {
        this.obligationName = obligationName;
    }

    public void setObligationType(ObligationType obligationType) {
        this.obligationType = obligationType;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setObligationContent(String obligationContent) {
        this.obligationContent = obligationContent;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id);
        stringBuilder.append(" ");
        stringBuilder.append(obligationName);
        stringBuilder.append(" ");
        stringBuilder.append(getObligationType());
        stringBuilder.append(" ");
        stringBuilder.append(startTime);
        stringBuilder.append(" ");
        stringBuilder.append(endTime);

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj != null && this.id == id){
            return true;
        }
        return false;
    }
}
