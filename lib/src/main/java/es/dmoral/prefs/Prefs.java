/*
 * Copyright (C) 2020-21 Application Library Engineering Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.dmoral.prefs;

//import android.annotation.TargetApi;
import ohos.agp.window.dialog.ToastDialog;
// import android.content.Context;

import ohos.app.Context;
import ohos.data.preferences.Preferences;

//import android.content.SharedPreferences;
//import ohos.eventhandler.*;                     //import android.os.Build;

import ohos.data.DatabaseHelper; // for using data base helper
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import org.jetbrains.annotations.NotNull;  //import android.support.annotation.NonNull;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

/**
 * Created by grender on 7/05/16.
 */
public class Prefs {

    private static final String LENGTH = "_length";
    private static final String DEFAULT_STRING_VALUE = "";
    private static final int DEFAULT_INT_VALUE = -1;
    private static final double DEFAULT_DOUBLE_VALUE = -1d;
    private static final float DEFAULT_FLOAT_VALUE = -1f;
    private static final long DEFAULT_LONG_VALUE = -1L;
    private static final boolean DEFAULT_BOOLEAN_VALUE = false;
    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD000F00, Prefs.class.getSimpleName());
    private static Preferences sharedPreferences; //private static SharedPreferences sharedPreferences;

    private static Prefs prefsInstance;

    public Prefs(@NotNull Context context) {

        DatabaseHelper databaseHelper = new DatabaseHelper(context.getApplicationContext());
        sharedPreferences =
                databaseHelper.getPreferences(
                        context.getBundleName() + "_preferences"

                );
        //avoided the Context.MODE_PRIVATE  cause the API databaseHelper.getPreferences(String Prefname)
    }

    private Prefs(@NotNull Context context, @NotNull String preferencesName) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context.getApplicationContext());
        sharedPreferences = databaseHelper.getPreferences(
                preferencesName
        );
        //avoided the Context.MODE_PRIVATE  cause the API databaseHelper.getPreferences(String Prefname)
    }

    /**
     * @param context
     * @return Returns a 'Prefs' instance
     */

    public static Prefs with(@NotNull Context context) {
        if (prefsInstance == null) {
            prefsInstance = new Prefs(context);
        }
        return prefsInstance;
    }

    /**
     * @param context
     * @param forceInstantiation
     * @return Returns a 'Prefs' instance
     */
    public static Prefs with(@NotNull Context context, boolean forceInstantiation) {
        if (forceInstantiation) {
            prefsInstance = new Prefs(context);
        }
        return prefsInstance;
    }

    /**
     * @param context
     * @param preferencesName
     * @return Returns a 'Prefs' instance
     */
    public static Prefs with(@NotNull Context context, @NotNull String preferencesName) {
        if (prefsInstance == null) {
            prefsInstance = new Prefs(context, preferencesName);
        }
        return prefsInstance;
    }

    /**
     * @param context
     * @param preferencesName
     * @param forceInstantiation
     * @return Returns a 'Prefs' instance
     */
    public static Prefs with(@NotNull Context context, @NotNull String preferencesName,
                             boolean forceInstantiation) {
        if (forceInstantiation) {
            prefsInstance = new Prefs(context, preferencesName);
        }
        return prefsInstance;
    }


    public static Preferences getPreferences() {
        if (sharedPreferences != null) {
            return sharedPreferences;
        }
        throw new RuntimeException(
                "Prefs class not correctly instantiated. Please call Builder.setContext().build() in the Application class onCreate.");
    }
    // from here the public API's start
    // String related methods

    /**
     * @param what
     * @return Returns the stored value of 'what'
     */

    public static String read(String what) {
        return sharedPreferences.getString(what, DEFAULT_STRING_VALUE);
    }

    /**
     * @param what
     * @param defaultString
     * @return Returns the stored value of 'what'
     */
    //done
    public  static String read(String what, String defaultString) {
        return sharedPreferences.getString(what, defaultString);
    }

    /**
     * @param where
     * @param what
     */
    //done
    public static void write(String where, String what) {
        sharedPreferences.putString(where, what);
    }

    // int related methods

    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    public static int readInt(String what) {
        return sharedPreferences.getInt(what, DEFAULT_INT_VALUE);
    }

    /**
     * @param what
     * @param defaultInt
     * @return Returns the stored value of 'what'
     */
    public static int readInt(String what, int defaultInt) {
        return sharedPreferences.getInt(what, defaultInt);
    }

    /**
     * @param where
     * @param what
     */
    public static void writeInt(String where, int what) {
        sharedPreferences.putInt(where, what);
    }

    // double related methods

    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    public static double readDouble(String what) {
        if (!contains(what))
            return DEFAULT_DOUBLE_VALUE;
        return Double.longBitsToDouble(readLong(what));
    }

    /**
     * @param what
     * @param defaultDouble
     * @return Returns the stored value of 'what'
     */
    //done
    public static double readDouble(String what, double defaultDouble) {
        if (!contains(what))
            return defaultDouble;
        return Double.longBitsToDouble(readLong(what));
    }

    /**
     * @param where
     * @param what
     */
    //done
    public static  void writeDouble(String where, double what) {
        writeLong(where, Double.doubleToRawLongBits(what));
    }

    // float related methods

    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    public static float readFloat(String what) {
        return sharedPreferences.getFloat(what, DEFAULT_FLOAT_VALUE);
    }

    /**
     * @param what
     * @param defaultFloat
     * @return Returns the stored value of 'what'
     */
    public static float readFloat(String what, float defaultFloat) {
        return sharedPreferences.getFloat(what, defaultFloat);
    }

    /**
     * @param where
     * @param what
     */
    public static void writeFloat(String where, float what) {
        sharedPreferences.putFloat(where, what);
    }

    // long related methods

    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    public static long readLong(String what) {
        return sharedPreferences.getLong(what, DEFAULT_LONG_VALUE);
    }

    /**
     * @param what
     * @param defaultLong
     * @return Returns the stored value of 'what'
     */
    public static long readLong(String what, long defaultLong) {
        return sharedPreferences.getLong(what, defaultLong);
    }

    /**
     * @param where
     * @param what
     */
    public static void writeLong(String where, long what) {
        sharedPreferences.putLong(where, what);
    }

    // boolean related methods

    /**
     * @param what
     * @return Returns the stored value of 'what'
     */
    public static boolean readBoolean(String what) {
        return sharedPreferences.getBoolean(what, DEFAULT_BOOLEAN_VALUE);
    }

    /**
     * @param what
     * @param defaultBoolean
     * @return Returns the stored value of 'what'
     */
    public static boolean readBoolean(String what, boolean defaultBoolean) {
        return sharedPreferences.getBoolean(what, defaultBoolean);
    }

    /**
     * @param where
     * @param what
     */
    public static void writeBoolean(String where, boolean what) {
        sharedPreferences.putBoolean(where, what);
    }

    // String set methods

    /**
     * @param key
     * @param value
     */
    //@TargetApi(Build.VERSION_CODES.HONEYCOMB)  ---- commented this line

    // have a doubt here :



