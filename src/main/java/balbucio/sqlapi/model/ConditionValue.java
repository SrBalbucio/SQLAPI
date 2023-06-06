package balbucio.sqlapi.model;

public class ConditionValue {

    private String column;
    private Conditional conditional;
    private Object value;

    private Operator operator;

    public ConditionValue(String column, Conditional conditional, Object value, Operator operator) {
        this.column = column;
        this.conditional = conditional;
        this.value = value;
        this.operator = operator;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Conditional getConditional() {
        return conditional;
    }

    public void setConditional(Conditional conditional) {
        this.conditional = conditional;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public enum Conditional{
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

    public enum Operator{
        AND, OR, NOT, NULL;
    }
}
