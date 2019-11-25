package com.github.vinunair.parkinglot.util;

import java.util.StringTokenizer;

public class StringUtils {

	public static String [] convertLineToArray(String line) {
		StringTokenizer st = new StringTokenizer(line);
        String params[] = new String[st.countTokens()];
        int index=0;
        while(st.hasMoreTokens()) {
        	params[index++] = st.nextToken().trim();
        }
        return params;
	}
}
