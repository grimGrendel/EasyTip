package easytip.easytip;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;


public class Welcome extends Activity {

    protected EditText billAmountET;
    protected EditText tipPercentET;
    protected RatingBar ratingRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView txt = (TextView) findViewById(R.id.welcome_title);
        Typeface font = Typeface.createFromAsset(getAssets(), "Lobster-Regular.ttf");
        txt.setTypeface(font);

        // get the bill amount
        billAmountET = (EditText) findViewById(R.id.welcome_bill_amount_input_field);

        // get the tip percentage
        // When the tip percentage is set, it reset the star rating to 0
        tipPercentET = (EditText) findViewById(R.id.welcome_tip_amount_input_field);
        tipPercentET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!String.valueOf(s).equals(""))
                    ratingRB.setRating(0f);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // get the star rating
        // When the star rating is set, it reset the tip percentage to 0
        ratingRB = (RatingBar) findViewById(R.id.welcome_rating_bar);
        ratingRB.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tipPercentET.setText("", TextView.BufferType.EDITABLE);
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
        Intent getSummaryScreenIntent = new Intent (this, SummaryActivity.class);

        final int result = 1;

        getSummaryScreenIntent.putExtra("callingSummaryActivity", "Welcome");

        startActivity(getSummaryScreenIntent);

    }
}
