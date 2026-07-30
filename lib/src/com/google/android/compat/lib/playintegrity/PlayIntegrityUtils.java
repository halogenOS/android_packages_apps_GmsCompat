package com.google.android.compat.lib.playintegrity;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.GosPackageState;
import android.ext.PackageId;
import android.os.IBinder;

import java.util.function.UnaryOperator;

import com.google.android.compat.lib.util.ServiceConnectionWrapper;

public class PlayIntegrityUtils {
    private static final String TAG = "PlayIntegrityUtils";

    public static ServiceConnection maybeReplaceServiceConnection(Intent service, ServiceConnection orig) {
        if (PackageId.PLAY_STORE_NAME.equals(service.getPackage())) {
            UnaryOperator<IBinder> binderOverride = null;

            final String CLASSIC_SERVICE =
                    "com.google.android.play.core.integrityservice.BIND_INTEGRITY_SERVICE";
            final String STANDARD_SERVICE =
                    "com.google.android.play.core.expressintegrityservice.BIND_EXPRESS_INTEGRITY_SERVICE";

            String action = service.getAction();
            if (STANDARD_SERVICE.equals(action)) {
                binderOverride = StandardPlayIntegrityServiceWrapper::new;
            } else if (CLASSIC_SERVICE.equals(action)) {
                binderOverride = ClassicPlayIntegrityServiceWrapper::new;
            }

            if (binderOverride != null) {
                return new ServiceConnectionWrapper(orig, binderOverride);
            }
        }
        return null;
    }

    static boolean isPlayIntegrityBlocked() {
        // Mirror of android.ext.settings.app.AswBlockPlayIntegrityApi.I.get():
        // default is "not blocked"; the per-app switch sets the
        // BLOCK_PLAY_INTEGRITY_API GosPackageState flag. That class (and the
        // flag constants) are not part of the exposed API surface, so they
        // cannot be referenced directly from this library's classloader
        // domain.
        Context ctx = com.google.android.compat.lib.util.LibContext.app;
        return GosPackageState.getForSelf(ctx)
                .hasFlag(com.google.android.compat.lib.util.LibContext.FLAG_BLOCK_PLAY_INTEGRITY_API);
    }
}
