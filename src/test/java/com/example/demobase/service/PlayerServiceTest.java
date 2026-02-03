package com.example.demobase.service;

import com.example.demobase.dto.PlayerDTO;
import com.example.demobase.model.Player;
import com.example.demobase.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    private Player player;
    private PlayerDTO playerDTO;

    @BeforeEach
    void setUp() {
        player = new Player(1L, "Juan Pérez", LocalDate.of(2025, 1, 15));
        playerDTO = new PlayerDTO(1L, "Juan Pérez", LocalDate.of(2025, 1, 15));
    }

    @Test
    void testGetAllPlayers() {
        // Given
        Player player2 = new Player(2L, "María García", LocalDate.of(2025, 1, 20));
        List<Player> players = Arrays.asList(player, player2);
        when(playerRepository.findAll()).thenReturn(players);

        // When
        List<PlayerDTO> result = playerService.getAllPlayers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan Pérez", result.get(0).getNombre());
        assertEquals("María García", result.get(1).getNombre());
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void testGetPlayerById_Success() {
        // Given
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        // When
        PlayerDTO result = playerService.getPlayerById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan Pérez", result.getNombre());
        verify(playerRepository, times(1)).findById(1L);
    }

    @Test
    void testGetPlayerById_NotFound() {
        // Given
        when(playerRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> playerService.getPlayerById(999L));
        verify(playerRepository, times(1)).findById(999L);
    }

    @Test
    void testCreatePlayer_WithDate() {
        // Given
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        // When
        PlayerDTO result = playerService.createPlayer(playerDTO);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Juan Pérez", result.getNombre());
        assertEquals(LocalDate.of(2025, 1, 15), result.getFecha());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testCreatePlayer_WithoutDate() {
        // Given
        PlayerDTO dtoWithoutDate = new PlayerDTO(null, "Nuevo Jugador", null);
        Player savedPlayer = new Player(3L, "Nuevo Jugador", LocalDate.now());
        when(playerRepository.save(any(Player.class))).thenReturn(savedPlayer);

        // When
        PlayerDTO result = playerService.createPlayer(dtoWithoutDate);

        // Then
        assertNotNull(result);
        assertEquals("Nuevo Jugador", result.getNombre());
        assertNotNull(result.getFecha());
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testUpdatePlayer_Success() {
        // Given
        PlayerDTO updatedDTO = new PlayerDTO(1L, "Juan Pérez Actualizado", LocalDate.of(2025, 1, 20));
        Player updatedPlayer = new Player(1L, "Juan Pérez Actualizado", LocalDate.of(2025, 1, 20));
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(playerRepository.save(any(Player.class))).thenReturn(updatedPlayer);

        // When
        PlayerDTO result = playerService.updatePlayer(1L, updatedDTO);

        // Then
        assertNotNull(result);
        assertEquals("Juan Pérez Actualizado", result.getNombre());
        assertEquals(LocalDate.of(2025, 1, 20), result.getFecha());
        verify(playerRepository, times(1)).findById(1L);
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void testUpdatePlayer_NotFound() {
        // Given
        when(playerRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> playerService.updatePlayer(999L, playerDTO));
        verify(playerRepository, times(1)).findById(999L);
        verify(playerRepository, never()).save(any(Player.class));
    }

    @Test
    void testDeletePlayer_Success() {
        // Given
        when(playerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(playerRepository).deleteById(1L);

        // When
        playerService.deletePlayer(1L);

        // Then
        verify(playerRepository, times(1)).existsById(1L);
        verify(playerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeletePlayer_NotFound() {
        // Given
        when(playerRepository.existsById(999L)).thenReturn(false);

        // When & Then
        assertThrows(RuntimeException.class, () -> playerService.deletePlayer(999L));
        verify(playerRepository, times(1)).existsById(999L);
        verify(playerRepository, never()).deleteById(anyLong());
    }
}

