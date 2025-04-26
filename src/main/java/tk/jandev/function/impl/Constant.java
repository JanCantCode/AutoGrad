package tk.jandev.function.impl;

import tk.jandev.function.Function;
import tk.jandev.function.VariableContext;

public class Constant implements Function {
    private final double value;
    public Constant(double value) {
        this.value = value;
    }

    public double get() {
        return this.value;
    }

    @Override
    public double apply(VariableContext variables) {
        return this.value;
    }

    @Override
    public Function deriveWith(Variable variable) {
        return new Constant(0);
    }

    @Override
    public Function findOptimizedNode() { // Constants cannot be optimized and don't have children
        return this;
    }

    @Override
    public Function substituteVariableForConstant(Variable variable, Constant constant) {
        return new Constant(this.get()); // constants arent variables and don't have children
    }

    @Override
    public String toString() {
        return String.valueOf(this.get());
    }
}
