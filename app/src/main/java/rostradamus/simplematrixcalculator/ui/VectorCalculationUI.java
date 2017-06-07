package rostradamus.simplematrixcalculator.ui;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.*;
import android.widget.*;
import android.view.*;

import org.w3c.dom.Text;

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
    private String currCalculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vectorController = VectorController.getInstance();
        setContentView(R.layout.vector_calcuator_layout);
        inputs = new ArrayList<>();
        result = null;
        currCalculation = null;
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

    public void selectCalculation(View view) {
        currCalculation = getResources().getResourceName(view.getId()).split("/")[1];
        TextView tv = (TextView) findViewById(R.id.defaultCal);
        tv.setText(((Button) findViewById(view.getId())).getText());
        setup();
    }

    public void submit(View v) {

        try {
            List<Vector> vectors = vectorConverter();
            switch (currCalculation) {

                case "dotProduct": {
                    result = doubleToVector(vectorController.dotProduct(vectors.get(0), vectors.get(1)));
                    renderResult(result);
                    break;
                }
                case "norm": {
                    result = doubleToVector(vectorController.norm(vectors.get(0)));
                    renderResult(result);
                    break;
                }
                case "addition": {
                    result = vectorController.add(vectors.get(0), vectors.get(1));
                    renderResult(result);
                    break;
                }
                case "unitVector": {
                    result = vectorController.unitVector(vectors.get(0));
                    renderResult(result);
                    break;
                }
                case "crossProduct": {
                    result = vectorController.crossProduct(vectors.get(0), vectors.get(1));
                    renderResult(result);
                    break;
                }
                case "scalarMultiplication": {
                    //TODO
                    break;
                }
                case "angle": {
                    result = doubleToVector(vectorController.angle(vectors.get(0), vectors.get(1)));
                    renderResult(result);
                    break;
                }
                case "scalarProjection": {
                    result = doubleToVector(vectorController.scalarProjection(vectors.get(0), vectors.get(1)));
                    renderResult(result);
                    break;
                }
                case "vectorProjection": {
                    result = vectorController.projection(vectors.get(0), vectors.get(1));
                    renderResult(result);
                    break;
                }
                default: {
                    alertHelper("Warning: Unsupported Operation");
                }
            }
        } catch (UnavailableVectorException e) {
            alertHelper(e.getMessage());
        } catch (Exception e) {
            alertHelper("Unexpected Exception: " + e.getMessage());
        }
    }


    private List<Vector> vectorConverter() {
        List<Vector> vectors = new ArrayList<>();

        outerloop:
        for (List<EditText> ets: inputs) {
            List<Double> components = new ArrayList<>();
            for (EditText et: ets){
                String input = et.getText().toString();
                try {
                    components.add(Double.parseDouble(input));
                } catch (RuntimeException e) {
                    alertHelper(e.getMessage());
                    break outerloop;
                }

            }
            vectors.add(vectorController.createVector(components));
        }
        return vectors;
    }

    private Vector doubleToVector(double value) {
        return vectorController.createVector(Collections.singletonList(value));
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
            TableRow tableRow = (TableRow) findViewById(numRow);
            for (int i = 0; i < numComponent; i++) {
                tableRow.removeView(findViewById(numRow + i));
            }
            TableLayout vectorTable = (TableLayout) findViewById(R.id.vectorTable);
            vectorTable.removeView(tableRow);
            inputs.remove(numRow - 1);
            numRow--;
        }
    }

    private void setup() {

        switch (currCalculation) {
            case "norm":
            case "unitVector":
                if (numRow != 1) {
                    flushInput();
                    setRows(1);
                }

                break;
            default:
                if (numRow != 2) {
                    flushInput();
                    setRows(2);
                }
                break;
        }

        Button submit = (Button) findViewById(R.id.submit);
        submit.setVisibility(View.VISIBLE);
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

    private double cutDouble(double value) {
        return Double.parseDouble(String.format(DEFAULT_DELTA_VALUE, value));
    }



    // THIS METHOD WAS DELETED DUE TO THE INTERNAL CODE CHANGE
    /*
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
    */


        /* REMOVED
    private void removeRow(View view) {
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
    */
        /* NOTE: NEEDLESS IN THIS VERSION
    private List<Double> cutDouble(List<Double> doubles) {
        List<Double> newComps = new ArrayList<>();
        for (Double comp: doubles) newComps.add(cutDouble(comp));
        return newComps;
    }
    */

    /* REMOVED
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
    */

}
