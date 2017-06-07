package com.oasis.firebird.util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class CommonUtils {

	private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final int SALT_BYTE_SIZE = 24;
	private static final int HASH_BYTE_SIZE = 24;
	private static final int PBKDF2_ITERATIONS = 1000;
	private static final int ITERATION_INDEX = 0;
	private static final int SALT_INDEX = 1;
	private static final int PBKDF2_INDEX = 2;
	private static final String SEPARATOR = ",";

	public static String getSimpleCalendarDescription(Calendar calendar) {

		return getSimpleCalendar(calendar, new SimpleDateFormat("dd.MM.yyyy '@' HH:mm", Locale.ENGLISH), false);

	}

	public static String getSimpleCalendarDescriptionServer(Calendar calendar) {

		if (calendar.get(Calendar.SECOND) == 0) {
			return getSimpleCalendar(calendar, new SimpleDateFormat("dd.MM.yyyy '@' HH:mm", Locale.ENGLISH), false);
		} else {
			return getSimpleCalendar(calendar, new SimpleDateFormat("dd.MM.yyyy '@' HH:mm:ss", Locale.ENGLISH), true);
		}

	}

	public static String getSimpleCalendar(Calendar calendar, SimpleDateFormat dateFormat, Boolean withSeconds) {

		Calendar previous = Calendar.getInstance();
		previous.add(Calendar.DAY_OF_MONTH, -1);

		boolean today = calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		boolean yesterday = calendar.get(Calendar.YEAR) == previous.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == previous.get(Calendar.DAY_OF_YEAR);

		if (today) {

			if (withSeconds) {
				dateFormat = new SimpleDateFormat("'Today' '@' HH:mm:ss", Locale.ENGLISH);
			} else {
				dateFormat = new SimpleDateFormat("'Today' '@' HH:mm", Locale.ENGLISH);
			}

		} else if (yesterday) {

			if (withSeconds) {
				dateFormat = new SimpleDateFormat("'Yesterday' '@' HH:mm:ss", Locale.ENGLISH);
			} else {
				dateFormat = new SimpleDateFormat("'Yesterday' '@' HH:mm", Locale.ENGLISH);
			}

		}

		return dateFormat.format(calendar.getTime());

	}

	public static String getSimpleCalendarDayName(Calendar calendar) {

		Calendar previous = Calendar.getInstance();
		previous.add(Calendar.DAY_OF_YEAR, -1);

		Calendar week = Calendar.getInstance();
		week.add(Calendar.DAY_OF_YEAR, -6);

		boolean today = calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		boolean yesterday = calendar.get(Calendar.YEAR) == previous.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == previous.get(Calendar.DAY_OF_YEAR);
		boolean withInWeek = calendar.get(Calendar.YEAR) == week.get(Calendar.YEAR) && (calendar.get(Calendar.DAY_OF_YEAR) > week.get(Calendar.DAY_OF_YEAR)) && ((calendar.get(Calendar.DAY_OF_YEAR) - week.get(Calendar.DAY_OF_YEAR)) < 7);

		SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy @ HH:mm", Locale.ENGLISH);

		if (today) {

			dateFormat = new SimpleDateFormat("'Today' @ HH:mm", Locale.ENGLISH);

		} else if (yesterday) {

			dateFormat = new SimpleDateFormat("'Yesterday' @ HH:mm", Locale.ENGLISH);

		} else if (withInWeek) {
			dateFormat = new SimpleDateFormat("EEEE @ HH:mm", Locale.ENGLISH);
		}

		return dateFormat.format(calendar.getTime());

	}

	public static String getSimpleCalendarDateName(Calendar calendar) {

		Calendar previous = Calendar.getInstance();
		previous.add(Calendar.DAY_OF_YEAR, -1);

		Calendar week = Calendar.getInstance();
		week.add(Calendar.DAY_OF_YEAR, 7);

		boolean today = calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		boolean yesterday = calendar.get(Calendar.YEAR) == previous.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == previous.get(Calendar.DAY_OF_YEAR);
		boolean withInWeek = calendar.get(Calendar.YEAR) == week.get(Calendar.YEAR) && ((week.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR)) < 7) && ((week.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR)) > 0);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

		if (today) {

			dateFormat = new SimpleDateFormat("'Today'", Locale.ENGLISH);

		} else if (yesterday) {

			dateFormat = new SimpleDateFormat("'Yesterday'", Locale.ENGLISH);

		} else if (withInWeek) {
			dateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
		}

		return dateFormat.format(calendar.getTime());

	}

	public static String getSimpleCalendarDateNameFuture(Calendar calendar) {

		Calendar next = Calendar.getInstance();
		next.add(Calendar.DAY_OF_YEAR, 1);

		Calendar week = Calendar.getInstance();
		week.add(Calendar.DAY_OF_YEAR, 7);

		boolean today = calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
		boolean tomorrow = calendar.get(Calendar.YEAR) == next.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == next.get(Calendar.DAY_OF_YEAR);
		boolean withInWeek = calendar.get(Calendar.YEAR) == week.get(Calendar.YEAR) && (calendar.get(Calendar.DAY_OF_YEAR) > week.get(Calendar.DAY_OF_YEAR)) && ((calendar.get(Calendar.DAY_OF_YEAR) - week.get(Calendar.DAY_OF_YEAR)) < 7);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);

		if (today) {

			dateFormat = new SimpleDateFormat("'Today'", Locale.ENGLISH);

		} else if (tomorrow) {

			dateFormat = new SimpleDateFormat("'Tomorrow'", Locale.ENGLISH);

		} else if (withInWeek) {
			dateFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
		}

		return dateFormat.format(calendar.getTime());

	}

	public static String getSimpleCalendarMonth(Calendar calendar) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM", Locale.ENGLISH);
		return dateFormat.format(calendar.getTime());

	}

	public static String getSimpleCalendarDay(Calendar calendar) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("d", Locale.ENGLISH);
		return dateFormat.format(calendar.getTime());

	}

	public static String getSimpleCalendarYear(Calendar calendar) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
		return dateFormat.format(calendar.getTime());

	}

	public static Integer getPercentage(Integer total, Integer count) {

		double fraction = (double) count / (double) total;
		return (int) (fraction * 100.0);

	}

	public static double distance(double lat1, double lon1, double lat2, double lon2, Unit unit) {

		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		if (unit == Unit.KILOMETERS) {
			dist = dist * 1.609344;
		} else if (unit == Unit.NAUTICAL_MILES) {
			dist = dist * 0.8684;
		}

		return (dist);

	}

	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public static String createHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return createHash(password.toCharArray());
	}

	public static String createHash(char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {

		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[SALT_BYTE_SIZE];
		random.nextBytes(salt);

		byte[] hash = pbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
		
	}

	public static boolean validatePassword(String password, String correctHash) throws NoSuchAlgorithmException, InvalidKeySpecException {
		return validatePassword(password.toCharArray(), correctHash);
	}

	public static boolean validatePassword(char[] password, String correctHash) throws NoSuchAlgorithmException, InvalidKeySpecException {

		String[] params = correctHash.split(":");
		int iterations = Integer.parseInt(params[ITERATION_INDEX]);
		byte[] salt = fromHex(params[SALT_INDEX]);
		byte[] hash = fromHex(params[PBKDF2_INDEX]);

		byte[] testHash = pbkdf2(password, salt, iterations, hash.length);
		return slowEquals(hash, testHash);
		
	}

	private static boolean slowEquals(byte[] a, byte[] b) {
		
		int diff = a.length ^ b.length;
		for (int i = 0; i < a.length && i < b.length; i++)
			diff |= a[i] ^ b[i];
		return diff == 0;
		
	}

	private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
		
		PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		return skf.generateSecret(spec).getEncoded();
		
	}

	private static byte[] fromHex(String hex) {
		
		byte[] binary = new byte[hex.length() / 2];
		for (int i = 0; i < binary.length; i++) {
			binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return binary;
		
	}

	private static String toHex(byte[] array) {
		
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0)
			return String.format("%0" + paddingLength + "d", 0) + hex;
		else
			return hex;
		
	}
	
	public static String getStringFromList(List<Long> ids) {
		
		String string = "";
		
		for (Long id : ids) {
			string += id + SEPARATOR;
		}

		if (string.length() >= 1) {
			string = string.substring(0, string.length() - 1);
		}
		
		return string;
		
	}
	
	public static List<Long> getListFromString(String string) {
		
		List<Long> ids = new ArrayList<>();
		
		if (string != null) {
		
			String[] split = string.split(SEPARATOR);
		
			for (String s : split) {
				
				if (s.length() > 0) {
					ids.add(Long.valueOf(s));
				}
				
			}
		
		}
		
		return ids;
		
	}

	public static String getString(List<String> ids) {

		String string = "";

		for (String id : ids) {
			string += id + SEPARATOR;
		}

		if (string.length() >= 1) {
			string = string.substring(0, string.length() - 1);
		}

		return string.equals("") ? null : string;

	}

	public static List<String> getList(String string) {

		List<String> ids = new ArrayList<>();

		if (string != null) {

			String[] split = string.split(SEPARATOR);

			for (String s : split) {

				if (s.length() > 0) {
					ids.add(s);
				}

			}

		}

		return ids;

	}
	
	public static long getDaysSince(Calendar calendar) {

		Calendar now = Calendar.getInstance();
		
		long difference = now.getTimeInMillis() - calendar.getTimeInMillis();
		long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
		
		return days;
		
	}
	
	public static double round(double value, int places) {
		
	    if (places < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	    
	}

	public static Calendar getISO8601Date(String date) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
		Calendar calendar = Calendar.getInstance();

		try {
			calendar.setTimeInMillis(simpleDateFormat.parse(date).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return calendar;
	}

	public static String getISO8601Date(Calendar date) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
		return simpleDateFormat.format(date.getTime());

	}

	public static Size resize(int width, int height, int size) {

		float ratio = (float) width / (float) height;

		int w = size;
		int h = size;

		if (width > height) {
			h = (int) (ratio * ((float) size));
		} else if (width < height) {
			w = (int) (ratio * ((float) size));
		}
		return new Size(w, h);
	}

	public static long getDaysBetween(Calendar start, Calendar end) {

		Date startDate = start.getTime();
		Date endDate = end.getTime();
		long startTime = startDate.getTime();
		long endTime = endDate.getTime();
		long diffTime = endTime - startTime;

		return (diffTime / (1000 * 60 * 60 * 24)) + 1;

	}

	public static int nullSafeStringComparator(final String one, final String two) {
		if (one == null ^ two == null) {
			return (one == null) ? -1 : 1;
		}

		if (one == null && two == null) {
			return 0;
		}

		return one.compareToIgnoreCase(two);
	}

	public static Double trimDouble(String f) {

		f = f.replace(",", "");
		f = f.replace(" ", "");
		f = f.replace("[^0-9.]+", "");
		f = f.replaceAll("\\s+","");

		return Double.valueOf(f);

	}

}
