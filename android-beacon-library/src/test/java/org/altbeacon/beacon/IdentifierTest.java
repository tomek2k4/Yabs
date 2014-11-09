package org.altbeacon.beacon;

import android.os.Parcel;

import static android.test.MoreAsserts.assertNotEqual;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.robolectric.RobolectricTestRunner;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.robolectric.annotation.Config;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)

/*
HOW TO SEE DEBUG LINES FROM YOUR UNIT TESTS:

1. set a line like this at the start of your test:
           org.robolectric.shadows.ShadowLog.stream = System.err;
2. run the tests from the command line
3. Look at the test report file in your web browser, e.g.
   file:///Users/dyoung/workspace/AndroidProximityLibrary/build/reports/tests/index.html
4. Expand the System.err section
 */

public class IdentifierTest {
    @Test
    public void testEqualsNormalizationIgnoresCase() {
        Identifier identifier1 = Identifier.parse("2f234454-cf6d-4a0f-adf2-f4911ba9ffa6");
        Identifier identifier2 = Identifier.parse("2F234454-CF6D-4A0F-ADF2-F4911BA9FFA6");

        assertTrue("Identifiers of different case should match", identifier1.equals(identifier2));
    }
    @Test
    public void testToStringNormalizesCase() {
        Identifier identifier1 = Identifier.parse("2F234454-CF6D-4A0F-ADF2-F4911BA9FFA6");

        assertEquals("Identifiers of different case should match", "2f234454-cf6d-4a0f-adf2-f4911ba9ffa6", identifier1.toString());
    }

    @Test
    public void testToByteArrayConvertsUuids() {
        Identifier identifier1 = Identifier.parse("2F234454-CF6D-4A0F-ADF2-F4911BA9FFA6");
        byte[] bytes = identifier1.toByteArrayOfSpecifiedEndianness(true);
        assertEquals("byte array is correct length", bytes.length, 16);
        assertEquals("first byte of uuid converted properly", 0x2f, bytes[0] & 0xFF);
        assertEquals("second byte of uuid converted properly", 0x23, bytes[1] & 0xFF);
        assertEquals("last byte of uuid converted properly", 0xa6, bytes[15] & 0xFF);
    }

    @Test
    public void testToByteArrayConvertsUuidsAsLittleEndian() {
        Identifier identifier1 = Identifier.parse("2F234454-CF6D-4A0F-ADF2-F4911BA9FFA6");
        byte[] bytes = identifier1.toByteArrayOfSpecifiedEndianness(false);
        assertEquals("byte array is correct length", bytes.length, 16);
        assertEquals("first byte of uuid converted properly", 0xa6, bytes[0] & 0xFF);
        assertEquals("last byte of uuid converted properly", 0x2f, bytes[15] & 0xFF);
    }

    @Test
    public void testToByteArrayConvertsHex() {
        Identifier identifier1 = Identifier.parse("0x010203040506");
        byte[] bytes = identifier1.toByteArrayOfSpecifiedEndianness(true);
        assertEquals("byte array is correct length", bytes.length, 6);
        assertEquals("first byte of hex is converted properly", 0x01, bytes[0] & 0xFF);
        assertEquals("last byte of hex is converted properly", 0x06, bytes[5] & 0xFF);
    }
    @Test
    public void testToByteArrayConvertsDecimal() {
        Identifier identifier1 = Identifier.parse("65534");
        byte[] bytes = identifier1.toByteArrayOfSpecifiedEndianness(true);
        assertEquals("byte array is correct length", bytes.length, 2);
        assertEquals("first byte of decimal converted properly", 0xff, bytes[0] & 0xFF);
        assertEquals("last byte of decimal converted properly", 0xfe, bytes[1] & 0xFF);
    }
}
