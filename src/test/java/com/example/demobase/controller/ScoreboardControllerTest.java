package com.example.demobase.controller;

import com.example.demobase.dto.ScoreboardDTO;
import com.example.demobase.service.ScoreboardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ScoreboardController.class)
class ScoreboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScoreboardService scoreboardService;

    @Test
    void testGetScoreboard() throws Exception {
        // Given
        ScoreboardDTO score1 = new ScoreboardDTO(1L, "Juan Pérez", 45, 3L, 2L, 1L);
        ScoreboardDTO score2 = new ScoreboardDTO(2L, "María García", 20, 1L, 1L, 0L);
        List<ScoreboardDTO> scores = Arrays.asList(score1, score2);
        when(scoreboardService.getScoreboard()).thenReturn(scores);

        // When & Then
        mockMvc.perform(get("/api/scoreboard"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].idJugador").value(1))
                .andExpect(jsonPath("$[0].puntajeTotal").value(45))
                .andExpect(jsonPath("$[1].idJugador").value(2));

        verify(scoreboardService, times(1)).getScoreboard();
    }

    @Test
    void testGetScoreboardByPlayer() throws Exception {
        // Given
        ScoreboardDTO score = new ScoreboardDTO(1L, "Juan Pérez", 45, 3L, 2L, 1L);
        when(scoreboardService.getScoreboardByPlayer(1L)).thenReturn(score);

        // When & Then
        mockMvc.perform(get("/api/scoreboard/player/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idJugador").value(1))
                .andExpect(jsonPath("$.puntajeTotal").value(45))
                .andExpect(jsonPath("$.partidasJugadas").value(3));

        verify(scoreboardService, times(1)).getScoreboardByPlayer(1L);
    }
}

