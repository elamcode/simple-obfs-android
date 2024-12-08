package com.github.shadowsocks.plugin.obfs_local;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import com.github.shadowsocks.plugin.NativePluginProvider;
import com.github.shadowsocks.plugin.PathProvider;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Mygod
 */
public final class BinaryProvider extends NativePluginProvider implements IConstants {

    @Override
    public void populateFiles(PathProvider provider) {
        provider.addPath("obfs-local", "755");
    }

    @Override
    public String getExecutable() {
        return getContext().getApplicationInfo().nativeLibraryDir + "/libobfs-local.so";
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri) {
        final String uriPath = uri.getPath();
        Log.d(TAG, "openFile uriPath:%s", uriPath);
        if ("/obfs-local".equals(uriPath)) {
            try {
                String executable = getExecutable();
                Log.d(TAG, "openFile executable:%s", executable);
                return ParcelFileDescriptor.open(new File(executable), ParcelFileDescriptor.MODE_READ_ONLY);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "openFile", e);
                return null;
            }
        }

        return null;
    }
}
