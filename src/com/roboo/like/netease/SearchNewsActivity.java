package com.roboo.like.netease;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

public class SearchNewsActivity extends BaseActivity
{
	private TelephonyManager mTelephonyManager;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setTVTitle(R.string.tv_search_news);
		showSearchBtn(true);
		this.mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = mTelephonyManager.getDeviceId();
		String softVersion = mTelephonyManager.getDeviceSoftwareVersion();
		String phoneType = mTelephonyManager.getPhoneType()+"";
		String networkType = mTelephonyManager.getNetworkType()+"";
		String simSerialNumber= mTelephonyManager.getSimSerialNumber();
		String subscriberId = mTelephonyManager.getSubscriberId(); 
		 List<NeighboringCellInfo> cellInfo = mTelephonyManager.getNeighboringCellInfo();
		 StringBuffer sb = new StringBuffer();
		 if(null != cellInfo )
		 {
			 for(NeighboringCellInfo c: cellInfo)
			 {
				sb.append(c.getCid() + " :: " + c.getLac() + "  :: " + c.getNetworkType() + " :: " + c.getPsc() + " :: " + c.getRssi() ); 
			 }
		 }
		String networkInfo = mTelephonyManager.getNetworkCountryIso() + " :: " 
			+ mTelephonyManager.getNetworkOperator() + " :: " + mTelephonyManager.getNetworkOperatorName();
	

		 GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
		System.out.println("deviceId = " + deviceId + " softVersion = " + softVersion +
			( null == location ?"":(" location = " + location + " cid =  " + location.getCid() + " lac =  " + location.getLac() + " psc = " + location.getPsc()))
			 +" phoneType = " +phoneType + " networkType = " + networkType + " networkInfo = " + networkInfo
			 + " simSerialNumber = " + simSerialNumber 
			 + " subscriberId =  " + subscriberId 
			 + " sb = " + sb.toString());
	}
	@Override
	public boolean onSearchRequested()
	{
		System.out.println("onSearchRequested method execute");
		return super.onSearchRequested();
		
	}
}
