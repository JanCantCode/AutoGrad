package tk.jandev.data;

import tk.jandev.function.Function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataSet implements Iterable<DataPoint> {
    private final List<DataPoint> data = new ArrayList<>();
    private int currentIndex = 0;

    public void addData(DataPoint point) {
        this.data.add(point);
    }

    public void addData(double[] in, double[] out) {
        this.addData(new DataPoint.SimpleDataPoint(in, out));
    }

    public DataPoint get(int index) {
        return data.get(index);
    }

    public int size() {
        return this.data.size();
    }

    @Override
    public Iterator<DataPoint> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return currentIndex < data.size() - 1;
            }

            @Override
            public DataPoint next() {
                currentIndex++;
                return data.get(currentIndex);
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (DataPoint point : this) {
            result.append(point);
            result.append("\n");
        }

        return result.toString();
    }
}
