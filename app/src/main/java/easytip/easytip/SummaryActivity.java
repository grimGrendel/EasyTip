package easytip.easytip;


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

        TextView txt = (TextView) findViewById(R.id.summary_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "Lobster-Regular.ttf");
        txt.setTypeface(font);

        // Calculate and display the bill
       // calculateBillAmount();
    }

    /*
     *   Calculating the total bill, the tip, and the amount each person pays
     *   and displays those in the proper textViews
     */
    private void calculateBillAmount() {
        /* TODO refer each value to the now protected variables in Welcome to avoid NullPointerErrors.
        *  (The variables are : billAmountET, tipPercentET, and ratingRB) I'm working on it.
        */

        // Get the user inputs
        EditText etInput = (EditText) findViewById(R.id.welcome_bill_amount_input_field);
        final double initialBill = Double.parseDouble(String.valueOf(etInput.getText()));

        etInput = (EditText) findViewById(R.id.welcome_tip_amount_input_field);
        final double tipPercent = Double.parseDouble(String.valueOf(etInput.getText()));

        RatingBar rtInput = (RatingBar) findViewById(R.id.welcome_rating_bar);
        final double rating = Double.parseDouble(String.valueOf(rtInput.getRating()));

        etInput = (EditText) findViewById(R.id.welcome_number_persons);
        final int numPersons = Integer.parseInt(String.valueOf(etInput.getText()));
        final double OFFSET = 0.001;

        // Calculate total tip
        double totalTip = initialBill*(tipPercent);
        if (rating > OFFSET)
            totalTip = initialBill*(10 + 2*rating);

        // Calculate total bill
        double totalBill = initialBill + totalTip;

        // Calculate bill per person
        double billPerPerson = totalBill / numPersons;

        // Display everything
        TextView totalAmountTV = (TextView) findViewById(R.id.textView1);
        totalAmountTV.setText(String.valueOf(initialBill));

        TextView totalTipTV = (TextView) findViewById(R.id.textView2);
        totalTipTV.setText(String.valueOf(totalTip));

        TextView totalBillTV = (TextView) findViewById(R.id.textView3);
        totalBillTV.setText(String.valueOf(totalBill));
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
