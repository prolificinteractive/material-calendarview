package com.persiancalendar; /**
 * Persian Calendar see: http://code.google.com/p/persian-calendar/
   Copyright (C) 2012  Mortezaadi@gmail.com
   PersianDateParser.java
   
   Persian Calendar is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


/**
 * Parses text from the beginning of the given string to produce a
 * PersianCalendar.
 * 
 * <p>
 * See the {@link #getPersianDate()} method for more information on date
 * parsing.
 * 
 * <pre>
 *                Example
 *                     
 *  {@code
 *    PersianCalendar pCal =
 *     new PersianDateParser("1361/3/1").getPersianDate();             
 *  }
 * </pre>
 * 
 * @author Morteza contact: <a
 *         href="mailto:Mortezaadi@gmail.com">Mortezaadi@gmail.com</a>
 * @version 1.0
 */
public class PersianDateParser {

	private String dateString;
	private String delimiter = "/";

	/**
	 * <pre>
	 * construct parser with date string assigned
	 * the default delimiter is '/'.
	 * 
	 * To assign deferment delimiter use:
	 * {@link #PersianDateParser(String dateString, String delimiter)}
	 * 
	 *                     Example
	 *                     
	 *  {@code
	 *    PersianCalendar pCal =
	 *     new PersianDateParser("1361/3/1").getPersianDate();             
	 *  }
	 * </pre>
	 * 
	 * @param dateString String that has value like "1361/3/1"
	 */
	public PersianDateParser(String dateString) {
		this.dateString = dateString;
	}

	/**
	 * <pre>
	 * construct parser with date string assigned
	 * the default delimiter is '/'. with this constructor
	 * you can set different delimiter to parse the date
	 * based on this delimiter.
	 * see also:
	 * {@link #PersianDateParser(String dateString)}
	 * 
	 *                     Example
	 *                     
	 *  {@code
	 *    PersianCalendar pCal =
	 *     new PersianDateParser("1361-3-1","-").getPersianDate();             
	 *  }
	 * </pre>
	 * 
	 * @param dateString String date
	 * @param delimiter delimiter
	 */
	public PersianDateParser(String dateString, String delimiter) {
		this(dateString);
		this.delimiter = delimiter;
	}

	/**
	 * Produce the PersianCalendar object from given DateString throws Exception
	 * if couldn't parse the text.
	 * 
	 * @return PersianCalendar object
	 * @exception RuntimeException
	 */
	public PersianCalendar getPersianDate() {

		checkDateStringInitialValidation();

		String tokens[] = splitDateString(normalizeDateString(dateString));
		int year = Integer.parseInt(tokens[0]);
		int month = Integer.parseInt(tokens[1]);
		int day = Integer.parseInt(tokens[2]);

		checkPersianDateValidation(year, month, day);

		PersianCalendar pCal = new PersianCalendar();
		pCal.setPersianDate(year, month, day);

		return pCal;
	}

	/**
	 * validate the given date
	 * 
	 * @param year year
	 * @param month month
	 * @param day day
	 */
	private void checkPersianDateValidation(int year, int month, int day) {
		if (year < 1)
			throw new RuntimeException("year is not valid");
		if (month < 1 || month > 12)
			throw new RuntimeException("month is not valid");
		if (day < 1 || day > 31)
			throw new RuntimeException("day is not valid");
		if (month > 6 && day == 31)
			throw new RuntimeException("day is not valid");
		if (month == 12 && day == 30 && !PersianCalendarUtils.isPersianLeapYear(year))
			throw new RuntimeException("day is not valid " + year + " is not a leap year");
	}

	/**
	 * planned for further calculation before parsing the text
	 * 
	 * @param dateString string date
	 * @return right date format
	 */
	private String normalizeDateString(String dateString) {
		// dateString = dateString.replace("-", delimiter);
		return dateString;
	}

	private String[] splitDateString(String dateString) {
		String tokens[] = dateString.split(delimiter);
		if (tokens.length != 3)
			throw new RuntimeException("wrong date:" + dateString + " is not a Persian Date or can not be parsed");
		return tokens;
	}

	private void checkDateStringInitialValidation() {
		if (dateString == null)
			throw new RuntimeException("input didn't assing please use setDateString()");
		// if(dateString.length()>10)
		// throw new RuntimeException("wrong date:" + dateString +
		// " is not a Persian Date or can not be parsed" );
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

}
