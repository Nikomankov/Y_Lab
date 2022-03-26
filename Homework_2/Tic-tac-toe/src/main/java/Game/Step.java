package Game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Step {
    @JsonProperty("_num")
    private int num;

    @JsonProperty("_id")
    private int id;

    @JsonProperty("_line")
    private int line;

    @JsonProperty("_column")
    private int column;

    public Step(int num, int id, int line, int column) {
        this.num = num;
        this.id = id;
        this.line = line;
        this.column = column;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
