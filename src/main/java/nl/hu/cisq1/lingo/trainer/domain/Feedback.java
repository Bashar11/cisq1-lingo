package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Feedback {

    private boolean wordIsGuessed = false ;
    private String attempt;
    private List<Mark> marks;
    List<String> hints = new ArrayList<>();


    public Feedback( String attempt, List<Mark>marks){
        if (attempt.length() != marks.size()) {
            throw new InvalidFeedbackException(attempt.length(), marks.size());
        }
            this.attempt = attempt;
            this.marks= marks;


    }

    public Feedback(){}



    public boolean wordCorrect(){
        for (Mark mark : marks){
            if(mark != Mark.Correct){
                return wordIsGuessed;
            }
        }
        return wordIsGuessed = true;
    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(Mark.Correct::equals);
    }


    public boolean guessValid() {
        return marks.stream().noneMatch(mark -> mark == Mark.Invalid);
    }

    public boolean guessInValid() {
        return !guessValid();
       // return marks.stream().allMatch(mark -> mark == Mark.Invalid);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "wordIsGuessed=" + wordIsGuessed +
                ", attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return wordIsGuessed == feedback.wordIsGuessed && Objects.equals(attempt, feedback.attempt) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(wordIsGuessed, attempt, marks);
    }



    public String giveHint(String wordToGuess,String previousHint){

        String [] splittedWord = wordToGuess.split("");
        String [] splittedPreviousHint = previousHint.split("");


        for(int i = 0; i < splittedWord.length; i++)

            if (!(marks.get(i) == Mark.Correct))
                hints.add(splittedPreviousHint[i]);

             else if (marks.get(i) == Mark.Correct)
                hints.add(splittedWord[i]);

             else
                hints.add(".");


        return String.join("",hints);
    }

    public static List<Mark> rightWord(String wordToGuess, String word) {
        List<Mark> rightWord = new ArrayList<>();

        if ((word.length() == wordToGuess.length())) {
            for (int i = 0; i < wordToGuess.length(); i++) {
                if (wordToGuess.charAt(i) == word.charAt(i)) {
                    rightWord.add(Mark.Correct);
                } else if ((wordToGuess.charAt(i) != word.charAt(i))
                        && wordToGuess.indexOf(word.charAt(i)) != -1)
                    rightWord.add(Mark.Present);
                else if (wordToGuess.charAt(i) != word.charAt(i) && wordToGuess.indexOf(word.charAt(i)) == -1) {
                    rightWord.add(Mark.Absent);

                } else {
                    rightWord.add(Mark.Invalid);

                }

            }}

        return rightWord;

    }











}
