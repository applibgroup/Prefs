package es.dmoral.prefs;



import ohos.aafwk.ability.delegation.AbilityDelegatorRegistry;
import ohos.data.preferences.Preferences;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class ExampleOhosTest {

    private final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();


    @Test
    public void testBundleName() {
        final String actualBundleName = AbilityDelegatorRegistry.getArguments().getTestBundleName();
        assertEquals("es.dmoral.prefs", actualBundleName);
    }



    @Test
    public void testPref() {
        try {
            Preferences pref = Prefs.getPreferences();
            assertNotNull("Preferences is initialized", pref);
        } catch (RuntimeException e) {
            assertTrue("Pref is not initialized in MyApplication",true);
        }
    }

    @Test
    public void testPrefBoolean() {
        try {
            Prefs.writeBoolean("key_bool", true);
            assertTrue("Preferences tested boolean value", Prefs.readBoolean("key_bool"));
        } catch (RuntimeException e) {
            assertTrue("Pref is not initialized in MyApplication",true);
        }
    }

    @Test
    public void testPrefString() {
        try {
            Prefs.write("key_string", actualBundleName);
            assertTrue("Preferences tested string value", Prefs.read("key_string")
                    .equalsIgnoreCase(actualBundleName));
        } catch (RuntimeException e) {
            assertTrue("Pref is not initialized in MyApplication",true);
        }
    }

    @Test
    public void testPrefInt() {
        try {
            Prefs.writeInt("key_int", 100);
            assertEquals("Preferences tested int value", 100, Prefs.readInt("key_int"));
        } catch (RuntimeException e) {
            assertTrue("Pref is not initialized in MyApplication",true);
        }
    }

    @Test
    public void testPrefLong() {
        try {
            Prefs.writeLong("key_long", 100);
            assertEquals("Preferences tested long value", 100, Prefs.readLong(  "key_long"));
        } catch (RuntimeException e) {
            assertTrue("Pref is not initialized in MyApplication",true);
        }
    }

    @Test
    public void testDouble() {
        try {
            Prefs.writeDouble("key_double", 100);
            assertEquals("Preferences tested double value", 100, Prefs.readDouble(  "key_double"), 0.0);
        } catch (RuntimeException e) {
            assertTrue("Pref is not initialized in MyApplication",true);
        }
    }

    @Test
    public void testPrefFloat() {
        try {
            Prefs.writeFloat("key_float", 100);
            assertEquals("Preferences tested float value", 100, Prefs.readFloat("key_float"), 0.0);
        } catch (RuntimeException e) {
            assertTrue("Pref is not initialized in MyApplication",true);
        }
    }

    @Test
    public void testPrefStringSet() {
        Set<String> stringSet = new HashSet<>();

        stringSet.add("openharmony");
        stringSet.add("project");
        stringSet.add("created");
        stringSet.add("EasyPrefs");
        stringSet.add("application");

        try {
            Prefs.putStringSet("key_string_set", stringSet);
            assertEquals("Preferences tested StringSet value", stringSet, Prefs.getStringSet("key_string_set", new HashSet<>()));
        } catch (RuntimeException e) {
            assertTrue("Pref is not initialized in MyApplication",true);
        }
    }

}