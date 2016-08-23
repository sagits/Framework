package atrativaweb.com.br.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;

import atrativaweb.com.br.framework.R;


/**
 * Created by Renato on 15/04/2016.
 */
public class Utils {

    public static String formatCurrencyAsString(Float value) {
        NumberFormat nf = NumberFormat.getCurrencyInstance();

        String valueAsString = value.toString();

        if (valueAsString.contains(".")) {
            if (valueAsString.substring(valueAsString.indexOf(".") + 1).length() < 2) {
                valueAsString = valueAsString + "0";
            }
        } else {
            valueAsString = valueAsString + ".00";
        }

        BigDecimal valueAsBD = BigDecimal.valueOf(value);
        valueAsBD.setScale(2, BigDecimal.ROUND_HALF_UP);

        String formated = nf.format(valueAsBD);
        if (value < 0 && !formated.contains("-") && String.valueOf(value).contains("-")) {
            formated = "-" + formated;
            formated = formated.replace("(", "");
            formated = formated.replace(")", "");
        }
        return formated;
    }

    public static void generateKeyHash(Context context) {
        // Add code to print out the key hash
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "renatoprobst.digitalgourmet",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static void showSuccessMessage(Activity activity, View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.green));
        snackbar.show();
    }

    public static void showErrorMessage(Activity activity, View view, String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(activity, R.color.primary));
        snackbar.show();
    }
}
