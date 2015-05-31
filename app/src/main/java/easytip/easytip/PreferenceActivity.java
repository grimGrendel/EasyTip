package easytip.easytip;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by pascaltremblay-lecomte on 15-05-27.
 */
public class PreferenceActivity extends ActionBarActivity {

    SharedPreferences prefs;
    final public static String SETTINGS_KEY = "settings";
    final public static String PERCENT_TIP_KEY = "preferences_default_percent_tip";
    final public static String CURRENCY_SYMBOL_KEY = "currency_symbol";

    EditText defaultTipPercent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // Set a toolbar to replace the action bar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set a stylish font for the view & app title
        TextView settingsTitle = (TextView) findViewById(R.id.settings_title);
        TextView appTitle = (TextView) findViewById(R.id.toolbar_title);
        TextView doneButton = (TextView) findViewById(R.id.preferences_done_button);

        Typeface font = Typeface.createFromAsset(getAssets(), "Lobster-Regular.ttf");
        appTitle.setTypeface(font);
        settingsTitle.setTypeface(font);
        doneButton.setTypeface(font);

        defaultTipPercent = (EditText) findViewById(R.id.preferences_default_percent_tip);

        prefs = getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE);
        String savedTipPercent = prefs.getString(PERCENT_TIP_KEY, null);
        String savedCurrencySymbol = prefs.getString(CURRENCY_SYMBOL_KEY, null);

        if (savedTipPercent != null)
        {
            defaultTipPercent.setText(savedTipPercent);

        } else {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PERCENT_TIP_KEY,
                    String.valueOf(defaultTipPercent.getText()));
            editor.commit();
        }

        RadioButton currencyRadioButton = null;
        if(savedCurrencySymbol != null){

            switch(savedCurrencySymbol) {
                case "DOLLAR":
                    updateCurrencySymbol("DOLLAR");
                    currencyRadioButton = (RadioButton) findViewById(R.id.radio_currency_dollar);
                    currencyRadioButton.setChecked(true);
                    break;
                case "EURO":
                    updateCurrencySymbol("EURO");
                    currencyRadioButton = (RadioButton) findViewById(R.id.radio_currency_euro);
                    currencyRadioButton.setChecked(true);
                    break;
                case "POUND":
                    updateCurrencySymbol("POUND");
                    currencyRadioButton = (RadioButton) findViewById(R.id.radio_currency_pound);
                    currencyRadioButton.setChecked(true);
                    break;
            }
        } else {
            currencyRadioButton = (RadioButton) findViewById(R.id.radio_currency_dollar);
            currencyRadioButton.setChecked(true);
            updateCurrencySymbol("DOLLAR");
        }
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PERCENT_TIP_KEY,
                String.valueOf(defaultTipPercent.getText()));
        editor.commit();
    }

    public void minusPercentageButtonClicked(View view){
        defaultTipPercent = (EditText) findViewById(R.id.preferences_default_percent_tip);
        int initialTipPercentage = Integer.parseInt(defaultTipPercent.getText().toString());
        if(initialTipPercentage > 1)
            initialTipPercentage--;
        defaultTipPercent.setText(Integer.toString(initialTipPercentage));
    }

    public void plusPercentageButtonClicked(View view){
        defaultTipPercent = (EditText) findViewById(R.id.preferences_default_percent_tip);
        int initialTipPercentage = Integer.parseInt(defaultTipPercent.getText().toString());
        initialTipPercentage++;
        defaultTipPercent.setText(Integer.toString(initialTipPercentage));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preferences, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onCurrencyButtonClicked(View view){
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_currency_dollar:
                if (checked)
                    updateCurrencySymbol("DOLLAR");
                    break;
            case R.id.radio_currency_euro:
                    if (checked)
                    updateCurrencySymbol("EURO");
                    break;
            case R.id.radio_currency_pound:
                if (checked)
                    updateCurrencySymbol("POUND");
                    break;
        }
    }

    private void updateCurrencySymbol(String currencySymbol) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CURRENCY_SYMBOL_KEY, currencySymbol);
        editor.commit();
    }

    public void preferencesDoneClick(View view) {
        Intent i=new Intent(this, Welcome.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
