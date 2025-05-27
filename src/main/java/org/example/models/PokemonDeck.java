package org.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PokemonDeck implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Pokemon> pokemons;

    public PokemonDeck(String name) {
        this.name = name;
        this.pokemons = new ArrayList<Pokemon>();
    }

    public String getName() {
        return name;
    }
    public List<Pokemon> getPokemons() {
        return pokemons;
    }
}
