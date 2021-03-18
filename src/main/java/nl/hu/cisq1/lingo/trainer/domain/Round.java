package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "round")
public class Round implements Serializable {

    @Column
    private String wordToGuess;
    @Column
    private int attempts;
    @ManyToOne
    private LingoGame lingo;
    @OneToMany(mappedBy = "round")
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
    public Round(){}

    public int getLength(){
        String[] splitWord = this.wordToGuess.split("");
        return splitWord.length;
    }

    public boolean isRoundFinished() {
        return (feedbacks.size() == 5 && !getLastFeedback().isWordGuessed()) || getLastFeedback().isWordGuessed(); }


    public Feedback getLastFeedback(){
        Feedback feedback = new Feedback();
        if(feedbacks.size() == 1){
            feedback = feedbacks.get(0);
        }else if(!feedbacks.isEmpty()){
            feedback = feedbacks.get(feedbacks.size() - 1);

        }
        return feedback;
    }


    public void getFeedbacks(String attempt, List<Mark> marks) {
        Feedback feedback = new Feedback(attempt, marks);
        feedbacks.add(feedback);
    }

    public void guess(String attempt) {
            Feedback feedback = Feedback.basedOn(this.wordToGuess,attempt);

            this.feedbacks.add(feedback);
            this.attempts += 1;
            this.lastHint = feedback.giveHint(attempt, this.wordToGuess);

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
