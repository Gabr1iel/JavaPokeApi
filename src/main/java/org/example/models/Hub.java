package org.example.models;

import org.example.utils.AlertUtils;
import org.example.utils.ApiHandler;
import org.example.utils.FileHandler;
import org.example.services.PokemonServices;


public class Hub {
    ApiHandler apiHandler = new ApiHandler();
    FileHandler fileHandler = new FileHandler();
    AlertUtils alertUtils = new AlertUtils();
    PokemonServices pokemonServices = new PokemonServices(apiHandler, fileHandler, alertUtils);

    public PokemonServices getPokemonServices() {
        return pokemonServices;
    }
    public ApiHandler getApiHandler() {
        return apiHandler;
    }
    public FileHandler getFileHandler() {
        return fileHandler;
    }
    public AlertUtils getAlertUtils() { return alertUtils; }
}
