package notebook.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Максим on 08.04.2017.
 */
public class DateHandler {
    private static final String DAY_EVENT = "dd/MM/yyyy";
    private static final String PERIOD_EVENT = "dd/MM/yyyy HH:mm";

    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat(DAY_EVENT);
        return formatter.format(date);
    }

    public static Date stringToDate(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat(DAY_EVENT);
        format.setLenient(false);

        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

}
