package org.example.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.example.filehandler.ApiHandler;
import org.example.filehandler.FileHandler;
import org.example.models.Pokemon;
import org.example.models.PokemonDeck;

import java.util.*;

public class PokemonServices  {
    private ApiHandler apiHandler;
    private FileHandler fileHandler;
    private List<PokemonDeck> pokemonDecksList;

    public PokemonServices(ApiHandler apiHandler, FileHandler fileHandler) {
        this.apiHandler = apiHandler;
        this.pokemonDecksList = fileHandler.loadDecksFromFile();
    }

    public Pokemon createPokemon(String pokemonName) {
        String jsonResponse = apiHandler.getPokemons(pokemonName);
        List<String> elementTypes = new ArrayList<>();
        List<String> abilities = new ArrayList<>();
        JsonObject json = JsonParser.parseString(jsonResponse).getAsJsonObject();

        String name = json.get("name").getAsString();
        String imgUrl = json.get("sprites").getAsJsonObject().get("front_default").getAsString();
        Integer weight = json.get("weight").getAsInt();
        Integer height = json.get("height").getAsInt();
        JsonArray stats = json.get("stats").getAsJsonArray();
        JsonArray abilitiesType = json.get("abilities").getAsJsonArray();
        JsonArray types = json.get("types").getAsJsonArray();
        Integer hp = stats.get(0).getAsJsonObject().get("base_stat").getAsInt();
        Integer attack = stats.get(1).getAsJsonObject().get("base_stat").getAsInt();
        Integer defense = stats.get(2).getAsJsonObject().get("base_stat").getAsInt();
        for (int j = 0; j <= 1; j++ ) {
            JsonObject ability = abilitiesType.get(j).getAsJsonObject();
            abilities.add(ability.get("ability").getAsJsonObject().get("name").getAsString());
        }
        for (int i = 0 ; i < types.size() ; i++) {
            JsonObject type = types.get(i).getAsJsonObject();
            elementTypes.add(type.get("type").getAsJsonObject().get("name").getAsString());
        }

        Pokemon newPokemon = new Pokemon(name, hp, attack, defense, weight, height, imgUrl, elementTypes, abilities);
        return newPokemon;
    }

    public List<PokemonDeck> getPokemonDecksList() {
        return pokemonDecksList;
    }
}
