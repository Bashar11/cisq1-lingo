package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Progress {
    private double score;
    private int roundNumber;
    private String lastHint;
    private final List<Feedback>feedbacks = new ArrayList<>();

    public Progress(double score, List<Feedback> feedbacks, String lastHint) {
    }
}
