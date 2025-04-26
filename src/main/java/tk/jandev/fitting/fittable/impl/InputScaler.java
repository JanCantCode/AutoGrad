package tk.jandev.fitting.fittable.impl;

import tk.jandev.fitting.fittable.Fittable2D;
import tk.jandev.function.Function;
import tk.jandev.function.impl.Product;
import tk.jandev.function.impl.Sum;
import tk.jandev.function.impl.Variable;

import java.util.Set;

/**
 * Represents a linear Function in the form of f(x) = m * x
 */
public class InputScaler implements Fittable2D {
    private final Function function;
    private final Variable m;

    private final Variable x;

    private final Set<Variable> fittableParams;
    private final Set<Variable> inputParams;

    public InputScaler() {
        this.m = new Variable("m");
        this.x = new Variable("x");

        this.fittableParams = Set.of(m);
        this.inputParams = Set.of(x);

        this.function = new Product(
                m,
                x
        );
    }
    @Override
    public Set<Variable> getFittableParams() {
        return this.fittableParams;
    }

    @Override
    public Set<Variable> getInputParameters() {
        return this.inputParams;
    }

    @Override
    public Function getFunction() {
        return this.function;
    }
}
