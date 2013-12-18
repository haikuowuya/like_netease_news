/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\android_workspace\\like_netease_news\\src\\com\\roboo\\like\\netease\\aidl\\ISocketService.aidl
 */
package com.roboo.like.netease.aidl;
public interface ISocketService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.roboo.like.netease.aidl.ISocketService
{
private static final java.lang.String DESCRIPTOR = "com.roboo.like.netease.aidl.ISocketService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.roboo.like.netease.aidl.ISocketService interface,
 * generating a proxy if needed.
 */
public static com.roboo.like.netease.aidl.ISocketService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.roboo.like.netease.aidl.ISocketService))) {
return ((com.roboo.like.netease.aidl.ISocketService)iin);
}
return new com.roboo.like.netease.aidl.ISocketService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_printMsg:
{
data.enforceInterface(DESCRIPTOR);
this.printMsg();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.roboo.like.netease.aidl.ISocketService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void printMsg() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_printMsg, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_printMsg = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void printMsg() throws android.os.RemoteException;
}
