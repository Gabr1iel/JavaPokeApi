package org.example.services;

import com.google.gson.JsonSyntaxException;
import org.example.filehandler.ApiHandler;
import org.example.filehandler.FileHandler;
import org.example.models.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PokemonServicesTest {
    ApiHandler mockApiHandler;
    FileHandler mockFileHandler;
    PokemonServices service;

    @BeforeEach
    void setup() {
        mockApiHandler = mock(ApiHandler.class);
        mockFileHandler = mock(FileHandler.class);
        service = new PokemonServices(mockApiHandler, mockFileHandler);
    }

    @Test
    void createPokemon_ShouldParseJson() {
        String mockedJson = """
            {
              "name": "pikachu",
              "sprites": { "front_default": "https://img.url" },
              "weight": 60,
              "height": 4,
              "stats": [
                { "base_stat": 35 },
                { "base_stat": 55 },
                { "base_stat": 40 }
              ],
              "abilities": [
                { "ability": { "name": "static" } },
                { "ability": { "name": "lightning-rod" } }
              ],
              "types": [
                { "type": { "name": "electric" } }
              ]
            }
        """;

        when(mockApiHandler.getPokemons("pikachu")).thenReturn(mockedJson);
        Pokemon result = service.createPokemon("pikachu");

        assertEquals("pikachu", result.getName());
        assertEquals(35, result.getHp());
        assertEquals(55, result.getAtk());
        assertEquals(40, result.getDef());
        assertEquals(60, result.getWeight());
        assertEquals(4, result.getHeight());
        assertEquals("https://img.url", result.getPokemonImgUrl());
        assertTrue(result.getType().contains("electric"));
        assertTrue(result.getAbilities().contains("static"));
        assertTrue(result.getAbilities().contains("lightning-rod"));
    }

    @Test
    void createPokemon_ShouldHandleMissingSprite() {
        String brokenJson = """
        {
          "name": "pikachu",
          "weight": 60,
          "height": 4,
          "stats": [
            { "base_stat": 35 },
            { "base_stat": 55 },
            { "base_stat": 40 }
          ],
          "abilities": [
            { "ability": { "name": "static" } },
            { "ability": { "name": "lightning-rod" } }
          ],
          "types": [
            { "type": { "name": "electric" } }
          ]
        }
    """;

        when(mockApiHandler.getPokemons("pikachu")).thenReturn(brokenJson);

        assertThrows(NullPointerException.class, () -> service.createPokemon("pikachu"));
    }

    @Test
    void pokemonServices_ShouldHandleInvalidJson() {
        String invalidJson = "{ name: pikachu";

        when(mockApiHandler.getPokemons("pikachu")).thenReturn(invalidJson);
        assertThrows(JsonSyntaxException.class, () -> service.createPokemon("pikachu"));
    }

    @Test
    void pokemonServices_ShouldThrowOnNull() {
        when(mockApiHandler.getPokemons("pikachu")).thenReturn(null);
        assertThrows(NullPointerException.class, () -> service.createPokemon("pikachu"));
    }
}
