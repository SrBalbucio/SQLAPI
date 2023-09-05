package balbucio.sqlapi.model;

public class ConditionValue {

    private String column;
    private Conditional conditional;
    private Object value;

    private Operator operator;

    public ConditionValue(String query){
        String[] step = query.split(" ");
        this.column = step[0];
        this.conditional = Conditional.valueOf(step[1]);
        this.value = step[2];
        this.operator = Operator.valueOf(step[3]);
    }

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

}
