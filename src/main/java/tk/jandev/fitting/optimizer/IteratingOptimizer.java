package tk.jandev.fitting.optimizer;

import tk.jandev.data.DataPoint;
import tk.jandev.data.DataSet;
import tk.jandev.fitting.fittable.Fittable2D;
import tk.jandev.function.VariableContext;

public abstract class IteratingOptimizer<T extends OptimizationConfig> {
    protected final Fittable2D function;
    protected final T configData;
    protected final DataSet dataSet;
    protected VariableContext currentParameters;

    public IteratingOptimizer(Fittable2D function, T configData, DataSet dataSet) {
        this.function = function;
        this.configData = configData;
        this.dataSet = dataSet;
    }

    public VariableContext getCurrentParameters() {
        return this.currentParameters;
    }

    public abstract void iteration();
}
