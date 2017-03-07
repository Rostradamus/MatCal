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

    Vector(double... components) {
        this.components = new ArrayList<>();
        for(Double component : components) {
            this.components.add(component);
        }
        this.numComponents = this.components.size();
        this.isNull = (numComponents == 0);
    }

    List<Double> getComponents() {
        return components;
    }

    void setComponent( int pos, double component) throws UnavailableVectorException{
        try {
            this.components.set(pos, component);
        } catch (Exception e) {
            throw new UnavailableVectorException(e.getMessage());
        }
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

        Vector vector = (Vector) o;

        return components.equals(vector.components);
    }

    @Override
    public int hashCode() {
        return components.hashCode();
    }


    @Override
    public Iterator<Double> iterator() {
        return components.iterator();
    }
}

