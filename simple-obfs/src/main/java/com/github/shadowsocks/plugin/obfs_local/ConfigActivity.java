package com.github.shadowsocks.plugin.obfs_local;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import com.github.shadowsocks.plugin.PluginContract;
import com.github.shadowsocks.plugin.PluginOptions;

/**
 * @author Mygod
 */
public class ConfigActivity extends FragmentActivity implements Toolbar.OnMenuItemClickListener, IConstants {

    private ConfigFragment mChild;
    private PluginOptions mOptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_navigation_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.inflateMenu(R.menu.menu_config);
        toolbar.setOnMenuItemClickListener(this);

        mOptions = generatePluginOptions(getIntent());

        mChild = (ConfigFragment) getFragmentManager().findFragmentById(R.id.content);
        mChild.onInitializePluginOptions(mOptions);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        final int itemId = item.getItemId();
        if (itemId == R.id.action_apply) {
            saveChanges(mChild.options);
            finish();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (mChild.options != mOptions) {
            new AlertDialog.Builder(this)//
                    .setTitle(R.string.unsaved_changes_prompt)//
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            saveChanges(mChild.options);
                        }
                    })//
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            discardChanges();
                            finish();
                        }
                    })//
                    .setNeutralButton(android.R.string.cancel, null)//
                    .create()//
                    .show();
        } else {
            super.onBackPressed();
        }
    }

    private PluginOptions generatePluginOptions(Intent intent) {
        final String optionsText = intent.getStringExtra(PluginContract.EXTRA_OPTIONS);
        Log.d(TAG, "generatePluginOptions text:%s", optionsText);
        return new PluginOptions(optionsText);
    }

    private Intent generateCallbackIntent() {
        final String optionsText = mOptions.toString();
        Log.d(TAG, "generatePluginOptions text:%s", optionsText);
        Intent intent = new Intent();
        intent.putExtra(PluginContract.EXTRA_OPTIONS, optionsText);
        return intent;
    }

    public void saveChanges(PluginOptions options) {
        setResult(-1, generateCallbackIntent());
    }

    private void discardChanges() {
        setResult(0);
    }
}
