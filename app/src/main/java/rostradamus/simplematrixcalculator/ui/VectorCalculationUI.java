package rostradamus.simplematrixcalculator.ui;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.*;
import android.widget.*;
import android.view.*;
import rostradamus.simplematrixcalculator.R;
import rostradamus.simplematrixcalculator.exception.UnavailableVectorException;
import rostradamus.simplematrixcalculator.model.IVectorController;
import rostradamus.simplematrixcalculator.model.Vector;
import rostradamus.simplematrixcalculator.model.VectorController;
import java.util.*;

public class VectorCalculationUI extends AppCompatActivity {
    private IVectorController vectorController;
    private int numRow;
    private int numComponent;
    private List<List<EditText>> inputs;
    private static final String DEFAULT_DELTA_VALUE = "%.7f";
    private Vector result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vectorController = VectorController.getInstance();
        setContentView(R.layout.vector_calcuator_layout);
        inputs = new ArrayList<>();
        resulte = null;
        numRow = 0;
        EditText inputNumber = (EditText) findViewById(R.id.componentEditText);
        inputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flushInput();
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    public void addRow(View view) {
        final EditText editText = (EditText) findViewById(R.id.componentEditText);
        if (editText.getText().toString().equals("")) {
            alertHelper("Must enter number");
            return;
        }
        final TableLayout table = (TableLayout) findViewById(R.id.vectorTable);
        table.post(new Runnable() {
            @Override
            public void run() {

                numRow++;
                numComponent = Integer.parseInt(editText.getText().toString());
                TableRow row = new TableRow(getApplicationContext());
                row.setId(numRow);
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(params);
                int width = table.getWidth() / numComponent;
                List<EditText> ets = new ArrayList<>();
                for (int i = 0; i < numComponent; i++) {
                    EditText input = new EditText(getApplicationContext());
                    input.setWidth(width);
                    input.setGravity(Gravity.CENTER_HORIZONTAL);
                    input.setId(numRow * 100 + i);
                    row.addView(input);
                    ets.add(input);
                }
                table.addView(row);
                inputs.add(ets);
            }
        });
    }



    public void removeRow(View view) {
        if (numRow == 0) {
            alertHelper("No more row to remove");
            return;
        }
        TableRow tableRow = (TableRow) findViewById(numRow);
        for (int i = 0; i < numComponent; i++) {
            tableRow.removeView(findViewById(numRow + i));
        }
        TableLayout vectorTable = (TableLayout) findViewById(R.id.vectorTable);
        vectorTable.removeView(tableRow);
        inputs.remove(numRow - 1);
        numRow--;
    }

    public void dotProduct(View view) {
        setup(2);
        if (inputs.size() != 2) {
            alertHelper("Must be 2 rows for Dot Product");
            return;
        }
        List<Vector> vectors = vectorConverter();
        try {
            double result = vectorController.dotProduct(vectors.get(0), vectors.get(1));
            renderResult(result);
        } catch (UnavailableVectorException e) {
            alertHelper("Vector is NOT valid");
        }
    }

    public void norm (View view) {
        setup(1);
        if (inputs.size() != 1) {
            alertHelper("Must be 1 row for Length");
            return;
        }
        try {
            double result = vectorController.norm(vectorConverter().get(0));

            renderResult(result);
        } catch (UnavailableVectorException e) {
            alertHelper("Vector is NOT valid");
        }

    }

    public void unitVector(View view) {
        setup(1);
        if (inputs.size() != 1) {
            alertHelper("Must be 1 row for Unit Vector");
            return;
        }
        try {
            Vector result = vectorController.unitVector(vectorConverter().get(0));
            renderResult(result);
        } catch (UnavailableVectorException e) {
            alertHelper("Vector is NOT valid");
        }
    }

    public void addition(View view) {
        setup(2);
        if (inputs.size() == 0) {
            alertHelper("Must be more than 1 row");
            return;
        }
        List<Vector> vectors = vectorConverter();
        try {
            Vector result = null;
            for (Vector vector: vectors) {
                if (result == null)
                    result = vector;
                else
                    result = vectorController.add(result, vector);
            }
            renderResult(result);
        } catch (UnavailableVectorException e) {
            alertHelper(e.getMessage());
        }
    }

