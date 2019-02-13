package tanks.client;

import java.text.DecimalFormat;

public class Utils {
    public static String formatDoubleToDecimals(double input, int decimalCount) {
        DecimalFormat df = new DecimalFormat();
        String pattern = null;

        if (decimalCount == 0) {
            pattern = "##";
        } else if (decimalCount > 0) {
            pattern = "##." + String.format(new String(new char[decimalCount]).replace("\0", "#"));
        } else {
            return null;
        }

        df.applyPattern(pattern);
        return df.format(input);
    }
}
