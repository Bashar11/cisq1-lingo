package nl.hu.cisq1.lingo.trainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.TrainerRepository;
import nl.hu.cisq1.lingo.trainer.presentation.AttemptDto;
import nl.hu.cisq1.lingo.trainer.presentation.TrainerController;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@Import(CiTestConfiguration.class)
public class ControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    WordService wordService;

    @Autowired
    TrainerController controller;
    private Long gameId;

    @Autowired
    TrainerRepository repository;

    @BeforeEach
    void beforeAll() {

        gameId = controller.startGame().getId();
    }


    @Test
    @DisplayName("a game can be started and it has an ID")
    void newGame() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.score", is(0.0)))
                .andExpect(content().string(containsString("\"state\":\"STARTING\"")));
        assertNotEquals(gameId, null);
    }

    @Test
    @DisplayName("new round can be started")
    void newRoundValid() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game/" + gameId + "/round");
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("cannot start new round if game not found")
    void cannotStartRound() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game/0/round");
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }




    @Test
    @DisplayName("a new Round cannot be started when the current round is running")
    void newRoundAlreadyActive() throws Exception {
        controller.newRound(gameId);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game/" + gameId + "/round");
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("guess a word after starting a game")
    void guessWord() throws Exception {
        RequestBuilder newGameRequest = MockMvcRequestBuilders
                .post("/lingo/game");
        MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
        Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");
        AttemptDto guess = new AttemptDto();
        guess.attempt = "knoop";

//         JSON genereren van AttemptDto object en deze als een string returnen
        String guessBody = new ObjectMapper().writeValueAsString(guess);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game/" + gameId + "/round");
        mockMvc.perform(request)
                .andExpect(status().isOk());
        RequestBuilder guessRequest = MockMvcRequestBuilders
                .post("/lingo/game/" + gameId + "/guess")
                .contentType(MediaType.APPLICATION_JSON)

                .content(guessBody);
        mockMvc.perform(guessRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attempts", is(1)))
                .andExpect(jsonPath("$.id", greaterThanOrEqualTo(0)))
                .andExpect(jsonPath("$.feedbacks", hasSize(1)));
    }

    @Test
    @DisplayName("Making a guess on not existed game")
    void guessWordOnNotExistedGame() throws Exception {
        RequestBuilder newGameRequest = MockMvcRequestBuilders
                .post("/lingo/game");
        MockHttpServletResponse response = mockMvc.perform(newGameRequest).andReturn().getResponse();
        Integer gameId = JsonPath.read(response.getContentAsString(), "$.id");
        AttemptDto guess = new AttemptDto();
        guess.attempt = "knoop";

//         JSON genereren van AttemptDto object en deze als een string returnen
        String guessBody = new ObjectMapper().writeValueAsString(guess);
        RequestBuilder request = MockMvcRequestBuilders
                .post("/lingo/game/" + gameId + "/round");
        mockMvc.perform(request)
                .andExpect(status().isOk());
        RequestBuilder guessRequest = MockMvcRequestBuilders
                .post("/lingo/game/0/guess")
                .contentType(MediaType.APPLICATION_JSON)

                .content(guessBody);
        mockMvc.perform(guessRequest)
                .andExpect(status().isNotFound());

    }




}
