package org.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.models.Pokemon;

public class PokemonCardController {
    @FXML private Label pokemonNameLabel;
    @FXML private Label pokemonTypeLabel;
    @FXML private ImageView pokemonImg;

    public void setData(Pokemon pokemon) {
        pokemonNameLabel.setText(pokemon.getName());

        Image image = new Image(pokemon.getPokemonImgUrl(), true);
        pokemonImg.setImage(image);
    }
}
