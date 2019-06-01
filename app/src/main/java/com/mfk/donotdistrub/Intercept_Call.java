package com.mfk.donotdistrub;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import static java.lang.Thread.sleep;


public class Intercept_Call extends BroadcastReceiver{



    @Override
    public void onReceive(Context context, Intent intent) {

        final String ACTION = "android.provider.Telephony.SEND_SMS";


        try {
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            String MSG_TYPE = intent.getAction();
            boolean result=false;
            for(int i=0;i<MainActivity.blockList.size();i++)
            {

            ///    MainActivity.blockList.get(i).id.toString();
                if((MainActivity.blockList.get(i).phone.compareToIgnoreCase(incomingNumber)==0 )&& MainActivity.blockList.get(i).dayto==-1)
                {
                    result=true;
                    if(MainActivity.blockList.get(i).busy==1) {
                        sleep(5000);
                        try {
                            SmsManager.getDefault().sendTextMessage(incomingNumber, null,
                                    "Mai!", null, null);
                        } catch (Exception e) {

                        }
                        result = true;
                        break;
                    }
                }
                else {
                    if (incomingNumber.compareToIgnoreCase(MainActivity.blockList.get(i).phone)==0) {
                        Calendar calendar = Calendar.getInstance();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int time = calendar.get(Calendar.HOUR_OF_DAY);
                        if ((MainActivity.blockList.get(i).dayfrom >= day && day<=MainActivity.blockList.get(i).dayto) && ( MainActivity.blockList.get(i).timefrom >= time&&time<=MainActivity.blockList.get(i).timefrom)) {
                            result = true;
                            break;
                        } else {
                            result = false;
                        }
                    }
                }  }

            if (result) {

                    System.out.println("--------------my number---------" + incomingNumber);
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    Class <?> classTelephony = Class.forName(telephonyManager.getClass().getName());
                    Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");
                    methodGetITelephony.setAccessible(true);
                    Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);
                    Class <?> telephonyInterfaceClass = Class.forName(telephonyInterface.getClass().getName());
                    Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
                    methodEndCall.invoke(telephonyInterface);
                    setResultData(null);
                }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

///////////////////////////////////////////////////////////////////////

