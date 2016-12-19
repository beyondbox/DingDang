package com.zlkj.dingdangwuyou.utils;

/**
 * Created by Botx on 2016/10/14.
 */
public class LogHelper {
	private static boolean debug = true;
	public static int DEBUG = android.util.Log.DEBUG;

	public static void i(String tag, String msg) {
		if (debug)
			android.util.Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (debug)
			android.util.Log.e(tag, msg);
	}

	public static void e(String tag, String msg, Throwable tr) {
		if (debug)
			android.util.Log.e(tag, msg, tr);
	}

	public static void d(String tag, String msg) {
		if (debug)
			android.util.Log.d(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (debug)
			android.util.Log.v(tag, msg);
	}

	public static boolean isLoggable(String arg0, int arg1) {
		if (debug)
			return android.util.Log.isLoggable(arg0, arg1);
		else
			return false;
	}
}
