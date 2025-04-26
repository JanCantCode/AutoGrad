package tk.jandev.function.impl;

import tk.jandev.function.Function;
import tk.jandev.function.VariableContext;

public class Product implements Function {
    private Function a;
    private Function b;

    public Product(Function a, Function b) {
        this.a = a;
        this.b = b;
    }
    @Override
    public double apply(VariableContext variables) {
        return this.a.apply(variables) * this.b.apply(variables);
    }

    @Override
    public Function deriveWith(Variable variable) {
        Product leftTerm = new Product(a, b.deriveWith(variable));
        Product rightTerm = new Product(a.deriveWith(variable), b);
        return new Sum(leftTerm, rightTerm);
    }

    @Override
    public Function findOptimizedNode() {
        a = a.findOptimizedNode();
        b = b.findOptimizedNode();

        if (a instanceof Constant constA) {
            if (constA.get() == 0) return new Constant(0);
            if (constA.get() == 1) return b;
        }
        if (b instanceof Constant constB) {
            if (constB.get() == 0) return new Constant(0);
            if (constB.get() == 1) return a;
        }

        if (a instanceof Constant constA && b instanceof Constant constB) {
            return new Constant(constA.get() * constB.get());
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
            aNew = a.substituteVariableForConstant(variable, constant);
        }

        if (b instanceof Variable var) {
            if (var.equals(variable)) bNew = constant;
        } else { // if b ins not a Variable, pass down the substitution
            bNew = b.substituteVariableForConstant(variable, constant);
        }
        return new Product(aNew, bNew);
    }

    @Override
    public String toString() {
        return "( " + this.a.toString() + " * " + this.b.toString() + " )";
    }
}
