package tk.jandev.function.impl;

import tk.jandev.function.Function;

import java.util.Set;

public class Sum implements Function {
    private Function a;
    private Function b;

    public Sum(Function a, Function b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public double apply(Set<Variable> variables) {
        return a.apply(variables) + b.apply(variables);
    }

    @Override
    public Function deriveWith(Variable variable) {
        return new Sum(a.deriveWith(variable), b.deriveWith(variable));
    }

    @Override
    public Function findOptimizedNode() {
        a = a.findOptimizedNode();
        b = b.findOptimizedNode();
        if (a instanceof Constant constantA && b instanceof Constant constantB) {
            return new Constant(constantA.get() + constantB.get());
        }
        return this;
    }

    @Override
    public Function substituteVariableForConstant(Variable variable, Constant constant) {
        Function aNew = a;
        Function bNew = b;
        if (a instanceof Variable var) {
            if (var.equals(variable)) aNew = constant;
        } else { // if a is not a Variable, pass down the substitution
            a = a.substituteVariableForConstant(variable, constant);
        }

        if (b instanceof Variable var) {
            if (var.equals(variable)) bNew = constant;
        } else {
            b = b.substituteVariableForConstant(variable, constant);
        }

        return new Sum(aNew, bNew);
    }
}
