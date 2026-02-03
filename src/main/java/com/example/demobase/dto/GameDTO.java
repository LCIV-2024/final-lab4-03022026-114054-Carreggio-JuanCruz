package com.example.demobase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO {
    private Long id;
    private Long idJugador;
    private String nombreJugador;
    private String resultado;
    private Integer puntaje;
    private LocalDateTime fechaPartida;
    private String palabra;
}

