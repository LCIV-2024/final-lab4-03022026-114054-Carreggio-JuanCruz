package com.example.demobase.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameResponseDTO {
    private String palabraOculta;
    private List<Character> letrasIntentadas;
    private Integer intentosRestantes;
    private Boolean palabraCompleta;
    private Integer puntajeAcumulado;
}

