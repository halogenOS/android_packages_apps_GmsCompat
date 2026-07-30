package com.google.android.compat.lib.util;

import android.content.Context;

/**
 * Holds the host app's Context, captured when the library is initialised.
 *
 * The framework's android.app.compat.gms.GmsCompat.appContext() serves the
 * same purpose, but that method is not part of the exposed API surface, so
 * the ART hidden-API verifier blocks it in the library's classloader domain
 * (NoSuchMethodError at runtime). Keeping our own handle sidesteps that
 * entirely.
 */
public final class LibContext {
    public static volatile Context app;

    /** GosPackageState flags used by the Play Integrity flow. The named
     * constants in GosPackageStateFlag are not part of the exposed API
     * surface either, so they are mirrored here as literals. */
    public static final int FLAG_PLAY_INTEGRITY_API_USED_AT_LEAST_ONCE = 26;
    public static final int FLAG_SUPPRESS_PLAY_INTEGRITY_API_NOTIF = 27;
    public static final int FLAG_BLOCK_PLAY_INTEGRITY_API = 28;

    private LibContext() {}
}
