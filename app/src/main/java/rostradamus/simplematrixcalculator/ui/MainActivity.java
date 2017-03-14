package rostradamus.simplematrixcalculator.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import rostradamus.simplematrixcalculator.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startVectorCalculation(View view) {
        Intent intent = new Intent(this, VectorCalcuatorUI.class);
        startActivity(intent);
    }

    public void startMatrixCalculation(View view) {
        Intent intent = new Intent(this, MatrixCalculationUI.class);
        startActivity(intent);
    }

}
