package model;

public class Country {
    private String name;
    private Boolean isUnderDeveloped;
    private Boolean isInWar;

    public Country(String name, Boolean isUnderDeveloped, Boolean isInWar) {
        this.name = name;
        this.isUnderDeveloped = isUnderDeveloped;
        this.isInWar = isInWar;
    }

    public Country(String name) {
        this(name, null, null);
    }

    public String getName() {
        return name;
    }
}
