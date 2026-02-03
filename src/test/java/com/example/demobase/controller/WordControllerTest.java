package com.example.demobase.controller;

import com.example.demobase.dto.WordDTO;
import com.example.demobase.service.WordService;
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

@WebMvcTest(WordController.class)
class WordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordService wordService;

    @Test
    void testGetAllWords() throws Exception {
        // Given
        WordDTO word1 = new WordDTO(1L, "PROGRAMADOR", true);
        WordDTO word2 = new WordDTO(2L, "COMPUTADORA", false);
        List<WordDTO> words = Arrays.asList(word1, word2);
        when(wordService.getAllWords()).thenReturn(words);

        // When & Then
        mockMvc.perform(get("/api/words"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].palabra").value("PROGRAMADOR"))
                .andExpect(jsonPath("$[0].utilizada").value(true))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].utilizada").value(false));

        verify(wordService, times(1)).getAllWords();
    }
}

