package org.example.models;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private Integer hp;
    private Integer atk;
    private Integer def;
    private Integer weight;
    private Integer height;
    private String pokemonImgUrl;
    private List<String> type;
    private List<String> abilities;

    public Pokemon(
            String name,
            Integer hp,
            Integer atk,
            Integer def,
            Integer weight,
            Integer height,
            String pokemonImgUrl,
            List<String> type,
            List<String> abilities
    ) {
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.weight = weight;
        this.height = height;
        this.pokemonImgUrl = pokemonImgUrl;
        this.type = type;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }
    public Integer getHp() {
        return hp;
    }
    public Integer getAtk() {
        return atk;
    }
    public Integer getDef() {
        return def;
    }
    public List<String> getAbilities() {
        return abilities;
    }
    public List<String> getType() {
        return type;
    }
    public String getPokemonImgUrl() {
        return pokemonImgUrl;
    }

}
