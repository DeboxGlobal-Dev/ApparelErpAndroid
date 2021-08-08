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

    public String getCategoryID() {
        return preferences.getString(PrefKey.categoryID.toString(), "");
    }

    public void setCategoryID(String userOtp) {
        editor.putString(PrefKey.categoryID.toString(), userOtp);
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


    enum PrefKey {
        userPhone,
        userOtp,
        categoryID,
        isLogin,
        cartCount,
        defaultAddress,
        defaultAddressOne,
        firstName,
        lastName


    }
}
