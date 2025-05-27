package org.example.models;

import org.example.filehandler.ApiHandler;
import org.example.filehandler.FileHandler;
import org.example.services.PokemonServices;


public class Hub {
    ApiHandler apiHandler = new ApiHandler();
    FileHandler fileHandler = new FileHandler();
    PokemonServices pokemonServices = new PokemonServices(apiHandler, fileHandler);

    public PokemonServices getPokemonServices() {
        return pokemonServices;
    }
    public ApiHandler getApiHandler() {
        return apiHandler;
    }
    public FileHandler getFileHandler() {
        return fileHandler;
    }
}
