package tk.jandev.function.impl;

import tk.jandev.function.Function;
import tk.jandev.function.VariableContext;

// TODO completely rework variable system
// Each Input should contain a mapping of all variables
public class Variable implements Function {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }


    @Override
    public double apply(VariableContext variables) {
        if (!variables.contains(this)) throw new ArithmeticException("Variable %var% could not be resolved in Expression".replace("%var%", this.name));
        return variables.get(this);
    }

    @Override
    public Function deriveWith(Variable variable) {
        if (variable.equals(this)) return new Constant(1);
        return new Constant(0);
    }

    @Override
    public Function findOptimizedNode() { // variables cannot be optimized.
        return this;
    }

    @Override
    public Function substituteVariableForConstant(Variable variable, Constant constant) {
        return this; // A Function cant be substituted
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Variable var) {
            return this.name.equals(var.name); // if 2 variables have the same name, they are the same variable. Variable names should be unique
        }

        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode(); // objekt hashcode is only dependant on name.
    }
}