    public void crossProduct(View view) {
        setup(2);
        if (!(inputs.size() > 1)) {
            alertHelper("Must be more than 1 row");
            return;
        }
        List<Vector> vectors = vectorConverter();
        try {
            Vector result = null;
            for (Vector vector: vectors) {
                if (result == null)
                    result = vector;
                else
                    vectorController.crossProduct(result, vector);
            }
            renderResult(result);
        } catch (UnavailableVectorException e) {
            alertHelper(e.getMessage());
        }
    }

    public void angle(View view) {
        setup(2);
    }

    private List<Vector> vectorConverter() {
        List<Vector> vectors = new ArrayList<>();
        for (List<EditText> ets: inputs) {
            List<Double> components = new ArrayList<>();
            for (EditText et: ets){
                String input = et.getText().toString();
                try {
                    components.add(Double.parseDouble(input));
                } catch (RuntimeException e) {
                    alertHelper(e.getMessage());
                }

            }
            vectors.add(vectorController.createVector(components));
        }
        return vectors;
    }

    public void showResult(View v) {
        
    }

    private void alertHelper(String msg) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    private void renderResult(final double result) {
        final TableLayout resultTable = (TableLayout) findViewById(R.id.outputTable);
        resultTable.post(new Runnable() {
            @Override
            public void run() {
                int width = resultTable.getWidth();
                resultTable.removeAllViews();
                TextView resultView = new TextView(getApplicationContext());
                resultView.setWidth(width);
                resultView.setText("Result = \n" + Double.toString(cutDouble(result)));
                resultView.setGravity(Gravity.CENTER_HORIZONTAL);
                resultView.setTextSize(35);
                resultTable.addView(resultView);
            }
        });
    }

    private void renderResult(final Vector result) {
        final TableLayout resultTable = (TableLayout) findViewById(R.id.outputTable);
        resultTable.post(new Runnable() {
            @Override
            public void run() {
                resultTable.removeAllViews();
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                TextView headText = new TextView(getApplicationContext());
                headText.setText("Result =");
                headText.setGravity(Gravity.CENTER_HORIZONTAL);
                headText.setTextSize(35);
                resultTable.addView(headText);
                int vectorSize = vectorController.getNumComponents(result);
                int width = resultTable.getWidth();
                for (int i = 0; i < vectorSize; i++) {
                    TableRow tr = new TableRow(getApplicationContext());
                    tr.setLayoutParams(params);
                    TextView component = new TextView(getApplicationContext());
                    System.out.println(vectorController.getComponentAt(result, i));
                    component.setText(Double.toString(cutDouble(vectorController.getComponentAt(result, i))));
                    component.setTextSize(23);
                    component.setWidth(width);
                    component.setGravity(Gravity.CENTER_HORIZONTAL);
                    tr.addView(component);
                    resultTable.addView(tr);
                }

            }
        });
    }

    private void flushInput() {
        while (inputs.size() != 0) {
            removeRow(null);
        }
    }

    private void setup(int numRow) {
        flushInput();
        setRows(numRow);
    }

    private void setRows(final int number) {
        final EditText editText = (EditText) findViewById(R.id.componentEditText);
        if (editText.getText().toString().equals("")) {
            alertHelper("Must enter number");
            return;
        }
        final TableLayout table = (TableLayout) findViewById(R.id.vectorTable);
        table.post(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < number; j++) {
                    numRow++;
                    numComponent = Integer.parseInt(editText.getText().toString());
                    TableRow row = new TableRow(getApplicationContext());
                    row.setId(numRow);
                    TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(params);
                    int width = table.getWidth() / numComponent;
                    List<EditText> ets = new ArrayList<>();
                    for (int i = 0; i < numComponent; i++) {
                        EditText input = new EditText(getApplicationContext());
                        input.setWidth(width);
                        input.setGravity(Gravity.CENTER_HORIZONTAL);
                        input.setId(numRow * 100 + i);
                        row.addView(input);
                        ets.add(input);
                    }
                    table.addView(row);
                    inputs.add(ets);
                }
            }
        });
    }

    private List<Double> cutDouble(List<Double> doubles) {
        List<Double> newComps = new ArrayList<>();
        for (Double comp: doubles) newComps.add(cutDouble(comp));
        return newComps;
    }

    private double cutDouble(double value) {
        return Double.parseDouble(String.format(DEFAULT_DELTA_VALUE, value));
    }



}
