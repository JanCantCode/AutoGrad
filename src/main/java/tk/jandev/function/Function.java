package tk.jandev.function;

import tk.jandev.function.impl.Constant;
import tk.jandev.function.impl.Variable;

import java.util.Set;
// TODO generalize function children, see getChildren. When this is done, generalize all methods regarding modifications of children
// TODO replace all methods that return Functions with recursive generics.
public interface Function {
    /**
     *
     * @param variables the values of all variables of this function. If not all variables are present, an Exception will be thrown.
     * @return the value of the function at the point specified by variables
     */
    double apply(Set<Variable> variables);

    /**
     *
     * @param variable the variable to take the derivative of this function in respect to
     * @return a new Function that represents the derivative of this function.
     */
    Function deriveWith(Variable variable);

    /**
     * Tries to find a new Function Node that can represent this Function node in a more efficient manner.
     * This method is recursive, and every Function node should call it upon its children and replace its children with the optimized Functions.
     * @return a new Function node that is functionally equivalent to this function node. It is possible for this method to return the same Function node.
     */
    Function findOptimizedNode();

    /**
     * Returns a new Function that has replaced all instances of this variable with a provided Constant. Does not modify the current function
     * @param variable the variable to replace
     * @param constant the constant to replace it with
     * @return a new function, that has substituted this variable for this constant
     */
    Function substituteVariableForConstant(Variable variable, Constant constant);
    /**
     * TODO
     * return all children of this function, currently unsupported
     */
    default Function[] getChildren() {
        throw new UnsupportedOperationException();
    }
}
