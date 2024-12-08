package com.github.shadowsocks.plugin.obfs_local;

import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import be.mygod.preference.EditTextPreference;
import be.mygod.preference.PreferenceFragment;
import com.github.shadowsocks.plugin.PluginContract;
import com.github.shadowsocks.plugin.PluginOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mygod
 */
public class ConfigFragment extends PreferenceFragment {
    PluginOptions options = null;

    private static final List<Pair<String, String>> OPTIONS_List = new ArrayList<>();

    static {
        OPTIONS_List.add(new Pair<>("obfs", "http"));
        OPTIONS_List.add(new Pair<>("obfs-host", "cloudfront.net"));
        OPTIONS_List.add(new Pair<>("obfs-uri", "/"));
    }


    @Override
    public void onCreatePreferences(Bundle bundle, String key) {
        addPreferencesFromResource(R.xml.config);
    }

    void onInitializePluginOptions(PluginOptions options) {
        this.options = options;

        Preference pref;
        for (Pair<String, String> pair : OPTIONS_List) {
            pref = findPreference(pair.first);
            if (pref == null) {
                continue;
            }
            if (pref instanceof DropDownPreference) {
                DropDownPreference ddp = (DropDownPreference) pref;
                ddp.setValue(options.getOrDefault(pair.first, pair.second));
            }
            if (pref instanceof EditTextPreference) {
                EditTextPreference etp = (EditTextPreference) pref;
                etp.setText(options.getOrDefault(pair.first, pair.second));
            }

            pref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    options.put(preference.getKey(), newValue.toString());
                    return true;
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PluginContract.EXTRA_OPTIONS, options.toString());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            options = new PluginOptions(savedInstanceState.getString(PluginContract.EXTRA_OPTIONS));
            onInitializePluginOptions(options);
        }
    }
}
