package app.Model;

import java.util.Arrays;
import java.util.List;

public class TItem {

    private List<Integer> indexes;
    private String name;

    public TItem(String name, Integer... indexes) {
        this.indexes = Arrays.asList(indexes);
        this.name = name;
    }

    public List<Integer> getIndexes() {
        return indexes;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
