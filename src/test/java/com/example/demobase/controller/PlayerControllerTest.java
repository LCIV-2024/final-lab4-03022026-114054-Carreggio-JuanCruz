package com.example.demobase.controller;

import com.example.demobase.dto.PlayerDTO;
import com.example.demobase.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllPlayers() throws Exception {
        // Given
        List<PlayerDTO> players = Arrays.asList(
                new PlayerDTO(1L, "Juan Pérez", LocalDate.of(2025, 1, 15)),
                new PlayerDTO(2L, "María García", LocalDate.of(2025, 1, 20))
        );
        when(playerService.getAllPlayers()).thenReturn(players);

        // When & Then
        mockMvc.perform(get("/api/players"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].nombre").value("María García"));

        verify(playerService, times(1)).getAllPlayers();
    }

    @Test
    void testGetPlayerById() throws Exception {
        // Given
        PlayerDTO player = new PlayerDTO(1L, "Juan Pérez", LocalDate.of(2025, 1, 15));
        when(playerService.getPlayerById(1L)).thenReturn(player);

        // When & Then
        mockMvc.perform(get("/api/players/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));

        verify(playerService, times(1)).getPlayerById(1L);
    }

    @Test
    void testCreatePlayer() throws Exception {
        // Given
        PlayerDTO inputDTO = new PlayerDTO(null, "Nuevo Jugador", LocalDate.of(2025, 1, 25));
        PlayerDTO createdDTO = new PlayerDTO(3L, "Nuevo Jugador", LocalDate.of(2025, 1, 25));
        when(playerService.createPlayer(any(PlayerDTO.class))).thenReturn(createdDTO);

        // When & Then
        mockMvc.perform(post("/api/players")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.nombre").value("Nuevo Jugador"));

        verify(playerService, times(1)).createPlayer(any(PlayerDTO.class));
    }

    @Test
    void testUpdatePlayer() throws Exception {
        // Given
        PlayerDTO inputDTO = new PlayerDTO(1L, "Juan Pérez Actualizado", LocalDate.of(2025, 1, 15));
        PlayerDTO updatedDTO = new PlayerDTO(1L, "Juan Pérez Actualizado", LocalDate.of(2025, 1, 15));
        when(playerService.updatePlayer(eq(1L), any(PlayerDTO.class))).thenReturn(updatedDTO);

        // When & Then
        mockMvc.perform(put("/api/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez Actualizado"));

        verify(playerService, times(1)).updatePlayer(eq(1L), any(PlayerDTO.class));
    }

    @Test
    void testDeletePlayer() throws Exception {
        // Given
        doNothing().when(playerService).deletePlayer(1L);

        // When & Then
        mockMvc.perform(delete("/api/players/1"))
                .andExpect(status().isNoContent());

        verify(playerService, times(1)).deletePlayer(1L);
    }
}

