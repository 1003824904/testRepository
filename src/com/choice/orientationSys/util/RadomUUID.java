package com.choice.orientationSys.util;

import java.util.UUID;

public class RadomUUID {
	public static String createUUID(){
		return String.valueOf(UUID.randomUUID()).replaceAll("-", "");
	}
}
