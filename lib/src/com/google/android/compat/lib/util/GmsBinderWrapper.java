package com.google.android.compat.lib.util;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ShellCallback;

import java.io.FileDescriptor;

/**
 * Lib-local copy of the framework's android.os.BinderWrapper. The framework
 * class is @hide, which makes it unresolvable for third-party apps: when this
 * library is loaded into a GMS-client app's process, any class extending the
 * framework BinderWrapper fails to link with NoSuchMethodError. Delegating
 * through this identical lib-local class keeps the wrapper usable in every
 * classloader domain.
 */
public class GmsBinderWrapper implements IBinder {
    protected final IBinder base;

    public GmsBinderWrapper(IBinder base) {
        this.base = base;
    }

    @Override
    public boolean transact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        return base.transact(code, data, reply, flags);
    }

    @Nullable
    @Override
    public IInterface queryLocalInterface(@NonNull String descriptor) {
        return base.queryLocalInterface(descriptor);
    }

    @Nullable
    @Override
    public String getInterfaceDescriptor() throws RemoteException {
        return base.getInterfaceDescriptor();
    }

    @Override
    public boolean pingBinder() {
        return base.pingBinder();
    }

    @Override
    public boolean isBinderAlive() {
        return base.isBinderAlive();
    }

    @Override
    public void dump(@NonNull FileDescriptor fd, @Nullable String[] args) throws RemoteException {
        base.dump(fd, args);
    }

    @Override
    public void dumpAsync(@NonNull FileDescriptor fd, @Nullable String[] args) throws RemoteException {
        base.dumpAsync(fd, args);
    }

    @Override
    public void shellCommand(@Nullable FileDescriptor in, @Nullable FileDescriptor out, @Nullable FileDescriptor err, @NonNull String[] args, @Nullable ShellCallback shellCallback, @NonNull ResultReceiver resultReceiver) throws RemoteException {
        base.shellCommand(in, out, err, args, shellCallback, resultReceiver);
    }

    @Override
    public void linkToDeath(@NonNull DeathRecipient recipient, int flags) throws RemoteException {
        base.linkToDeath(recipient, flags);
    }

    @Override
    public boolean unlinkToDeath(@NonNull DeathRecipient recipient, int flags) {
        return base.unlinkToDeath(recipient, flags);
    }

    @Nullable
    public IBinder getExtension() throws RemoteException {
        return base.getExtension();
    }

    @Override
    public void addFrozenStateChangeCallback(@NonNull FrozenStateChangeCallback callback) throws RemoteException {
        base.addFrozenStateChangeCallback(callback);
    }

    @Override
    public boolean removeFrozenStateChangeCallback(@NonNull FrozenStateChangeCallback callback) {
        return base.removeFrozenStateChangeCallback(callback);
    }
}
