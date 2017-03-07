package rostradamus.simplematrixcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rostradamus.simplematrixcalculator.model.MatrixController;
import rostradamus.simplematrixcalculator.model.VectorController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        VectorController vectorController = VectorController.getInstance();
        MatrixController matrixController = MatrixController.getInstance();
        setContentView(R.layout.activity_main);
    }
}
