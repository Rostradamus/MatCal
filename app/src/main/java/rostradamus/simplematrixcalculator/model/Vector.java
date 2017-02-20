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

    public Vector(double... components) {
        this.components = new ArrayList<>();
        for(Double component : components) {
            this.components.add(component);
        }
        this.numComponents = this.components.size();
        if (numComponents == 0)
            this.isNull = true;
        else
            this.isNull = false;
    }

    public List<Double> getComponents() {
        return components;
    }

    public void setComponent( int pos, double component) throws UnavailableVectorException{
        try {
            this.components.set(pos, component);
        } catch (Exception e) {
            throw new UnavailableVectorException(e.getMessage());
        }
    }

    public int getNumComponents() {
        return numComponents;
    }

    public boolean isNull() {
        return isNull;
    }


    public Vector unitVector() throws UnavailableVectorException {
        double length = norm();
        Vector retVector = new Vector();

        for (Double component : components) {
            retVector.components.add(component/length);
        }
        return retVector;
    }

    public double norm() throws UnavailableVectorException {
        double result = dotProduct(this);

        return Math.sqrt(result);
    }

    public void addComponent(double component) {
        components.add(component);
        numComponents++;
        if(isNull)
            isNull = false;
    }

    public double dotProduct(Vector other) throws UnavailableVectorException{
        if (numComponents != other.getNumComponents() || isNull)
            throw new UnavailableVectorException("The number of components are different");

        double result = 0;

        for (int i = 0; i < numComponents; i++) {
            result += components.get(i) * other.getComponents().get(i);
        }

        return result;
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