//    public void putStringSet(final String key, final Set<String> value) {
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
////
//           sharedPreferences.putStringSet(key, value);
//
////        } else {
//            // Workaround for pre-HC's lack of StringSets
//            //putOrderedStringSet(key, value);
//      //  }
//    }
    @SuppressWarnings("WeakerAccess")
    public static void putStringSet(final String key, final Set<String> value) throws IllegalArgumentException {
        try {
            getPreferences().putStringSet(key, value);
        } catch(IllegalArgumentException e) {
            HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * @param key
     * @param value
     */
//    public static void putOrderedStringSet(String key, Set<String> value) {
//        int stringSetLength = 0;
//        if (sharedPreferences.hasKey(key + LENGTH)) {    // android : SharedPreferences.contains() changed to hasKey() from Preferences.hasKey()
//            // First read what the value was
//            stringSetLength = readInt(key + LENGTH);
//        }
//        writeInt(key + LENGTH, value.size());
//        int i = 0;
//        for (String aValue : value) {
//            write(key + "[" + i + "]", aValue);
//            i++;
//        }
//        for (; i < stringSetLength; i++) {
//            // Remove any remaining values
//            remove(key + "[" + i + "]");
//        }
//    }

    /**
     * @param key
     * @param defValue
     * @return Returns the String Set with HoneyComb compatibility
     */
    // @TargetApi(Build.VERSION_CODES.HONEYCOMB)  avoiding this line since there is no buildversions for hmos
//    public Set<String> getStringSet(final String key, final Set<String> defValue) {
//
//       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            return sharedPreferences.getStringSet(key, defValue);
//        //} else {
//            // Workaround for pre-HC's missing getStringSet
//            //return getOrderedStringSet(key, defValue);
//       // }
//
//    }
//corresponding API implemented
    @SuppressWarnings("WeakerAccess")
    public static Set<String> getStringSet(final String key, final Set<String> defValue) throws IllegalArgumentException {
        try {
            return getPreferences().getStringSet(key, defValue);
        } catch(IllegalArgumentException e) {
            HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
            throw new IllegalArgumentException(e.getMessage());
        }
    }



    /**
     * @paramkey
     * @paramdefValue
     * @return Returns the ordered String Set
     */
//    public Set<String> getOrderedStringSet(String key, final Set<String> defValue) {
//        if (contains(key + LENGTH)) {
//            LinkedHashSet<String> set = new LinkedHashSet<>();
//            int stringSetLength = readInt(key + LENGTH);
//            if (stringSetLength >= 0) {
//                for (int i = 0; i < stringSetLength; i++) {
//                    set.add(read(key + "[" + i + "]"));
//                }
//            }
//            return set;
//        }
//        return defValue;
//    }

    @SuppressWarnings("WeakerAccess")
//    public static Set<String> getOrderedStringSet(String key, final Set<String> defValue) throws IllegalArgumentException {
//        Preferences prefs = getPreferences();
//        try {
//            if (prefs.hasKey(key + LENGTH)) {
//                LinkedHashSet<String> set = new LinkedHashSet<>();
//                int stringSetLength = prefs.getInt(key + LENGTH, -1);
//                if (stringSetLength >= 0) {
//                    for (int i = 0; i < stringSetLength; i++) {
//                        set.add(prefs.getString(key + "[" + i + "]", null));
//                    }
//                }
//                return set;
//            }
//        } catch(IllegalArgumentException e) {
//            HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
//            throw new IllegalArgumentException(e.getMessage());
//        }
//        return defValue;
//    }


    // end related methods

    /**
     * @param key
     */
    //implementing remove method directly from teh Easyprefs.java
//    public  void remove(final String key) {
//        if (contains(key + LENGTH)) {
//            // Workaround for pre-HC's lack of StringSets
//            int stringSetLength = readInt(key + LENGTH);
//            if (stringSetLength >= 0) {
//                sharedPreferences.remove(key + LENGTH).apply();
//                for (int i = 0; i < stringSetLength; i++) {
//                    sharedPreferences.remove(key + "[" + i + "]").apply();
//                }
//            }
//        }
//        sharedPreferences.remove(key).apply();
//    }
    private static void remove(final String key) throws IllegalArgumentException {
        Preferences prefs = getPreferences();
        try {
            if (prefs.hasKey(key + LENGTH)) {
                int stringSetLength = prefs.getInt(key + LENGTH, -1);
                if (stringSetLength >= 0) {
                    prefs.delete(key + LENGTH);
                    for (int i = 0; i < stringSetLength; i++) {
                        prefs.delete(key + "[" + i + "]");
                    }
                }
            }
            prefs.delete(key);
        } catch(IllegalArgumentException e) {
            HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    /**
     * @param key
     * @return Returns if that key exists
     */
    public static boolean contains(final String key) throws IllegalArgumentException {
        try {
            boolean flag = getPreferences().hasKey(key);
            if(!flag) {
                flag = sharedPreferences.hasKey(key + LENGTH);
            }

            if(!flag) {
                flag = sharedPreferences.hasKey(key + "[" + 0 + "]");
            }
            return flag;
        } catch(IllegalArgumentException e) {
            HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    /**
     * Clear all the preferences
     */
    //public void clear() {
    //  sharedPreferences.clear();
    //}.


//    public static Preferences clear() { //one button
//        final Preferences editor = getPreferences().clear();
//        return editor;
//    }


    public static void clear() { //one button
//        final Preferences editor =
        getPreferences().clear();
//        return editor;
    }

    private static Preferences edit() {
        return getPreferences();
    }


}
// there no methods for edit() and clear() in android