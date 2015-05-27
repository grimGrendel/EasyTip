package easytip.easytip;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.content.SharedPreferences;

/**
 * Created by pascaltremblay-lecomte on 15-05-27.
 */
public class PreferenceActivity extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        EditText defaultTipPercent = (EditText) findViewById(R.id.preferences_default_percent_tip);

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        String restoredText = prefs.getString("settings", null);

        if (restoredText != null)
        {
            String savedTipPercent = prefs.getString("preferences_default_percent_tip", "");
            defaultTipPercent.setText(savedTipPercent);

        } else {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("preferences_default_percent_tip",
                    String.valueOf(defaultTipPercent.getText()));
            editor.apply();
        }
    }

    protected void onStop() {
        super.onStop();

        /*
        TODO This method will be used to put the settings into the SharedPreferences object for persistence
         */
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
}
