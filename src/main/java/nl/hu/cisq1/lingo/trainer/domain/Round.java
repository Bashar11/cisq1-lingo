package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundFinishedException;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "round")
public class Round implements Serializable {

    @Column
    private String wordToGuess;
    @Column
    private int attempts;

    @OneToMany
    @JoinColumn
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private final List<Feedback> feedbacks = new ArrayList<>();
    @Column
    private String lastHint;
    @Id
    @GeneratedValue()
    private Long id;

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.lastHint = giveHintAtBegin();
    }


    public Integer getWordLength() {
        return getWordToGuess().length();
    }

    public boolean isRoundFinished() {
        return attempts == 5 || feedbacks.stream().anyMatch(Feedback::isWordGuessed);
    }


    public Feedback getLastFeedback() {
        Feedback feedback = new Feedback();
        if (feedbacks.size() == 1) {
            feedback = feedbacks.get(0);
        } else if (!feedbacks.isEmpty()) {
            feedback = feedbacks.get(feedbacks.size() - 1);

        }
        return feedback;
    }

    public boolean isWon() {
        return getLastFeedback().isWordGuessed();
    }


    public void guess(String attempt) {
        if (isRoundFinished()) {
            throw new RoundFinishedException();
        }
        Feedback feedback = Feedback.basedOn(this.wordToGuess, attempt);

        this.feedbacks.add(feedback);
        this.attempts += 1;
        this.lastHint = feedback.giveHint(attempt, this.lastHint);


    }

    public String giveHintAtBegin() {
        List<String> hints = new ArrayList<>();
        String[] splitWord = wordToGuess.split("");

        hints.add(splitWord[0]);
        for (int i = 1; i < splitWord.length; i++) {
            hints.add(".");
        }

        return String.join("", hints);
    }


}
