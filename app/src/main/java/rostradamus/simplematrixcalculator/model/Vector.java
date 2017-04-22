package rostradamus.simplematrixcalculator.model;

import java.util.*;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;

/**
 * Created by rolee on 2016-12-28.
 */

public class Vector implements Iterable<Double>{
    private List<Double> components;
    private int numComponents;
    private boolean isNull;

    Vector(List<Double> components) {
        this.components = components;
        this.numComponents = this.components.size();
        this.isNull = (numComponents == 0);
    }

    List<Double> getComponents() {
        return components;
    }

    int getNumComponents() {
        return numComponents;
    }

    boolean isNull() {
        return isNull;
    }

    void addComponent(double component) {
        components.add(component);
        numComponents++;
        if(isNull)
            isNull = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vector doubles = (Vector) o;

        if (numComponents != doubles.numComponents) return false;
        if (isNull != doubles.isNull) return false;
        return components.equals(doubles.components);

    }

    @Override
    public int hashCode() {
        int result = components.hashCode();
        result = 31 * result + numComponents;
        result = 31 * result + (isNull ? 1 : 0);
        return result;
    }

    @Override
    public Iterator<Double> iterator() {
        return components.iterator();
    }
}

