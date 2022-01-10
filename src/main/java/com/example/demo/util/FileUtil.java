package com.example.demo.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	public static String join(String... paths) {
		if ( Util.isEmpty(paths) ) {
			return "";
		}

		printCheckRoot(paths);

		StringBuilder builder = new StringBuilder();
		for ( String path : paths ) {
			if ( !builder.isEmpty() ) {
				builder.append(sep());
			}
			builder.append(changeSeparator(path));
		}
		return builder.toString();
	}
	
	private static void printCheckRoot(String[] paths) {
		if ( paths.length == 0 ) {
			return;
		}
		String target = paths[0];
		if ( Util.isEmpty(target) ) {
			return;
		}

		if ( isXnix() ) {
			if ( target.indexOf(":\\") != -1 ) {
				logger.warn("パス指定にWindowsのドライブ指定があるようです {}",target);
			}
		} else {
			if ( target.indexOf("/") != -1 ) {
				logger.warn("パス指定にxnix系のドライブ指定があるようです {}",target);
			}
		}
	}

	private static String changeSeparator(String path) {
		if ( Util.isEmpty(path) ) {
			return path;
		}
		return path.replaceAll(replaceSepOther(), replaceSep());
	}

	private static String replaceSep() {
		String sep = sep();
		if ( Util.equals(sep, "/") ) {
			return "\\/";
		}
		return "\\\\";
	}

	private static String replaceSepOther() {
		String sep = sep();
		if ( Util.equals(sep, "/") ) {
			return "\\\\";
		}
		return "\\/";
	}

	private static String sep() {
		return File.separator;
	}

	public static boolean isXnix() {
		if ( Util.equals(sep(), "/") ) {
			return true;
		}
		return false;
	}
}
