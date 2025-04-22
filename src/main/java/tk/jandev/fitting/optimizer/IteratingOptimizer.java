package tk.jandev.fitting.optimizer;

import tk.jandev.data.DataPoint;
import tk.jandev.data.DataSet;
import tk.jandev.fitting.fittable.Fittable2D;

public abstract class IteratingOptimizer<T extends OptimizationConfig> {
    protected final Fittable2D function;
    protected final T configData;
    protected final DataSet dataSet;

    public IteratingOptimizer(Fittable2D function, T configData, DataSet dataSet) {
        this.function = function;
        this.configData = configData;
        this.dataSet = dataSet;
    }

    public abstract void iteration();
}
