package org.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import org.example.utils.AlertUtils;
import org.example.utils.FileHandler;
import org.example.models.Hub;
import org.example.models.Pokemon;
import org.example.models.PokemonDeck;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;


public class MainViewController {
    Hub hub = new Hub();
    FileHandler fileHandler = hub.getFileHandler();
    Pokemon currentPokemon = null;

    @FXML private FlowPane contentPane;
    @FXML private ListView<PokemonDeck> deckList;

    @FXML private void initialize() {
        contentPane.getStylesheets().add(getClass().getResource("/org/example/styles/style.css").toExternalForm());
        contentPane.setOnMouseClicked(event -> {
            Node target = (Node) event.getTarget();
            Node cardNode = findNodeWithClass(target, "pokemon-card");

            if (cardNode != null) {
                Pokemon p = (Pokemon) cardNode.getUserData();
                currentPokemon = p;
                System.out.println(currentPokemon.getName());
            } else {
                deckList.getSelectionModel().clearSelection();
                contentPane.getChildren().clear();
                currentPokemon = null;
            }
        });
        updateData();
    }

    @FXML private void updateData() {
        fileHandler.saveDecksToFile(hub.getPokemonServices().getPokemonDecksList());
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
                        cardNode.setUserData(p);
                        cardNode.getStyleClass().add(p.getType().get(0).toLowerCase());
                        System.out.println(p.getName() + p.getType().get(0));

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
            updateData();
            System.out.println("Deck name: " + hub.getPokemonServices().getPokemonDecksList().get(0).getName());
        });
    }
    @FXML private void handleRemoveDeck() {
        hub.getPokemonServices().getPokemonDecksList().remove(deckList.getSelectionModel().getSelectedItem());
        updateData();
    }
    @FXML private void handleEditDeck() {
        PokemonDeck currentDeck = deckList.getSelectionModel().getSelectedItem();
        String deckName = currentDeck.getName();

        if (currentDeck != null) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Edit Deck");
            dialog.setHeaderText("Enter your deck name");
            dialog.setContentText("Name: ");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                if (!name.trim().isEmpty()) {
                    currentDeck.setName(name);
                    updateData();
                } else {
                    hub.getAlertUtils().showErrorAlert("Chybějící název!", "Prosím vyplňte nový název");
                }
            });
        }
    }

    @FXML private void handleAddPokemonToDeck() {
        if (deckList.getSelectionModel().getSelectedItem() != null) {
            PokemonDeck currentDeck = deckList.getSelectionModel().getSelectedItem();
            if (currentDeck.getPokemons().size() <= 9) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Choose Pokemon");
                dialog.setHeaderText("Enter your Pokemon name");
                dialog.setContentText("Name: ");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(name -> {
                    Pokemon newPokemon = hub.getPokemonServices().createPokemon(name);
                    currentDeck.getPokemons().add(newPokemon);
                    updateData();
                });
            } else {
                hub.getAlertUtils().showErrorAlert("Plný deck!", "Tento deck už má maximální počet pokémonů!");
            }
        }
    }
    @FXML private void handleRemovePokemonFromDeck() {
        if (currentPokemon != null) {
            PokemonDeck currentDeck = deckList.getSelectionModel().getSelectedItem();
            currentDeck.getPokemons().remove(currentPokemon);
            updateData();
        }
    }

    private Node findNodeWithClass(Node node, String className ) { //hledá jestli elemnt má rodiče s danou třídou
        Node current = node;

        while (current != null) {
            if (current.getStyleClass().contains(className)) {
                return current;
            }
            current = current.getParent();
        }
        return null;
    }
}
