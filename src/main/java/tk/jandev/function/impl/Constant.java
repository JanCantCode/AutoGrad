package tk.jandev.function.impl;

import tk.jandev.function.Function;

import java.util.Set;

public class Constant implements Function {
    private final double value;
    public Constant(double value) {
        this.value = value;
    }

    public double get() {
        return this.value;
    }

    @Override
    public double apply(Set<Variable> variables) {
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
}
