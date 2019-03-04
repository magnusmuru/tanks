package tanks.client;

import tanks.client.networking.Packet;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String getPatternGroupOrNull(String msg, String pattern, int group) {
        Pattern r = Pattern.compile(pattern);
        return r.matcher(msg).group(group);
    }


}
