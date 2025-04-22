package tk.jandev.function.impl;

import tk.jandev.function.Function;

import java.util.Set;

public class Inverse implements Function {
    private Function function;
    public Inverse(Function function) {
        this.function = function;
    }
    @Override
    public double apply(Set<Variable> variables) {
        return 1 / function.apply(variables);
    }

    @Override
    public Function deriveWith(Variable variable) {
        Function numerator = new Product(new Constant(-1), this.function.deriveWith(variable));
        Function denominator = new Product(this, this); // f(x)²

        return new Product(new Inverse(denominator), numerator); // a/b = a * (1/b)
    }

    @Override
    public Function findOptimizedNode() {
        function = function.findOptimizedNode();


        if (function instanceof Constant constant) {
            return new Constant(1.0 / constant.get());
        }
        return this;
    }
}
