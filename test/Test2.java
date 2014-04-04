import java.util.Calendar;

import com.gwinsoft.util.GwinSoftUtil;


public class Test2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(GwinSoftUtil.getLastDayOfMonth());
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		int daySize = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		System.out.println(daySize);
	}

}
