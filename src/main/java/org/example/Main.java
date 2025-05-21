package org.example;

import org.example.filehandler.ApiHandler;
import org.example.models.Hub;

public class Main {
    public static void main(String[] args) {
        Hub hub = new Hub();

        hub.getPokemonServices().createPokemon(hub.getApiHandler().getPokemons("bulbasaur"));
    }
}