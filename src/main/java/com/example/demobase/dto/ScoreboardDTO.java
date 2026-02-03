package com.example.demobase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreboardDTO {
    private Long idJugador;
    private String nombreJugador;
    private Integer puntajeTotal;
    private Long partidasJugadas;
    private Long partidasGanadas;
    private Long partidasPerdidas;
}

