package es.udc.pa.pa008.practicapa.model.bidservice;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SuppressWarnings("serial")
public class TimeExpiredException extends Exception {

    private Calendar endDate;

    public TimeExpiredException(Calendar endDate) {

        super("Bid time has expired on => " + dateFormat(endDate));
        this.endDate = endDate;
    }

    public Calendar getEndDate() {
        return endDate;
    }

    private static String dateFormat(Calendar endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
        return sdf.format(endDate.getTime());
    }

}
