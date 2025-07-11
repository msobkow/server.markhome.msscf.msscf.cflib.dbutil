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

public class CFLibDbKeyHash224Test {

    @Test
    void testGetHashAlgo() {
		assertEquals("SHA-224", CFLibDbKeyHash224.HASH_ALGO);
		CFLibDbKeyHash224 k = new CFLibDbKeyHash224();
		assertEquals("SHA-224", k.getHashAlgo());
    }

    @Test
    void testGetHashLength() {
		assertEquals(28, CFLibDbKeyHash224.HASH_LENGTH);
		CFLibDbKeyHash224 k = new CFLibDbKeyHash224();
		assertEquals(28, k.getHashLength());
    }

    @Test
    void testGetHashLengthString() {
		assertEquals(56, CFLibDbKeyHash224.HASH_LENGTH_STRING);
		CFLibDbKeyHash224 k = new CFLibDbKeyHash224();
		assertEquals(56, k.getHashLengthString());
    }

    @Test
    void testGetBytes() {
		CFLibDbKeyHash224 k = new CFLibDbKeyHash224();
		byte[] bytes = k.getBytes();
		assertNull(bytes);
		CFLibDbKeyHash224 k2 = CFLibDbKeyHash224.nullGet();
		byte[] bytes2 = k2.getBytes();
		assertNotNull(bytes2);
    }

    @Test
    void testNullGet() {
		CFLibDbKeyHash224 n = CFLibDbKeyHash224.nullGet();
		byte[] bytes = n.getBytes();
		assertNotNull(bytes);
		assertEquals(28, bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			assertEquals(0, bytes[i]);
		}
    }

	@Test
    void testCompareOrdered() {

		CFLibDbKeyHash224 nullA = CFLibDbKeyHash224.nullGet();
		CFLibDbKeyHash224 nullB = CFLibDbKeyHash224.nullGet();
		assertEquals(0, CFLibDbKeyHash224.compareOrdered(nullA, nullB));

		byte[] bA = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28};
		byte[] bB = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};
		CFLibDbKeyHash224 valA = new CFLibDbKeyHash224(bA);
		assertEquals(0, CFLibDbKeyHash224.compareOrdered(valA, valA));
		CFLibDbKeyHash224 anotherA = new CFLibDbKeyHash224(bA);
		assertEquals(0, CFLibDbKeyHash224.compareOrdered(valA, anotherA));

		CFLibDbKeyHash224 valB = new CFLibDbKeyHash224(bB);
		assertTrue(CFLibDbKeyHash224.compareOrdered(valA, valB) < 0);
		assertTrue(CFLibDbKeyHash224.compareOrdered(valB, valA) > 0);

		CFLibDbKeyHash224 notNull = new CFLibDbKeyHash224(0);
		assertTrue(CFLibDbKeyHash224.compareOrdered(nullA, notNull) != 0);
		assertTrue(CFLibDbKeyHash224.compareOrdered(notNull, nullA) != 0);
    }
}
