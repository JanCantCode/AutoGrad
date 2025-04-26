package tk.jandev.function.impl;

import tk.jandev.function.Function;

import java.util.Set;

public class Exponent implements Function {
    private Function base;
    private final double exponent;
    public Exponent(Function base, double exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public double apply(Set<Variable> variables) {
        return Math.pow(base.apply(variables), this.exponent);
    }

    @Override
    public Function deriveWith(Variable variable) {
        Function firstTerm = new Product(new Constant(exponent), new Exponent(this.base, exponent - 1));
        Function secondTerm = this.base.deriveWith(variable);

        return new Product(firstTerm, secondTerm);
    }

    @Override
    public Function findOptimizedNode() {
        base = base.findOptimizedNode();

        if (this.exponent == 0) { // a to the power of 0 is always 1 - optimizing into constants allows easier later optimization
            return new Constant(1);
        }

        if (this.exponent == 1) {
            return this.base;
        }

        return this;
    }

    @Override
    public Function substituteVariableForConstant(Variable variable, Constant constant) {
        if (this.base instanceof Variable var) {
            if (var.equals(variable)) return new Exponent(constant, this.exponent);
        }
        return new Exponent(this.base.substituteVariableForConstant(variable, constant), exponent);
    }
}
