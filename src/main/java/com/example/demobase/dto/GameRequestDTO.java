package com.example.demobase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRequestDTO {
    private Long idJugador;
    private String palabra;
    private List<Character> letrasIntentadas;
    private Integer intentosRestantes;
}

