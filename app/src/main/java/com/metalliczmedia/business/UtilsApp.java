package com.metalliczmedia.business;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by user on 01-04-2017.
 */

public class UtilsApp {

    public static  final String BASE_URL="http://env-1692647.ind-cloud.everdata.com/user/";
    public static final String REGISTER_URL=BASE_URL+"register";
    public static final String GARAGE_LIST=BASE_URL+"search";
    public static final String MY_ORDER=BASE_URL+"userorderhistory";
    public static final String LOGIN_USER=BASE_URL+"login";

    public static final String SAVE_USER=BASE_URL+"update/location";
    public static final String SAVE_STORE=BASE_URL+"store";
    public static final String Order_History_Admin=BASE_URL+"orderhistory";
    public static final String SEARCH_BY_KEYWORD=BASE_URL+"searchbykeyword";
    public static final String UPDATE_PROFILE=BASE_URL+"updateprofile";
    public static final String PRICE_LIST=BASE_URL+"pricelist";
    public static final String ACCEPTED_REQUEST=BASE_URL+"accepted";
    public static final String GARAGE_UPDATE_PROFILE=BASE_URL+"updategarage";
    public static final String CANCEL_REQUEST=BASE_URL+"cancelorder";
    public static final String BOOKORDER=BASE_URL+"bookorder";
    public static final String IMAGE_BASEURL="http://garage.teuso.com/";
    public static final String PRODUCT_ADD=BASE_URL+"productadd";
    public static final String FORGOT_EMAIL_VERIFY=BASE_URL+"forgetpassword";
    public static final String OTP_VERIFY=BASE_URL+"otpvarify";
    public static final String CHANGE_PASSWORD=BASE_URL+"cforgetpassword";
    public static final String STATES=BASE_URL+"states";
    public static final String DELETE_PRICE=BASE_URL+"delproduct";
    public static final String CITYES=BASE_URL+"cities";
    public static final String GARAGE_USER_LIST=BASE_URL+"garageuserlist";
    public static final String FEEDBACK_URL=BASE_URL+"feedback";

    public static final String GARAGE_STATUS_URL=BASE_URL+"garageisclosed";



    public static boolean emailValidator(final String mailAddress) {

        Pattern pattern;
        Matcher matcher;

        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(mailAddress);
        return matcher.matches();

    }

    public static void setupUI(View view, final Activity context) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(context);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView,context);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);

        if (activity.getCurrentFocus() != null)
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


}
