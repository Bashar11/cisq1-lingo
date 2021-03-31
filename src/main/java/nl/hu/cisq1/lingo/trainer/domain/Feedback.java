package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.hu.cisq1.lingo.trainer.domain.enums.Mark;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.enums.Mark.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String attempt;

    @Enumerated
    @ElementCollection(targetClass = Mark.class)
    private List<Mark> marks;

    @Transient
    private boolean wordGuessed;

    public Feedback(String attempt, List<Mark> marks) {
        if (attempt.length() != marks.size()) {
            throw new InvalidFeedbackException(attempt.length(), marks.size());
        }
        this.attempt = attempt;
        this.marks = marks;
    }


    public boolean isWordGuessed() {
        return marks.stream().allMatch(CORRECT::equals);
    }


    public boolean guessValid() {
        return marks.stream().noneMatch(mark -> mark == Mark.INVALID);
    }

    public boolean guessInValid() {
        return !guessValid();
    }


    public String giveHint(String attempt, String previousHint) {
        String[] splitWord = attempt.split("");
        String[] splitPreviousHint = previousHint.split("");

        List<String> hints = new ArrayList<>();
        for (int i = 0; i < splitWord.length; i++) {
            if (marks.get(i) != CORRECT) {
                hints.add(splitPreviousHint[i]);
            } else {
                hints.add(splitWord[i]);
            }
        }

        return String.join("", hints);
    }

    public static Feedback basedOn(String wordToGuess, String attempt) {
        if (attempt.length() != wordToGuess.length()) {
            throw new InvalidFeedbackException(attempt.length(), wordToGuess.length());
        }

        List<Mark> marks = new ArrayList<>();
        List<Character> remainingLetters = new ArrayList<>();
        for (int i = 0; i < wordToGuess.length(); i++) {
            Character attemptLetter = attempt.charAt(i);
            Character toGuessLetter = wordToGuess.charAt(i);

            if (attemptLetter.equals(toGuessLetter)) {
                marks.add(CORRECT);
            } else {
                marks.add(null);
                remainingLetters.add(toGuessLetter);
            }
        }

        for (int i = 0; i < wordToGuess.length(); i++) {
            if (CORRECT.equals(marks.get(i))) {
                continue;
            }

            Character attemptLetter = attempt.charAt(i);
            if (remainingLetters.contains(attemptLetter)) {
                remainingLetters.remove(attemptLetter);
                marks.set(i, PRESENT);
            } else {
                marks.set(i, ABSENT);
            }
        }

        return new Feedback(attempt, marks);
    }
}
