package tk.jandev.function;

import tk.jandev.function.impl.Variable;

import java.util.*;

public class VariableContext {
    private final Map<Variable, Double> values; // mapping of variable to its value
    private final List<Variable> containedVariables; // list of variables for a deterministic ordering

    public VariableContext(Map<Variable, Double> values) {
        this.values = values;
        containedVariables = new ArrayList<>();
        containedVariables.addAll(values.keySet());
    }

    public VariableContext() {
        this.values = new HashMap<>();
        this.containedVariables = new ArrayList<>();
    }

    public void set(Variable variable, double value) {
        if (!contains(variable)) {
            this.containedVariables.add(variable);
        }
        this.values.put(variable, value);
    }

    public double get(Variable variable) {
        if (!this.values.containsKey(variable)) return 0; // TODO come up with a better return value, potentially throw error ?
        return this.values.get(variable);
    }

    public double get(int varIndex) {
        return get(this.containedVariables.get(varIndex));
    }

    public void add(Variable variable, double delta) {
        double current = get(variable);
        set(variable, current + delta);
    }

    public void add(int varIndex, double delta) {
        add(this.containedVariables.get(varIndex), delta);
    }

    public void add(double[] deltas) {
        if (this.size() != deltas.length) throw new AssertionError("VariableContext delta length didn't match variable count " + this.size() + " vs. " + deltas.length);
        for (int i = 0; i < deltas.length; i++) {
            add(i, deltas[i]);
        }
    }

    public void subtract(Variable variable, double delta) {
        add(variable, -delta);
    }

    public void subtract(int varIndex, double delta) {
        subtract(this.containedVariables.get(varIndex), delta);
    }

    public void subtract(double[] deltas) {
        for (int i = 0; i < deltas.length; i++) {
            subtract(i, deltas[i]);
        }
    }

    public boolean contains(Variable variable) {
        return this.containedVariables.contains(variable);
    }

    public boolean hasValue(Variable variable) {
        return this.values.containsKey(variable);
    }

    public void introduce(Variable variable) {
        this.containedVariables.add(variable);
    }

    public int size() {
        return this.containedVariables.size();
    }

    public double[] array() {
        double[] result = new double[this.size()];
        for (int i = 0; i < this.size(); i++) {
            Variable var = this.containedVariables.get(i);
            result[i] = this.get(var);
        }

        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        for (Variable var : this.containedVariables) {
            builder.append(var).append(" : ").append(get(var)).append("\n");
        }
        builder.append("}");

        return builder.toString();
    }

    public static VariableContext of(Set<Variable> variableSet) {
        VariableContext context = new VariableContext();
        for (Variable variable : variableSet) {
            context.introduce(variable);
        }

        return context;
    }
}
