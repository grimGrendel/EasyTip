package easytip.easytip;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.content.SharedPreferences;


public class Welcome extends ActionBarActivity {

    public final static String BILL_AMOUNT = "easyTip.BILL_AMOUNT";
    public final static String TIP_PERCENTAGE = "easyTip.TIP_PERCENTAGE";
    public final static String SERVICE_RATING = "easyTip.SERVICE_RATING";
    public final static String NUM_PERSONS = "easyTip.NUM_PERSONS";
    public final static String TIP_TYPE_CHOSEN = "easyTip.TIP_TYPE_CHOSEN";
    public final static String TIP_TYPE_PERCENTAGE = "easyTip.TIP_TYPE_PERCENTAGE";
    public final static String TIP_TYPE_STAR_RATING = "easyTip.TIP_TYPE_STAR_RATING";

    private EditText billAmount;
    private EditText tipPercent;
    private EditText numPersons;
    private RatingBar serviceRating;

    private TextView currencySymbol;
    private String tipType = TIP_TYPE_PERCENTAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set a stylish font for a app title, screen title & calculate bill button
        TextView appTitle = (TextView) findViewById(R.id.toolbar_title);
        TextView tipAlternative = (TextView) findViewById(R.id.welcome_tip_alternative);
        Button calculateBillBtn =(Button) findViewById(R.id.welcome_calculate_bill_button);

        Typeface font = Typeface.createFromAsset(getAssets(), "Lobster-Regular.ttf");

        appTitle.setTypeface(font);
        tipAlternative.setTypeface(font);
        calculateBillBtn.setTypeface(font);

        // get the bill amount input
        billAmount = (EditText) findViewById(R.id.welcome_bill_amount_input_field);

        // get the input for number of persons
        numPersons = (EditText) findViewById(R.id.welcome_number_persons);

        // get the currencySymbol text views
        currencySymbol = (TextView) findViewById(R.id.currency_symbol);

        // get the tip percentage input
        // When the tip percentage is set, it reset the star rating to 0
        tipPercent = (EditText) findViewById(R.id.welcome_tip_amount_input_field);
        tipPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!String.valueOf(s).equals("")) {
                    serviceRating.setRating(0f);

                    tipType = TIP_TYPE_PERCENTAGE;
                    TextView byServiceRating = (TextView) findViewById(R.id.welcome_by_service_quality);
                    TextView byPercentage = (TextView) findViewById(R.id.welcome_by_percentage);
                    byPercentage.setText(getResources().getString(R.string.welcome_tip_percentage_field_chosen));
                    byServiceRating.setText(getResources().getString(R.string.welcome_service_quality_field));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // get the star rating
        // When the star rating is set, it reset the tip percentage to 0
        serviceRating = (RatingBar) findViewById(R.id.welcome_rating_bar);
        serviceRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tipType = TIP_TYPE_STAR_RATING;
                TextView byServiceRating = (TextView) findViewById(R.id.welcome_by_service_quality);
                TextView byPercentage = (TextView) findViewById(R.id.welcome_by_percentage);
                byPercentage.setText(getResources().getString(R.string.welcome_tip_percentage_field));
                byServiceRating.setText(getResources().getString(R.string.welcome_service_quality_field_chosen));
            }
        });

        // make the done action available when editing text in soft keyboard
        billAmount.setImeOptions(EditorInfo.IME_ACTION_DONE);
        numPersons.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tipPercent.setImeOptions(EditorInfo.IME_ACTION_DONE);

        billAmount.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(billAmount, InputMethodManager.SHOW_IMPLICIT);

        // when user clicks outside edit text inputs the keyboard will hide itself
        billAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard("billAmount");
                }
            }
        });

        tipPercent.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard("percentage");
                }
            }
        });

        numPersons.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard("numPersons");
                }
            }
        });
    }

    private void hideKeyboard(String editText) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        switch(editText){
            case "billAmount":
                imm.hideSoftInputFromWindow(billAmount.getWindowToken(), 0);
                break;
            case "percentage":
                imm.hideSoftInputFromWindow(tipPercent.getWindowToken(), 0);
                break;
            case "numPersons":
                imm.hideSoftInputFromWindow(numPersons.getWindowToken(), 0);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getSharedPreferences(PreferenceActivity.SETTINGS_KEY, MODE_PRIVATE);
        String savedTipPercent = prefs.getString(PreferenceActivity.PERCENT_TIP_KEY, null);

        tipPercent.setText(savedTipPercent);

        String savedCurrency = prefs.getString(PreferenceActivity.CURRENCY_SYMBOL_KEY, null);

        if(savedCurrency != null) {
            switch (savedCurrency) {
                case "EURO":
                    currencySymbol.setText("€");
                    break;
                case "POUND":
                    currencySymbol.setText("£");
                    break;
                default:
                    currencySymbol.setText("$");
                    break;
            }
        } else {
            currencySymbol.setText("$");
        }
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

    public void minusPercentageButtonClicked(View view){
        //tipPercent = (EditText) findViewById(R.id.welcome_tip_amount_input_field);
        int initialNumPersons = Integer.parseInt(tipPercent.getText().toString());
        if(initialNumPersons > 1)
            initialNumPersons--;
        tipPercent.setText(Integer.toString(initialNumPersons));
    }

    public void plusPercentageButtonClicked(View view){
       // tipPercent = (EditText) findViewById(R.id.welcome_tip_amount_input_field);
        int initialTipPercent = Integer.parseInt(tipPercent.getText().toString());
        initialTipPercent++;
        tipPercent.setText(Integer.toString(initialTipPercent));
    }

    public void minusNumPersonButtonClicked(View view){
       // numPersons = (EditText) findViewById(R.id.welcome_number_persons);
        int initialNumPersons = Integer.parseInt(numPersons.getText().toString());
        if(initialNumPersons > 1)
            initialNumPersons--;
        numPersons.setText(Integer.toString(initialNumPersons));
    }

    public void plusNumPersonButtonClicked(View view){
       // numPersons = (EditText) findViewById(R.id.welcome_number_persons);
        int initialNumPersons = Integer.parseInt(numPersons.getText().toString());
        initialNumPersons++;
        numPersons.setText(Integer.toString(initialNumPersons));
    }

    public void welcomeSubmitButtonClicked(View view) {

        // Changes layout from Welcome to Summary
        Intent summaryIntent = new Intent (this, SummaryActivity.class);

        summaryIntent.putExtra("callingSummaryActivity", "Welcome");
        summaryIntent.putExtra(TIP_TYPE_CHOSEN, tipType);
        summaryIntent.putExtra(BILL_AMOUNT, billAmount.getText().toString());
        summaryIntent.putExtra(TIP_PERCENTAGE, tipPercent.getText().toString());
        summaryIntent.putExtra(SERVICE_RATING, String.valueOf(serviceRating.getRating()));
        summaryIntent.putExtra(NUM_PERSONS, numPersons.getText().toString());

        startActivity(summaryIntent);

    }
}
