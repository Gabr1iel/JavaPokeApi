package org.example.filehandler;

import org.example.models.PokemonDeck;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    public void saveDecksToFile(List<PokemonDeck> pokemonDecksList) {
        File directory = new File("data");
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (pokemonDecksList == null || pokemonDecksList.isEmpty()) {
            System.out.println("No pokemon decks found!");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("data/pokemon_decks.ser"))) {
            oos.writeObject(pokemonDecksList);
            System.out.println("Saved pokemon decks!");
        } catch (IOException e) {
            System.out.println("Error saving pokemon decks!" + e.getMessage());
            e.printStackTrace();
        }
    }
    public List<PokemonDeck> loadDecksFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("data/pokemon_decks.ser"))) {
            List<PokemonDeck> pokemonDecks = (List<PokemonDeck>) ois.readObject();
            return pokemonDecks;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading pokemon decks!" + e.getMessage());
            return new ArrayList<>();
        }
    }
}
