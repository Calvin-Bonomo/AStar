public enum Cell {
    EMPTY(" "),
    START("o"),
    END  ("x"),
    PATH ("+"),
    WALL ("0");

    String cellType;

    Cell (String cellType) {
        this.cellType = cellType;
    }

    String getCellType() {
        return cellType;
    }
}
