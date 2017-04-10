package rostradamus.simplematrixcalculator.model;

import java.util.List;

import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;

/**
 * Created by rolee on 2017-04-08.
 */

public interface IVectorController {

    Vector createVector();

    Vector createVector(List<Double> components);

    List<Double> getComponents(Vector vector);

    int getNumComponents(Vector vector);

    void setComponent(Vector vector, int pos, double component) throws UnavailableVectorException;

    boolean isNull(Vector vector);

    void addComponent(Vector vector, double component);

    Vector unitVector(Vector vector) throws UnavailableVectorException;

    double norm(Vector vector) throws UnavailableVectorException;

    Vector addition(List<Vector> vectors) throws UnavailableVectorException;

    Vector substraction(List<Vector> vectors) throws UnavailableVectorException;

    double dotProduct(Vector v1, Vector v2) throws UnavailableVectorException;

    Vector crossProduct(List<Vector> vectors) throws UnavailableVectorException;




}
