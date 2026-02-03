package com.example.demobase.controller;

import com.example.demobase.dto.GameDTO;
import com.example.demobase.dto.GameResponseDTO;
import com.example.demobase.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testStartGame() throws Exception {
        // Given
        GameResponseDTO response = new GameResponseDTO();
        response.setPalabraOculta("___________");
        response.setLetrasIntentadas(Arrays.asList());
        response.setIntentosRestantes(7);
        response.setPalabraCompleta(false);
        response.setPuntajeAcumulado(0);
        when(gameService.startGame(1L)).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/games/start/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.intentosRestantes").value(7))
                .andExpect(jsonPath("$.palabraCompleta").value(false))
                .andExpect(jsonPath("$.puntajeAcumulado").value(0));

        verify(gameService, times(1)).startGame(1L);
    }

    @Test
    void testMakeGuess() throws Exception {
        // Given
        GameResponseDTO response = new GameResponseDTO();
        response.setPalabraOculta("P__________");
        response.setLetrasIntentadas(Arrays.asList('P'));
        response.setIntentosRestantes(7);
        response.setPalabraCompleta(false);
        response.setPuntajeAcumulado(0);
        
        Map<String, Object> request = new HashMap<>();
        request.put("idJugador", 1);
        request.put("letra", "P");
        
        when(gameService.makeGuess(eq(1L), eq('P'))).thenReturn(response);

        // When & Then
        mockMvc.perform(post("/api/games/guess")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.intentosRestantes").value(7))
                .andExpect(jsonPath("$.letrasIntentadas[0]").value("P"));

        verify(gameService, times(1)).makeGuess(eq(1L), eq('P'));
    }

    @Test
    void testGetAllGames() throws Exception {
        // Given
        GameDTO game1 = new GameDTO();
        game1.setId(1L);
        game1.setIdJugador(1L);
        game1.setNombreJugador("Juan Pérez");
        game1.setResultado("GANADO");
        game1.setPuntaje(20);
        game1.setFechaPartida(LocalDateTime.now());
        game1.setPalabra("PROGRAMADOR");

        List<GameDTO> games = Arrays.asList(game1);
        when(gameService.getAllGames()).thenReturn(games);

        // When & Then
        mockMvc.perform(get("/api/games"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].resultado").value("GANADO"))
                .andExpect(jsonPath("$[0].puntaje").value(20));

        verify(gameService, times(1)).getAllGames();
    }

    @Test
    void testGetGamesByPlayer() throws Exception {
        // Given
        GameDTO game1 = new GameDTO();
        game1.setId(1L);
        game1.setIdJugador(1L);
        game1.setNombreJugador("Juan Pérez");
        game1.setResultado("GANADO");
        game1.setPuntaje(20);

        List<GameDTO> games = Arrays.asList(game1);
        when(gameService.getGamesByPlayer(1L)).thenReturn(games);

        // When & Then
        mockMvc.perform(get("/api/games/player/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].idJugador").value(1));

        verify(gameService, times(1)).getGamesByPlayer(1L);
    }
}

