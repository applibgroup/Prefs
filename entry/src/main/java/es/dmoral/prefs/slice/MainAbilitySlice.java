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


package es.dmoral.prefs.slice;

import es.dmoral.prefs.MainAbility;
import es.dmoral.prefs.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;

import es.dmoral.prefs.Prefs;
import es.dmoral.prefs.TextUtils;

import ohos.agp.components.*;
import ohos.agp.window.dialog.ToastDialog;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class MainAbilitySlice extends AbilitySlice implements Component.ClickedListener {


    private static final HiLogLabel LABEL_LOG = new HiLogLabel(3, 0xD000F00, MainAbility.class.getSimpleName());

    public static final String SAVED_TEXT = "saved_text";
    public static final String SAVED_NUMBER = "saved_number";
    public static final String SAVED_NUMBER_INT = "saved_number_int";
    public static final String SAVED_NUMBER_LONG = "saved_number_long";
    public static final String SAVED_NUMBER_FLOAT = "saved_number_float";
    public static final String SAVED_SWITCH = "saved_boolean";

    public static final String SAVED_STRINGSET = "saved_stringset";





    private static final String FROM_INSTANCE_STATE = " : from instance state";

    private Switch switch1;
    private TextField et_text, et_number,et_number_int , et_number_long,et_number_float,et_stringset;
    private Button bt_save_text, bt_save_number, bt_force_close ,bt_save_number_int,bt_save_number_long,bt_save_number_float,bt_save_stringset,bt_clear;

    private Text tv_saved_text, tv_saved_number , tv_saved_number_int ,tv_saved_number_long,tv_saved_number_float , tv_saved_stringset;



    @Override
    public void onStart(Intent intent) {


        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);


        Prefs prefs = new Prefs(getApplicationContext());

        ComponentContainer rootLayout = (ComponentContainer) LayoutScatter.getInstance(this)
                .parse(ResourceTable.Layout_ability_main, null, false);

        Boolean bool;

        ScrollView scrollView = (ScrollView) findComponentById(ResourceTable.Id_scrollview);

        switch1=(Switch) rootLayout.findComponentById(ResourceTable.Id_switch1);

        et_text = (TextField) rootLayout.findComponentById(ResourceTable.Id_et_text);
        et_number = (TextField) rootLayout.findComponentById(ResourceTable.Id_et_number);
        et_number_int=(TextField) rootLayout.findComponentById(ResourceTable.Id_et_number_int);
        et_number_long=(TextField) rootLayout.findComponentById(ResourceTable.Id_et_number_long);
        et_number_float=(TextField) rootLayout.findComponentById(ResourceTable.Id_et_number_float);
        et_stringset=(TextField) rootLayout.findComponentById(ResourceTable.Id_et_stringset);


        bt_save_text = (Button) rootLayout.findComponentById(ResourceTable.Id_bt_save_text);
        bt_save_number = (Button) rootLayout.findComponentById(ResourceTable.Id_bt_save_number);
        bt_save_number_int = (Button) rootLayout.findComponentById(ResourceTable.Id_bt_save_number_int);
        bt_save_number_long = (Button) rootLayout.findComponentById(ResourceTable.Id_bt_save_number_long);
        bt_save_number_float = (Button) rootLayout.findComponentById(ResourceTable.Id_bt_save_number_float);
        bt_save_stringset = (Button) rootLayout.findComponentById(ResourceTable.Id_bt_save_stringset);

        bt_force_close = (Button) rootLayout.findComponentById(ResourceTable.Id_bt_force_close);
        bt_clear=(Button) rootLayout.findComponentById(ResourceTable.Id_bt_clear);



        tv_saved_text = (Text) rootLayout.findComponentById(ResourceTable.Id_tv_saved_text);
        tv_saved_number = (Text) rootLayout.findComponentById(ResourceTable.Id_tv_saved_number);
        tv_saved_number_int =(Text) rootLayout.findComponentById(ResourceTable.Id_tv_saved_number_int);
        tv_saved_number_long =(Text) rootLayout.findComponentById(ResourceTable.Id_tv_saved_number_long);
        tv_saved_number_float =(Text) rootLayout.findComponentById(ResourceTable.Id_tv_saved_number_float);
        tv_saved_stringset =(Text) rootLayout.findComponentById(ResourceTable.Id_tv_saved_string_set);


        bt_save_text.setClickedListener(this);//will save string
        bt_save_number.setClickedListener(this);//save double number
        bt_force_close.setClickedListener(this);//force close the app
        bt_save_number_int.setClickedListener(this);//save int number
        bt_save_number_long.setClickedListener(this);
        bt_save_number_float.setClickedListener(this);
        bt_save_stringset.setClickedListener(this);
        bt_clear.setClickedListener(this);

        switch1.setCheckedStateChangedListener(new AbsButton.CheckedStateChangedListener() {
            // Called when the switch status is changed.
            @Override
            public void onCheckedChanged(AbsButton button, boolean isChecked) {
                if(isChecked){//if checked
                    //Log.d("name","Logcat is working here");
                    Prefs.writeBoolean(SAVED_SWITCH,true);
                    //Toast.makeText(MainAbilitySlice.this,"Switch is checked",Toast.LENGTH_SHORT).show();
                }
                else{

                    Prefs.writeBoolean(SAVED_SWITCH,false);
                    //Toast.makeText(MainActivity.this,"Switch is not check",Toast.LENGTH_SHORT).show();
                }

            }
        });

        {

            String s = prefs.read(SAVED_TEXT, getString(ResourceTable.String_not_found));
            HiLog.info(LABEL_LOG, String.format(Locale.ROOT, "%s", s));
            updateText(s);

            //showToast(""+SAVED_NUMBER);
            double d = Prefs.with(getApplicationContext(),"Custom_name").readDouble(SAVED_NUMBER, -1.0);
            updateNumber(d, false);

            //showToast(SAVED_NUMBER_INT);

            int i = Prefs.readInt(SAVED_NUMBER_INT, -1);
            updateNumber(i, false);

            long l = Prefs.readLong(SAVED_NUMBER_LONG, -1);
            updateNumber(l, false);

            float f = Prefs.readFloat(SAVED_NUMBER_FLOAT, -1.0f);
            updateNumber(f, false);

            bool=Prefs.readBoolean(SAVED_SWITCH,true);
            switch1.setChecked(bool);

            Set<String> stringset = prefs.getStringSet(SAVED_STRINGSET,new HashSet<>(Arrays.asList("default_string set")));
            updateSet(stringset);




        }



        try {

            String s = prefs.read(SAVED_TEXT, getString(ResourceTable.String_not_found));
            HiLog.info(LABEL_LOG, String.format(Locale.ROOT, "%s", s));
            updateText(s);

            //showToast(""+SAVED_NUMBER);
            double d = Prefs.with(getApplicationContext(),"Custom_name").readDouble(SAVED_NUMBER, -1.0);
            updateNumber(d, false);

            //showToast(SAVED_NUMBER_INT);

            int i = Prefs.readInt(SAVED_NUMBER_INT, -1);
            updateNumber(i, false);

            long l = Prefs.readLong(SAVED_NUMBER_LONG, -1);
            updateNumber(l, false);

            float f = Prefs.readFloat(SAVED_NUMBER_FLOAT, -1.0f);
            updateNumber(f, false);

            bool=Prefs.readBoolean(SAVED_SWITCH,true);
            switch1.setChecked(bool);

            Set<String> stringset = prefs.getStringSet(SAVED_STRINGSET,new HashSet<>(Arrays.asList("default_string set")));
            updateSet(stringset);




        } catch(IllegalArgumentException e) {
            HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
        }

        super.setUIContent(rootLayout);
    }

    private void updateText(String s) {
        String text = String.format(getString(ResourceTable.String_saved_text), s);
        tv_saved_text.setText(text);
    }

    private void updateNumber(double d, boolean fromInstanceState) {
        String text = String.format(getString(ResourceTable.String_saved_number), d, fromInstanceState ? FROM_INSTANCE_STATE : "");
        tv_saved_number.setText(text);
    }

    private void updateNumber(int d, boolean fromInstanceState) {
        String text = String.format(getString(ResourceTable.String_saved_number_int), d, fromInstanceState ? FROM_INSTANCE_STATE : "");
        tv_saved_number_int.setText(text);
    }

    private void updateNumber(long d, boolean fromInstanceState) {
        String text = String.format(getString(ResourceTable.String_saved_number_long), d, fromInstanceState ? FROM_INSTANCE_STATE : "");
        tv_saved_number_long.setText(text);
    }

    private void updateNumber(float d, boolean fromInstanceState) {
        String text = String.format(getString(ResourceTable.String_saved_number_float), d, fromInstanceState ? FROM_INSTANCE_STATE : "");
        tv_saved_number_float.setText(text);
    }
    private void updateSet(Set<String> String_set) {
        String[] arrayOfString = convert(String_set);
        String text = String.format(getString(ResourceTable.String_saved_string_set),
                Arrays.toString(arrayOfString));
        tv_saved_stringset.setText(text);

    }


    @Override
    public void onClick(Component component) {
        Intent intent;

        switch (component.getId()) {
            case ResourceTable.Id_bt_save_text:
                String text = et_text.getText();
                if (!TextUtils.isEmpty(text)) {
                    // one liner to save the String.
                    HiLog.info(LABEL_LOG, String.format(Locale.ROOT, "%s", Prefs.contains("key_order_string_set")));
                    try {
                        Prefs.write(SAVED_TEXT, text);
                        updateText(text);
                        showToast("Saved");
                    } catch(IllegalArgumentException e) {
                        HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
                    }
                } else {
                    showToast("trying to save a text with lenght 0");
                }
                break;



            case ResourceTable.Id_bt_save_number:
                HiLog.info(LABEL_LOG, String.format(Locale.ROOT, "%s", et_number.getText()));
                text = et_number.getText();
                if (!TextUtils.isEmpty(text)) {
                    try {
                        double d = Double.parseDouble(text);
                        Prefs.writeDouble(SAVED_NUMBER, d);
                        updateNumber(d, false);
                        showToast("Saved");
                    } catch(IllegalArgumentException e) {
                        HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
                    }
                } else {
                    showToast("trying to save a number with lenght 0");
                }
                break;



            case ResourceTable.Id_bt_save_number_int:
                HiLog.info(LABEL_LOG, String.format(Locale.ROOT, "%s", et_number.getText()));
                text = et_number_int.getText();//here is the error
                if (!TextUtils.isEmpty(text)) {
                    try {
                        int i = Integer.parseInt(text);
                        Prefs.writeInt(SAVED_NUMBER_INT, i);
                        updateNumber(i, false);
                        showToast("Saved");
                    } catch(IllegalArgumentException e) {
                        HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
                    }
                } else {
                    showToast("trying to save a number with lenght 0");
                }
                break;

            case ResourceTable.Id_bt_save_number_long:
                HiLog.info(LABEL_LOG, String.format(Locale.ROOT, "%s", et_number.getText()));
                text = et_number_long.getText();//here is the error
                if (!TextUtils.isEmpty(text)) {
                    try {
                        long l = Long.parseLong(text);
                        Prefs.writeLong(SAVED_NUMBER_LONG, l);
                        updateNumber(l, false);
                        showToast("Saved");
                    } catch(IllegalArgumentException e) {
                        HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
                    }
                } else {
                    showToast("trying to save a number with lenght 0");
                }
                break;


            case ResourceTable.Id_bt_save_number_float:
                HiLog.info(LABEL_LOG, String.format(Locale.ROOT, "%s", et_number.getText()));
                text = et_number_float.getText();//here is the error
                if (!TextUtils.isEmpty(text)) {
                    try {
                        float f = Float.parseFloat(text);
                        Prefs.writeFloat(SAVED_NUMBER_FLOAT, f);
                        updateNumber(f, false);
                        showToast("Saved");
                    } catch(IllegalArgumentException e) {
                        HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
                    }
                } else {
                    showToast("trying to save a number with lenght 0");
                }
                break;

            case ResourceTable.Id_bt_save_stringset:
                HiLog.info(LABEL_LOG, String.format(Locale.ROOT, "%s", et_number.getText()));


//                text = et_stringset.getText();
                String t = et_stringset.getText();
                String array[]=t.split(" ");
                Set<String> text1 = new HashSet<>(Arrays.asList(array));

                if (!TextUtils.isEmpty(t)) {
                    try {
                        Prefs.putStringSet(SAVED_STRINGSET, text1);
                        updateSet(text1);
                        showToast("Saved");
                    } catch(IllegalArgumentException e) {
                        HiLog.error(LABEL_LOG, String.format(Locale.ROOT, "%s", e.getMessage()));
                    }
                } else {
                    showToast("trying to save a stringset with lenght 0");
                }
                break;

            case ResourceTable.Id_bt_force_close:
                terminateAbility();
                break;


            case ResourceTable.Id_bt_clear:
                Prefs.clear();
                showToast("All prefs is cleared");
                break;
            default:
                throw new IllegalArgumentException(
                        "Did you add a new button forget to listen to view ID in the onclick?");
        }
    }

    private void showToast(String text) {
        ToastDialog t = new ToastDialog(MainAbilitySlice.this);
        t.setText(text);
        t.setDuration(100000);
        t.show();
    }





//
//
//    @Override
//    public void onActive() {
//        super.onActive();
//    }
//
//    @Override
//    public void onForeground(Intent intent) {
//        super.onForeground(intent);
//    }


    public static String[] convert(Set<String> setOfString)
    {

        // Create String[] of size of setOfString
        String[] arrayOfString = new String[setOfString.size()];

        // Copy elements from set to string array
        // using advanced for loop
        int index = 0;
        for (String str : setOfString)
            arrayOfString[index++] = str;

        // return the formed String[]
        return arrayOfString;
    }

}
