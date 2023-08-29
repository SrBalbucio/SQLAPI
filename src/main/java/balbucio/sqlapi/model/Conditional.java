package balbucio.sqlapi.model;

public enum Conditional {
    EQUALS("="), GREAT(">"), LESS("<"), GREATEREQUAL(">="), LESSEQUAL("<=");
    private String value;

    Conditional(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
