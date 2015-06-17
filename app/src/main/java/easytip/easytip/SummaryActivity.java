package easytip.easytip;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;



public class SummaryActivity extends ActionBarActivity {

    private TextView[] currencySymbol = new TextView[6];
    private String tipType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set a stylish font for the view & app title
        TextView summaryTitle = (TextView) findViewById(R.id.summary_title);
        TextView appTitle = (TextView) findViewById(R.id.toolbar_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "Lobster-Regular.ttf");
        appTitle.setTypeface(font);
        summaryTitle.setTypeface(font);

        // Get data from intent and calculate values to display
        Intent intent = getIntent();

        String billAmount = intent.getStringExtra(Welcome.BILL_AMOUNT);
        String tipPercent = intent.getStringExtra(Welcome.TIP_PERCENTAGE);
        String serviceRating = intent.getStringExtra(Welcome.SERVICE_RATING);
        String numPersons = intent.getStringExtra(Welcome.NUM_PERSONS);
        tipType = intent.getStringExtra(Welcome.TIP_TYPE_CHOSEN);

        calculateBillAmount(billAmount, tipPercent, serviceRating, numPersons);

        // get the currencySymbol text views
        currencySymbol[0] = (TextView) findViewById(R.id.currency_symbol_1);
        currencySymbol[1] = (TextView) findViewById(R.id.currency_symbol_2);
        currencySymbol[2] = (TextView) findViewById(R.id.currency_symbol_3);
        currencySymbol[3] = (TextView) findViewById(R.id.currency_symbol_4);
        currencySymbol[4] = (TextView) findViewById(R.id.currency_symbol_5);
        currencySymbol[5] = (TextView) findViewById(R.id.currency_symbol_6);

        SharedPreferences prefs = getSharedPreferences(PreferenceActivity.SETTINGS_KEY, MODE_PRIVATE);
        String savedCurrency = prefs.getString(PreferenceActivity.CURRENCY_SYMBOL_KEY, null);

        if(savedCurrency != null) {
            switch (savedCurrency) {
                case "EURO":
                    setCurrencySymbols("€");
                    break;
                case "POUND":
                    setCurrencySymbols("£");
                    break;
                default:
                    setCurrencySymbols("$");
                    break;
            }
        } else {
            setCurrencySymbols("$");
        }
    }

    private void setCurrencySymbols(String currencySymbol){
        for(TextView currencySymbolTextView : this.currencySymbol){
            currencySymbolTextView.setText(currencySymbol);
        }
    }

    /*
     *   Calculating the total bill, the tip, and the amount each person pays
     *   and displays those in the proper textViews
     */
    private void calculateBillAmount(String initialBillTxt, String tipPercentTxt,
                                        String ratingTxt, String numPersonsTxt) {

        final double initialBill;
        if (initialBillTxt.length() > 0)
            initialBill = Double.parseDouble(String.valueOf(initialBillTxt));
        else
            initialBill = 0;
        double tipPercent = 0.00;
        double rating = 0.00;

        double totalTip = 0.00;
        // if tip by value has been inputted
        if (tipType.equals(Welcome.TIP_TYPE_PERCENTAGE)) {
            tipPercent = Double.parseDouble(String.valueOf(tipPercentTxt));
            totalTip = initialBill*(tipPercent)/100;
        } else if (Double.parseDouble(String.valueOf(ratingTxt)) > 0.1){
            rating = Double.parseDouble(String.valueOf(ratingTxt));
            totalTip = initialBill*(10 + 2*rating)/100;
        } else {
            SharedPreferences prefs = getSharedPreferences(PreferenceActivity.SETTINGS_KEY, MODE_PRIVATE);
            String savedTipPercent = prefs.getString(PreferenceActivity.PERCENT_TIP_KEY, null);

            tipPercent = Double.parseDouble(String.valueOf(savedTipPercent));
            totalTip = initialBill*(tipPercent)/100;
        }
        final int numPersons;
        if (numPersonsTxt.length() > 0)
           numPersons = Integer.parseInt(String.valueOf(numPersonsTxt));
        else numPersons = 1;

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
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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
            Intent preferenceIntent = new Intent (this, PreferenceActivity.class);
            preferenceIntent.putExtra("callingSummaryActivity", "Welcome");
            startActivity(preferenceIntent);
            return true;
        } else if (id == R.id.action_exit) {
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
