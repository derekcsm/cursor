package com.derek_s.cursor;

import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class ActMain extends ActionBarActivity {

    public static String text_key = "TEXT_CONTENT";
    public static String mode_key = "NIGHT_MODE";
    private TinyDB db;
    private EditText et_main;
    private TextView tv_count;
    private RelativeLayout rl_main;
    private Menu mMenu;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.old_act_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mToolbar.getBackground().setAlpha(150);

        et_main = (EditText) findViewById(R.id.et_main);
        tv_count = (TextView) findViewById(R.id.tv_count);
        rl_main = (RelativeLayout) findViewById(R.id.rl_main);
        et_main.addTextChangedListener(mTextEditorWatcher);

        Typeface font =Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        et_main.setTypeface(font);
        tv_count.setTypeface(font);

        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data != null) {
            et_main.setText(data.toString());
        } else {
            db = new TinyDB(this);
            et_main.setText(db.getString(text_key));
        }

        if (db.getBoolean(mode_key)) {
            setMode(mode.NIGHT);
        } else {
            setMode(mode.DAY);
        }
    }

    @Override
    protected void onPause() {
        db.putString(text_key, et_main.getText().toString());

        super.onPause();
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String[] words = s.toString().split(" ");
            if (words.length == 1 && et_main.getText().toString().equals("")) {
                tv_count.setText(String.valueOf(s.length()) + " CHAR / 0 WORDS");
            } else if (words.length == 1 && !et_main.getText().toString().equals("")) {
                tv_count.setText(String.valueOf(s.length()) + " CHAR / 1 WORD");
            } else {
                tv_count.setText(String.valueOf(s.length()) + " CHAR / " + words.length + " WORDS");
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_act_main, menu);
        mMenu = menu;
        mMenu.findItem(R.id.action_night_mode).setChecked(db.getBoolean(mode_key));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_copy_all:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboard.setText(et_main.getText().toString());
                Toast.makeText(this, R.string.copied_text_successful, Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_night_mode:
                if (item.isChecked()) {
                    setMode(mode.DAY);
                    item.setChecked(false);
                } else {
                    setMode(mode.NIGHT);
                    item.setChecked(true);
                }
                break;
            case R.id.action_clear:
                confirmClear();
                break;
            case R.id.action_share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, et_main.getText().toString());
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
                break;
            case R.id.action_about:
                new MaterialDialog.Builder(this)
                        .title(R.string.app_name)
                        .positiveText(R.string.dismiss)
                        .content(Html.fromHtml(getString(R.string.about_body)))
                        .build()
                        .show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public enum mode {
        NIGHT, DAY
    }

    private void setMode(mode m) {
        switch (m) {
            case NIGHT:
                db.putBoolean(mode_key, true);
                rl_main.setBackgroundColor(getResources().getColor(R.color.night_bg));
                et_main.setTextColor(getResources().getColor(R.color.night_text));
                tv_count.setTextColor(getResources().getColor(R.color.night_text));
                tv_count.setBackgroundColor(getResources().getColor(R.color.night_info));
                break;
            case DAY:
                rl_main.setBackgroundColor(getResources().getColor(R.color.light_bg));
                et_main.setTextColor(getResources().getColor(R.color.light_text));
                tv_count.setTextColor(getResources().getColor(R.color.light_text));
                tv_count.setBackgroundColor(getResources().getColor(R.color.light_info));
                db.putBoolean(mode_key, false);
                break;
        }
    }

    private void confirmClear() {
        new MaterialDialog.Builder(this)
                .title(R.string.confirm_clear)
                .content(R.string.confirm_clear_message)
                .positiveText(R.string.yes)
                .negativeText(R.string.no)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        et_main.setText("");
                        db.putString(text_key, "");
                    }
                })
                .show();
    }

}
