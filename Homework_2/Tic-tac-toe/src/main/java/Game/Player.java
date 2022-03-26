package Game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Player {
    @JsonProperty("_id")
    private int id;

    @JsonProperty("_name")
    private String name;

    @JsonProperty("_symbol")
    private String symbol;

    public Player(int id, String name, String symbol){
        this.id = id;
        this.name = name;
        this.symbol = symbol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }


}
