package fr.paillaugue.outfitter.weather.entities;

public enum WeatherType {
    SUNNY,
    RAINY,
    COLD,
    SNOWY;

    public boolean isCold() {
        return this == COLD || this == SNOWY;
    }

    public boolean isWet() {
        return this == RAINY || this == SNOWY;
    }
}
