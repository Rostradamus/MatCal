package rostradamus.simplematrixcalculator.ui;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.*;
import android.view.View;


import java.util.*;

import rostradamus.simplematrixcalculator.R;
import rostradamus.simplematrixcalculator.model.CalculationController;
import rostradamus.simplematrixcalculator.model.ICalculationController;

public class VectorCalculationUI extends AppCompatActivity {
    private ICalculationController calculationController;
    private int numRow;
    private int numComponent;
    private List<List<EditText>> inputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculationController = CalculationController.getInstance();
        setContentView(R.layout.vector_calcuator_layout);
        numRow = 0;
        EditText inputNumber = (EditText) findViewById(R.id.componentEditText);
        inputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void addRow(View view) {
        final TableLayout table = (TableLayout) findViewById(R.id.vectorTable);
        table.post(new Runnable() {
            @Override
            public void run() {
                numRow++;

                EditText editText = (EditText) findViewById(R.id.componentEditText);
                int numComponent = Integer.parseInt(editText.getText().toString());
                TableRow row = new TableRow(getApplicationContext());
                row.setId(numRow);
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(params);
                int width = table.getWidth() / numComponent;
                Log.i("TableLayout Width", Integer.toString(width));
                for (int i = 0; i < numComponent; i++) {
                    EditText input = new EditText(getApplicationContext());
                    input.setWidth(width);
                    input.setId(numRow * 100 + i);
                    row.addView(input);
                }
                table.addView(row);
            }
        });
    }

    public void removeRow(View view) {
        if (numRow == 0) return;
        TableRow tableRow = (TableRow) findViewById(numRow);
        for (int i = 0; i < numComponent; i++) {
            tableRow.removeView(findViewById(numRow + i));
        }
        TableLayout vectorTable = (TableLayout) findViewById(R.id.vectorTable);
        vectorTable.removeView(tableRow);
        numRow--;
    }

    public void dotProduct(View view) {
        if (inputs.size() != 2) return;
        for (List<EditText> ets: inputs) {

        }
    }



}
