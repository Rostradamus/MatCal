package rostradamus.simplematrixcalculator.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import rostradamus.simplematrixcalculator.R;
import rostradamus.simplematrixcalculator.model.MatrixController;
import rostradamus.simplematrixcalculator.model.VectorController;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        VectorController vectorController = VectorController.getInstance();
        MatrixController matrixController = MatrixController.getInstance();
        setContentView(R.layout.activity_main);
        final Button button = (Button) findViewById(R.id.gaussian_elimination);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.matrix_input_ui);
            }
        });
    }

}
