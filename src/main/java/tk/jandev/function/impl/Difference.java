package tk.jandev.function.impl;

import tk.jandev.function.Function;

import java.util.Set;

public class Difference extends Sum {
    public Difference(Function a, Function b) {
        super(a, new Product( // a difference is just a sum where b is negated.
                new Constant(-1),
                b
        ));
    }

    @Override
    public String toString() {
        return "( " + a.toString() + " - " + b.toString() + " )";
    }

    @Override
    public Function substituteVariableForConstant(Variable variable, Constant constant) {
        return super.substituteVariableForConstant(variable, constant);
    }
}
