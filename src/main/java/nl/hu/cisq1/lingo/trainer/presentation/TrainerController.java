package nl.hu.cisq1.lingo.trainer.presentation;


import nl.hu.cisq1.lingo.trainer.application.TrainerGameService;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lingo/game")
public class TrainerController {

    private final TrainerGameService gameService;

    public TrainerController(TrainerGameService gameService) {
        this.gameService = gameService;
    }
    @PostMapping()
    public GameDto startGame(){
        LingoGame game = this.gameService.startGame();
        return createGameId(game);
    }
    @PostMapping("/{id}/guess")
    public GameDto makeGuess(@PathVariable(value = "id") Long id, String wordToGuess){
        LingoGame game = this.gameService.makeGuess(id,wordToGuess);
        return guessingWord(game) ;
    }

    @PostMapping("{id}/newRound")
    public GameDto newRound(@PathVariable(value = "id") Long id){
         LingoGame game = this.gameService.newRound(id);


        return creatingRound(game);
    }




    private GameDto createGameId(LingoGame lingoGame){
        return new GameDto(
                lingoGame.getId());
    }

    private GameDto guessingWord(LingoGame lingoGame){
        return new GameDto(
                lingoGame.getId(),
                lingoGame.getWordToGuess());
    }

    private GameDto creatingRound(LingoGame lingoGame){
        return new GameDto(lingoGame.getLastRound().getId());
    }

}
