package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
    @Column
    private String attempt;
    @ManyToOne
    private Round round;

    @Enumerated
    @ElementCollection(targetClass = Mark.class)
    private List<Mark> marks;
    @Id
    private Long id;
    @Transient
    private boolean wordGuessed;

    public Feedback(String attempt, List<Mark>marks){
        if (attempt.length() != marks.size()) {
            throw new InvalidFeedbackException(attempt.length(), marks.size());
        }
            this.attempt = attempt;
            this.marks = marks;


    }


    public boolean isWordGuessed() {
        return marks.stream().allMatch(Mark.CORRECT::equals);
    }


    public boolean guessValid() {
        return marks.stream().noneMatch(mark -> mark == Mark.INVALID);
    }

    public boolean guessInValid() {
        return !guessValid();
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", round=" + round +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(round, feedback.round) && Objects.equals(id, feedback.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, round, id);
    }

    public String giveHint(String wordToGuess, String previousHint){
        String [] splitWord = wordToGuess.split("");
        String [] splitPreviousHint = previousHint.split("");
        List<String> hints = new ArrayList<>();
        for (int i = 0; i < splitWord.length; i++) {
            if (marks.get(i) != Mark.CORRECT) {
                hints.add(splitPreviousHint[i]);
            } else {
                hints.add(splitWord[i]);
            }
        }

        return String.join("",hints);
    }

    public static Feedback basedOn(String wordToGuess, String attempt) {
        List<Mark> marks = new ArrayList<>();

        if ((attempt.length() == wordToGuess.length())) {
            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuess.charAt(i) == attempt.charAt(i)) {
                    marks.add(Mark.CORRECT);
                } else if ((wordToGuess.charAt(i) != attempt.charAt(i))
                        && wordToGuess.indexOf(attempt.charAt(i)) != -1)
                    marks.add(Mark.PRESENT);
                else if (wordToGuess.charAt(i) != attempt.charAt(i) && wordToGuess.indexOf(attempt.charAt(i)) == -1) {
                    marks.add(Mark.ABSENT);
                } else {
                    marks.add(Mark.INVALID);
                }
            }
        }

        return new Feedback(attempt, marks);
    }



}
