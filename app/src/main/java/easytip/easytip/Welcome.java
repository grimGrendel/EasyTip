package easytip.easytip;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;


public class Welcome extends ActionBarActivity {

    public final static String BILL_AMOUNT = "easyTip.BILL_AMOUNT";
    public final static String TIP_PERCENTAGE = "easyTip.TIP_PERCENTAGE";
    public final static String SERVICE_RATING = "easyTip.SERVICE_RATING";
    public final static String NUM_PERSONS = "easyTip.NUM_PERSONS";

    private EditText billAmount;
    private EditText tipPercent;
    private EditText numPersons;
    private RatingBar serviceRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        TextView txt = (TextView) findViewById(R.id.welcome_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "Lobster-Regular.ttf");
        txt.setTypeface(font);

        // get the bill amount input
        billAmount = (EditText) findViewById(R.id.welcome_bill_amount_input_field);

        // get the input for number of persons
        numPersons = (EditText) findViewById(R.id.welcome_number_persons);

        // get the tip percentage input
        // When the tip percentage is set, it reset the star rating to 0
        tipPercent = (EditText) findViewById(R.id.welcome_tip_amount_input_field);
        tipPercent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!String.valueOf(s).equals(""))
                    serviceRating.setRating(0f);
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
                tipPercent.setText("", TextView.BufferType.EDITABLE);
            }
        });
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void welcomeSubmitButtonClicked(View view) {

        // Changes layout from Welcome to Summary
        Intent summaryIntent = new Intent (this, SummaryActivity.class);

        summaryIntent.putExtra("callingSummaryActivity", "Welcome");
        summaryIntent.putExtra(BILL_AMOUNT, billAmount.getText().toString());
        summaryIntent.putExtra(TIP_PERCENTAGE, tipPercent.getText().toString());
        summaryIntent.putExtra(SERVICE_RATING, String.valueOf(serviceRating.getRating()));
        summaryIntent.putExtra(NUM_PERSONS, numPersons.getText().toString());

        startActivity(summaryIntent);

    }
}
