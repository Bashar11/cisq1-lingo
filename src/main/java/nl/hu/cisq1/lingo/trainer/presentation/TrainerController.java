package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerGameService;
import nl.hu.cisq1.lingo.trainer.domain.LingoGame;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lingo/game")
public class TrainerController {

    private final TrainerGameService gameService;

    public TrainerController(TrainerGameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping()
    public GameDto startGame() {
        LingoGame game = this.gameService.startGame();
        return new GameDto(game);
    }

    @PostMapping("/{id}/guess")
    public GameDto makeGuess(@PathVariable(value = "id") Long id, @RequestBody AttemptDto attemptDto) {
            LingoGame game = this.gameService.makeGuess(id, attemptDto.attempt);
            return new GameDto(game);
    }

    @PostMapping("{id}/round")
    public GameDto newRound(@PathVariable(value = "id") Long id) {
            LingoGame game = this.gameService.newRound(id);
            return new GameDto(game);
    }
}