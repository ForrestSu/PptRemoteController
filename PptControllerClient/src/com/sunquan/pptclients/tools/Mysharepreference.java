package com.sunquan.pptclients.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class Mysharepreference {

	 
	public  static String DEFAULT_SERVER_IP="192.168.0.1";
	public  static String DEFAULT_SERVER_PORT="60000";
    //1.�洢�������������
	public static boolean saveMessage(String key,String value,Context context) {
		boolean flag = false;
		// ��������������ļ���һ�㲻����׺����Ĭ�ϻᱣ���.xml���ļ�
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		// �����ݽ��б༭
		SharedPreferences.Editor editor=sharedPreferences.edit();
		editor.remove(key);
		editor.putString(key, value);
		flag=editor.commit();//�����ݳ־û����洢����  �����Ǵ洢������sd��
		return flag;
	}
	//2.��ȡ�������������
	public static String getMessage(String key,Context context)
	{
		SharedPreferences sharedPreferences=context.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
		//��ù������
	 
		String name=sharedPreferences.getString(key, null);//null��һ��������ʾȱʡֵ
		
		if(name==null)
		{
			if(key.equals("ip"))
				return DEFAULT_SERVER_IP;
			if(key.equals("port"))
				return DEFAULT_SERVER_PORT;
		}
		return name;
		
		
	}

}
