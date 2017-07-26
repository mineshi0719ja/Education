package jp.co.sss.shop.util;

import java.util.Calendar;

/**
 * 日付の妥当性チェック用クラス
 *
 * @author System Shared
 */
public class DateCheck {
	/**
	 * 指定された年月が現在よりも過去であるかを判定
	 *
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return true:現在より過去、false:現在、もしくは未来
	 */
	public static boolean isBeforeDate(String year, String month) {

		boolean isBefore = false;
		Calendar targetDate = Calendar.getInstance();
		Calendar currentDate = Calendar.getInstance();

		StringBuilder sb = new StringBuilder("20");
		sb.append(year);

		targetDate.set(Calendar.YEAR, Integer.parseInt(sb.toString()));
		targetDate.set(Calendar.MONTH, Integer.parseInt(month) - 1);

		if (targetDate.before(currentDate)) {
			isBefore = true;
		}

		return isBefore;
	}

}
