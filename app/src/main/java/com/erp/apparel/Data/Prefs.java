package com.erp.apparel.Data;

import android.content.Context;
import android.content.SharedPreferences;

public enum Prefs {
    INSTANCE;
    private static final String ApparelErp = "Apperal";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public void initPrefs(Context context) {
        preferences = context.getSharedPreferences(ApparelErp, Context.MODE_PRIVATE);
        editor = preferences.edit();
        editor.apply();
    }

    public String getFirstName() {
        return preferences.getString(PrefKey.firstName.toString(), "");
    }

    public void setFirstName(String firstName) {
        editor.putString(PrefKey.firstName.toString(), firstName);
        editor.commit();
    }

    public String getLastName() {
        return preferences.getString(PrefKey.lastName.toString(), "");
    }

    public void setLastName(String lastName) {
        editor.putString(PrefKey.lastName.toString(),lastName);
        editor.commit();
    }

    public String getUserPhone() {
        return preferences.getString(PrefKey.userPhone.toString(), "");
    }

    public void setUserPhone(String phone) {
        editor.putString(PrefKey.userPhone.toString(), phone);
        editor.commit();
    }

    public String getUserOtp() {
        return preferences.getString(PrefKey.userOtp.toString(), "");
    }

    public void setUserOtp(String userOtp) {
        editor.putString(PrefKey.userOtp.toString(), userOtp);
        editor.commit();
    }

    public String getID() {
        return preferences.getString(PrefKey.id.toString(), "");
    }

    public void setID(String id) {
        editor.putString(PrefKey.id.toString(), id);
        editor.commit();
    }

    public String getOldPassowrd() {
        return preferences.getString(PrefKey.oldpassword.toString(), "");
    }

    public void setOldPassword(String oldpassword) {
        editor.putString(PrefKey.oldpassword.toString(), oldpassword);
        editor.commit();
    }

    public boolean isLogin() {
        return preferences.getBoolean(PrefKey.isLogin.toString(), false);
    }

    public void setLoginStatus(boolean loginStatus) {
        editor.putBoolean(PrefKey.isLogin.toString(), loginStatus);
        editor.commit();
    }

    public String getCartCount() {
        return preferences.getString(PrefKey.cartCount.toString(), "0");
    }

    public void setCartCount(String cartCount) {
        editor.putString(PrefKey.cartCount.toString(), cartCount);
        editor.commit();
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }

    public void setDefaultAddress(int pos) {
        editor.putInt(PrefKey.defaultAddress.toString(), pos);
        editor.commit();
    }

    public int getDefaultAddress() {
        return preferences.getInt(PrefKey.defaultAddress.toString(), 0);
    }

    public void setDefaultAddressOne(String addressOne) {
        editor.putString(PrefKey.defaultAddressOne.toString(),addressOne);
        editor.commit();
    }


    public void setFactorysot(int factorysot) {
        editor.putInt(PrefKey.factorysot.toString(),factorysot);
        editor.commit();
    }

    public int getFactorysot() {
        return preferences.getInt(PrefKey.factorysot.toString(), 0);
    }

    public void setOverallsot(int overall) {
        editor.putInt(PrefKey.overallsot.toString(),overall);
        editor.commit();
    }

    public int getOverallsot() {
        return preferences.getInt(PrefKey.overallsot.toString(), 0);
    }

    public void setCfair(int cfair) {
        editor.putInt(PrefKey.cfair.toString(),cfair);
        editor.commit();
    }

    public int getCfair() {
        return preferences.getInt(PrefKey.cfair.toString(), 0);
    }

    public void setAirport(int airport) {
        editor.putInt(PrefKey.airport.toString(),airport);
        editor.commit();
    }

    public int getAirport() {
        return preferences.getInt(PrefKey.airport.toString(), 0);
    }

    public void setSeaport(int seaport) {
        editor.putInt(PrefKey.seaport.toString(),seaport);
        editor.commit();
    }

    public int getSeaport() {
        return preferences.getInt(PrefKey.seaport.toString(), 0);
    }


    public String getFhoEarly() {
        return preferences.getString(PrefKey.fhoEarly.toString(), "0");
    }

    public void setFhoEarly(String fhoEarly) {
        editor.putString(PrefKey.fhoEarly.toString(), fhoEarly);
        editor.commit();
    }

    public String getFhoDelay() {
        return preferences.getString(PrefKey.fhoDelayed.toString(), "0");
    }

    public void setFhodelay(String fhodelay) {
        editor.putString(PrefKey.fhoDelayed.toString(), fhodelay);
        editor.commit();
    }

    public String getFhoOntime() {
        return preferences.getString(PrefKey.fhoOntime.toString(), "0");
    }

    public void setFhoOntime(String fhoOntime) {
        editor.putString(PrefKey.fhoOntime.toString(), fhoOntime);
        editor.commit();
    }

    public String getPcdEarly() {
        return preferences.getString(PrefKey.pcdEarly.toString(), "0");
    }

    public void setPcdEarly(String pcdEarly) {
        editor.putString(PrefKey.pcdEarly.toString(), pcdEarly);
        editor.commit();
    }

    public String getPcdDelay() {
        return preferences.getString(PrefKey.pcdDelayed.toString(), "0");
    }

    public void setPcddelay(String pcddelay) {
        editor.putString(PrefKey.pcdDelayed.toString(), pcddelay);
        editor.commit();
    }


    public String getPcdOntime() {
        return preferences.getString(PrefKey.pcdOntime.toString(), "0");
    }

    public void setPcdOntime(String pcdOntime) {
        editor.putString(PrefKey.pcdOntime.toString(), pcdOntime);
        editor.commit();
    }


    public int getDelayed() {
        return preferences.getInt(PrefKey.delayed.toString(), 0);
    }

    public void setDelayed(int delayed) {
        editor.putInt(PrefKey.delayed.toString(),delayed);
        editor.commit();
    }

    public int getOngoing() {
        return preferences.getInt(PrefKey.ongoing.toString(), 0);
    }

    public void setOngoing(int ongoing) {
        editor.putInt(PrefKey.ongoing.toString(),ongoing);
        editor.commit();
    }

    public int getUpcoming() {
        return preferences.getInt(PrefKey.upcoming.toString(), 0);
    }

    public void setUpcoming(int upcoming) {
        editor.putInt(PrefKey.upcoming.toString(),upcoming);
        editor.commit();
    }

    enum PrefKey {
        userPhone,
        userOtp,
        categoryID,
        isLogin,
        cartCount,
        defaultAddress,
        defaultAddressOne,
        firstName,
        lastName,
        factorysot,
        overallsot,
        cfair,
        airport,
        seaport,
        fhoEarly,
        fhoDelayed,
        fhoOntime,
        pcdEarly,
        pcdDelayed,
        pcdOntime,
        id,
        oldpassword,
        delayed,
        ongoing,
        upcoming

    }
}
