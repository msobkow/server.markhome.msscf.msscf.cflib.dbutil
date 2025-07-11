/*
 *	MSS Code Factory CFLib 2.13
 *
 *	Copyright (c) 2025 Mark Stephen Sobkow
 *
 *	This file is part of MSS Code Factory.
 *
 *	MSS Code Factory is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU Lesser General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	MSS Code Factory is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU Lesser General Public License for more details.
 *
 *	You should have received a copy of the GNU Lesser General Public License
 *	along with MSS Code Factory.  If not, see https://www.gnu.org/licenses/.
 *
 *	Contact Mark Stephen Sobkow at mark.sobkow@gmail.com for commercial licensing.
 */

package org.msscf.msscf.v2_13.cflib.CFLib.dbutil;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CFLibDbKeyHash512Test {

    @Test
    void testGetHashAlgo() {
		assertEquals("SHA-512", CFLibDbKeyHash512.HASH_ALGO);
		CFLibDbKeyHash512 k = new CFLibDbKeyHash512();
		assertEquals("SHA-512", k.getHashAlgo());
    }

    @Test
    void testGetHashLength() {
		assertEquals(64, CFLibDbKeyHash512.HASH_LENGTH);
		CFLibDbKeyHash512 k = new CFLibDbKeyHash512();
		assertEquals(64, k.getHashLength());
    }

    @Test
    void testGetHashLengthString() {
		assertEquals(128, CFLibDbKeyHash512.HASH_LENGTH_STRING);
		CFLibDbKeyHash512 k = new CFLibDbKeyHash512();
		assertEquals(128, k.getHashLengthString());
    }

    @Test
    void testGetBytes() {
		CFLibDbKeyHash512 k = new CFLibDbKeyHash512();
		byte[] bytes = k.getBytes();
		assertNull(bytes);
		CFLibDbKeyHash512 k2 = CFLibDbKeyHash512.nullGet();
		byte[] bytes2 = k2.getBytes();
		assertNotNull(bytes2);
    }

    @Test
    void testNullGet() {
		CFLibDbKeyHash512 n = CFLibDbKeyHash512.nullGet();
		byte[] bytes = n.getBytes();
		assertNotNull(bytes);
		assertEquals(64, bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			assertEquals(0, bytes[i]);
		}
    }

	@Test
    void testCompareOrdered() {

		CFLibDbKeyHash512 nullA = CFLibDbKeyHash512.nullGet();
		CFLibDbKeyHash512 nullB = CFLibDbKeyHash512.nullGet();
		assertEquals(0, CFLibDbKeyHash512.compareOrdered(nullA, nullB));

		byte[] bA = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64};
		byte[] bB = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65};
		CFLibDbKeyHash512 valA = new CFLibDbKeyHash512(bA);
		assertEquals(0, CFLibDbKeyHash512.compareOrdered(valA, valA));
		CFLibDbKeyHash512 anotherA = new CFLibDbKeyHash512(bA);
		assertEquals(0, CFLibDbKeyHash512.compareOrdered(valA, anotherA));

		CFLibDbKeyHash512 valB = new CFLibDbKeyHash512(bB);
		assertTrue(CFLibDbKeyHash512.compareOrdered(valA, valB) < 0);
		assertTrue(CFLibDbKeyHash512.compareOrdered(valB, valA) > 0);

		CFLibDbKeyHash512 notNull = new CFLibDbKeyHash512(0);
		assertTrue(CFLibDbKeyHash512.compareOrdered(nullA, notNull) != 0);
		assertTrue(CFLibDbKeyHash512.compareOrdered(notNull, nullA) != 0);
    }
}
