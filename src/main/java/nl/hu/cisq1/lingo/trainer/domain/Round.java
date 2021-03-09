package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.Setter;
import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter

public class Round {


    private int currentRound;
    private Word word;
    private String wordToGuess;
    private int attempt;
    private Feedback feedback;
    List<Feedback> feedbacks = new ArrayList<>();
    List<String> hints = new ArrayList<>();
    private int wordLength;
    private String lastHint;


    public Round(Word word){
        this.word = word;
    }
    public Round(String wordToGuess, Feedback feedback) {

        this.wordToGuess= wordToGuess;
        this.feedback = feedback;
        this.lastHint = giveHintAtBegin();

    }
    public Round(){}

    public Round(String wordToGuess) {

        this.wordToGuess = wordToGuess;
    }



    public boolean increaseRound() {
        if (feedbacks.stream().anyMatch(Feedback::isWordGuessed)) {
            this.currentRound +=1;
            this.attempt = 1;
            return true;
        }
        return false;
    }

    public boolean increaseAttempt(Integer amount) {
        if (!roundFinished()) {
            if (feedbacks.stream().noneMatch(Feedback::isWordGuessed)) {
                this.attempt += amount;
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean roundFinished() {
        if (feedbacks.size() >= 5 || feedbacks.stream().anyMatch(Feedback::isWordGuessed)) {
            return true;
        }
        return false;
    }


//    public List<Feedback> getFeedback(String attempt, List<Mark> marks) {
//        Feedback feedback = new Feedback(attempt, marks);
//        feedbacks.add(feedback);
//        if (!roundFinished()) {
//            increaseAttempt(1);
//        }
//        return feedbacks;
//
//    }








    public void getFeedbacks(String attempt, List<Mark>marks){
        Feedback feedback = new Feedback(attempt, marks);
         feedbacks.add(feedback);
        }

    public String guess(String attempt){
        giveHintAtBegin();
        Feedback feedback = new Feedback(attempt, Feedback.rightWord(attempt, this.wordToGuess));
        feedbacks.add(feedback);
        increaseAttempt(1);
        roundFinished();
        return this.lastHint = feedback.giveHint(attempt, this.wordToGuess);

    }

//    public void guessWord(String guess) {
//        if (attempt == 5) {
//            throw new RuntimeException("sada");
//        }
//
//        if (guess.length() ==(this.word.getLength())) {
//            List<Mark> marks = Feedback.rightWord(guess,this.wordToGuess);
//            Feedback fb = new Feedback(guess, marks);
//            this.feedbacks.add(fb);
//            this.attempt++;
//        } else {
//            throw new RuntimeException("asd");
//        }
//    }




    public String giveHintAtBegin(){
        List<String> hintss = new ArrayList<>();

        String [] splittedWord = wordToGuess.split("");

        for(int i = 0; i < splittedWord.length; i++){
            if (i == 0){
                hintss.add(splittedWord[0]);
            }else {
                hintss.add(".");
            }
        }return String.join("",hintss);
    }


}
