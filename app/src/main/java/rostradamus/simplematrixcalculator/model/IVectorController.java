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

    double norm(Vector u) throws UnavailableVectorException;

    Vector unitVector(Vector u) throws UnavailableVectorException;

    Vector scalarMultiplication(double c, Vector u) throws UnavailableVectorException;

    Vector add(Vector u, Vector v) throws UnavailableVectorException;

    double dotProduct(Vector u, Vector v) throws UnavailableVectorException;

    Vector crossProduct(Vector u, Vector v) throws UnavailableVectorException;

    double scalarProjection(Vector u, Vector v) throws UnavailableVectorException;

    Vector projection(Vector u, Vector v) throws UnavailableVectorException;

    double angle(Vector u, Vector v) throws UnavailableVectorException;

    double angle(Vector u, Vector v, boolean isDegree) throws UnavailableVectorException;

}
