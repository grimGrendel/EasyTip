package easytip.easytip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.content.SharedPreferences;

/**
 * Created by pascaltremblay-lecomte on 15-05-27.
 */
public class PreferenceActivity extends ActionBarActivity {

    SharedPreferences prefs;
    final public static String SETTINGS_KEY = "settings";
    final public static String PERCENT_TIP_KEY = "preferences_default_percent_tip";
    EditText defaultTipPercent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        defaultTipPercent = (EditText) findViewById(R.id.preferences_default_percent_tip);

        prefs = getSharedPreferences(SETTINGS_KEY, MODE_PRIVATE);
        String savedTipPercent = prefs.getString(PERCENT_TIP_KEY, null);

        if (savedTipPercent != null)
        {
            defaultTipPercent.setText(savedTipPercent);

        } else {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(PERCENT_TIP_KEY,
                    String.valueOf(defaultTipPercent.getText()));
            editor.commit();
        }
    }

    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PERCENT_TIP_KEY,
                String.valueOf(defaultTipPercent.getText()));
        editor.commit();
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
            finish ();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void preferencesDoneClick(View view) {
        finish();
    }
}
