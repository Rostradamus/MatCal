package rostradamus.simplematrixcalculator.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rostradamus.simplematrixcalculator.R;
import rostradamus.simplematrixcalculator.model.IMatrixController;
import rostradamus.simplematrixcalculator.model.MatrixController;

public class MatrixCalculationUI extends AppCompatActivity {
    private IMatrixController matrixController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        matrixController = MatrixController.getInstance();
        setContentView(R.layout.matrix_calculation_layout);
    }
}
