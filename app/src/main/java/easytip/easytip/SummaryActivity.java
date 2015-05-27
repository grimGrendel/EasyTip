package easytip.easytip;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.RatingBar;



public class SummaryActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Set a stylish font for the view title
        TextView txt = (TextView) findViewById(R.id.summary_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "Lobster-Regular.ttf");
        txt.setTypeface(font);

        // Get data from intent and calculate values to display
        Intent intent = getIntent();

        String billAmount = intent.getStringExtra(Welcome.BILL_AMOUNT);
        String tipPercent = intent.getStringExtra(Welcome.TIP_PERCENTAGE);
        String serviceRating = intent.getStringExtra(Welcome.SERVICE_RATING);
        String numPersons = intent.getStringExtra(Welcome.NUM_PERSONS);

        calculateBillAmount(billAmount, tipPercent, serviceRating, numPersons);
    }

    /*
     *   Calculating the total bill, the tip, and the amount each person pays
     *   and displays those in the proper textViews
     */
    private void calculateBillAmount(String initialBillTxt, String tipPercentTxt,
                                        String ratingTxt, String numPersonsTxt) {

        /* TODO refer each value to the now protected variables in Welcome to avoid NullPointerErrors.
        *  (The variables are : billAmountET, tipPercentET, and ratingRB) I'm working on it.
        */

        final double initialBill = Double.parseDouble(String.valueOf(initialBillTxt));
        double tipPercent = 0.00;
        double rating = 0.00;

        double totalTip = 0.00;
        // if tip by value has been inputted
        if (tipPercentTxt.length() > 0) {
            tipPercent = Double.parseDouble(String.valueOf(tipPercentTxt));
            totalTip = initialBill*(tipPercent)/100;
        }else{
            rating = Double.parseDouble(String.valueOf(ratingTxt));
            totalTip = initialBill*(10 + 2*rating)/100;
        }
        final int numPersons = Integer.parseInt(String.valueOf(numPersonsTxt));

        // Calculate total bill
        double totalBill = initialBill + totalTip;

        // Calculate initial bill per person
        double initialBillPerPerson = initialBill / numPersons;

        // Calculate tip per person
        double totalTipPerPerson = totalTip / numPersons;

        // Calculate bill per person
        double billPerPerson = totalBill / numPersons;

        // Display results for one bill
        TextView summaryTotalAmountField = (TextView) findViewById(R.id.summaryTotalAmountField);
        summaryTotalAmountField.setText(String.format("%.2f", initialBill));

        TextView summaryTotalTipField = (TextView) findViewById(R.id.summaryTotalTipField);
        summaryTotalTipField.setText(String.format("%.2f", totalTip));

        TextView summaryGrandTotalField = (TextView) findViewById(R.id.summaryGrandTotalField);
        summaryGrandTotalField.setText(String.format("%.2f", totalBill));

        // Display results for many bills
        TextView summaryTotalAmountPerPersonField = (TextView) findViewById(R.id.summaryAmountPerPersonField);
        summaryTotalAmountPerPersonField.setText(String.format("%.2f", initialBillPerPerson));

        TextView summaryTotalTipPerPersonField = (TextView) findViewById(R.id.summaryTipPerPersonField);
        summaryTotalTipPerPersonField.setText(String.format("%.2f", totalTipPerPerson));

        TextView summaryGrandTotalPerPersonField = (TextView) findViewById(R.id.summaryGrandTotalPerPersonField);
        summaryGrandTotalPerPersonField.setText(String.format("%.2f", billPerPerson));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
