package balbucio.sqlapi.model;


public class ConditionModifier{

    private ConditionValue[] conditionValues;
    public ConditionModifier(ConditionValue[] conditionValues, Object... objects){
        this.conditionValues = conditionValues;
        for (int i = 0; i < objects.length; i++) {
            if (conditionValues[i] != null) {
                conditionValues[i].setValue(objects[i]);
            }
        }

    }
    
    public ConditionValue[] done(){
        return conditionValues;
    }
}
