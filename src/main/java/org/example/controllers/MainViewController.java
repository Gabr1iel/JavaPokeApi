package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import org.example.filehandler.FileHandler;
import org.example.models.Hub;
import org.example.models.Pokemon;
import org.example.models.PokemonDeck;

import java.io.IOException;
import java.util.Optional;


public class MainViewController {
    Hub hub = new Hub();
    FileHandler fileHandler = hub.getFileHandler();

    @FXML private FlowPane contentPane;
    @FXML private ListView<PokemonDeck> deckList;

    @FXML private void initialize() {
        contentPane.getStylesheets().add(getClass().getResource("/org/example/styles/style.css").toExternalForm());
        hub.getPokemonServices().createPokemon("Bulbasaur");
        updateData();
    }

    @FXML private void updateData() {
        deckList.setItems(FXCollections.observableList(hub.getPokemonServices().getPokemonDecksList()));
        deckList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleShowCards();
            }
        });
        deckList.setCellFactory(lc -> new ListCell<PokemonDeck>() {
            @Override protected void updateItem(PokemonDeck item, boolean empty) {
                super.updateItem(item, empty);
                setText(item == null ? null : item.getName());
            }
        });
    }

    @FXML private void handleShowCards() {
        if (deckList.getSelectionModel().getSelectedItem() != null) {
            contentPane.getChildren().clear();
            ObservableList<PokemonDeck> currentDeck = deckList.getSelectionModel().getSelectedItems();

            if (!currentDeck.getFirst().getPokemons().isEmpty()) {
                for (Pokemon p : currentDeck.getFirst().getPokemons()) {
                    System.out.println(p.getName());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/views/pokemon-card.fxml"));
                        Parent cardNode = fxmlLoader.load();

                        PokemonCardController controller = fxmlLoader.getController();
                        controller.setData(p);

                        contentPane.getChildren().add(cardNode);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("No pokemons in deck");
            }
        } else {
            System.out.println("No deck selected");
        }
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
        if (deckList.getSelectionModel().getSelectedItem() != null) {
            PokemonDeck currentDeck = deckList.getSelectionModel().getSelectedItem();
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Choose Pokemon");
            dialog.setHeaderText("Enter your Pokemon name");
            dialog.setContentText("Name: ");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                Pokemon newPokemon = hub.getPokemonServices().createPokemon(name);
                currentDeck.getPokemons().add(newPokemon);
                fileHandler.saveDecksToFile(hub.getPokemonServices().getPokemonDecksList());
                updateData();
            });
        }
    }

}
