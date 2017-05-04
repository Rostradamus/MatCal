package rostradamus.simplematrixcalculator.model;

import java.util.List;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;

/**
 * Created by rolee on 2017-04-08.
 */

public interface IVectorController {

    Vector createVector(List<Double> components);

    double getComponentAt(Vector vector, int index);

    int getNumComponents(Vector vector);

    boolean isNull(Vector vector);

    void addComponent(Vector vector, double component);

    Vector unitVector(Vector vector) throws UnavailableVectorException;

    double norm(Vector vector) throws UnavailableVectorException;

    Vector add(Vector v1, Vector v2) throws UnavailableVectorException;

    double dotProduct(Vector v1, Vector v2) throws UnavailableVectorException;

    Vector crossProduct(Vector v1, Vector v2) throws UnavailableVectorException;

}
