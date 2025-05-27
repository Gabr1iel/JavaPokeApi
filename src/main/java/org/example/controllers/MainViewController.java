package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import org.example.filehandler.FileHandler;
import org.example.models.Hub;
import org.example.models.PokemonDeck;

import java.util.Optional;


public class MainViewController {
    Hub hub = new Hub();
    FileHandler fileHandler = hub.getFileHandler();

    @FXML private StackPane contentPane;
    @FXML private TextField pokemonNameField;
    @FXML private ListView<PokemonDeck> deckList;

    @FXML private void initialize() {
        contentPane.getStylesheets().add(getClass().getResource("/org/example/styles/style.css").toExternalForm());
        hub.getPokemonServices().createPokemon("Bulbasaur");
        updateData();
    }

    @FXML private void updateData() {
        deckList.setItems(FXCollections.observableList(hub.getPokemonServices().getPokemonDecksList()));
        deckList.setCellFactory(lc -> new ListCell<PokemonDeck>() {
            @Override protected void updateItem(PokemonDeck item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? null : item.getName());
            }
        });
    }

    @FXML private void handleAddDeck() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Deck");
        dialog.setHeaderText("Enter your deck name");
        dialog.setContentText("Name: ");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            PokemonDeck deck = new PokemonDeck(name);
            hub.getPokemonServices().getPokemonDecksList().add(deck);
            fileHandler.saveDecksToFile(hub.getPokemonServices().getPokemonDecksList());
            updateData();
            System.out.println("Deck name: " + hub.getPokemonServices().getPokemonDecksList().get(0).getName());
        });
    }
    @FXML private void handleRemoveDeck() {
        hub.getPokemonServices().getPokemonDecksList().remove(deckList.getSelectionModel().getSelectedItem());
        fileHandler.saveDecksToFile(hub.getPokemonServices().getPokemonDecksList());
        updateData();
    }

    @FXML private void handleAddPokemonToDeck() {
        String pokemonName = pokemonNameField.getText();

    }

}
