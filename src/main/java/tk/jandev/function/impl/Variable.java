package tk.jandev.function.impl;

import tk.jandev.function.Function;

import java.util.Set;
// TODO completely rework variable system
// Each Input should contain a mapping of all variables
public class Variable implements Function {
    private final String name;
    private double value;

    public Variable(String name) {
        this.name = name;
    }

    public String name() {
        return this.name;
    }

    public double get() {
        return this.value;
    }

    public void set(double value) {
        this.value = value;
    }

    public void add(double delta) {
        this.value += delta;
    }

    public void subtract(double delta) {
        this.value -= delta;
    }

    @Override
    public double apply(Set<Variable> variables) {
        for (Variable var : variables) {
            System.out.println("this: " + this.name + " trying: " + var.name);
            if (var.equals(this)) return var.get();
        }
        throw new ArithmeticException("Did not find input variable matching ourself!");
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
        return "{" + this.name + " : " + this.value + "}";
    }

    @Override
    public int hashCode() {
        return this.name.hashCode(); // objekt hashcode is only dependant on name.
    }
}
