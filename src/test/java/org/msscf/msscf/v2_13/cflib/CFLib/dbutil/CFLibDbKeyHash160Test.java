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

public class CFLibDbKeyHash160Test {

    @Test
    void testGetHashAlgo() {
		assertEquals("SHA-1", CFLibDbKeyHash160.HASH_ALGO);
		CFLibDbKeyHash160 k = new CFLibDbKeyHash160();
		assertEquals("SHA-1", k.getHashAlgo());
    }

    @Test
    void testGetHashLength() {
		assertEquals(20, CFLibDbKeyHash160.HASH_LENGTH);
		CFLibDbKeyHash160 k = new CFLibDbKeyHash160();
		assertEquals(20, k.getHashLength());
    }

    @Test
    void testGetHashLengthString() {
		assertEquals(40, CFLibDbKeyHash160.HASH_LENGTH_STRING);
		CFLibDbKeyHash160 k = new CFLibDbKeyHash160();
		assertEquals(40, k.getHashLengthString());
    }

    @Test
    void testGetBytes() {
		CFLibDbKeyHash160 k = new CFLibDbKeyHash160();
		byte[] bytes = k.getBytes();
		assertNull(bytes);
		CFLibDbKeyHash160 k2 = CFLibDbKeyHash160.nullGet();
		byte[] bytes2 = k2.getBytes();
		assertNotNull(bytes2);
    }

    @Test
    void testNullGet() {
		CFLibDbKeyHash160 n = CFLibDbKeyHash160.nullGet();
		byte[] bytes = n.getBytes();
		assertNotNull(bytes);
		assertEquals(20, bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			assertEquals(0, bytes[i]);
		}
    }

	@Test
    void testCompareOrdered() {

		CFLibDbKeyHash160 nullA = CFLibDbKeyHash160.nullGet();
		CFLibDbKeyHash160 nullB = CFLibDbKeyHash160.nullGet();
		assertEquals(0, CFLibDbKeyHash160.compareOrdered(nullA, nullB));

		byte[] bA = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		byte[] bB = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21};
		CFLibDbKeyHash160 valA = new CFLibDbKeyHash160(bA);
		assertEquals(0, CFLibDbKeyHash160.compareOrdered(valA, valA));
		CFLibDbKeyHash160 anotherA = new CFLibDbKeyHash160(bA);
		assertEquals(0, CFLibDbKeyHash160.compareOrdered(valA, anotherA));

		CFLibDbKeyHash160 valB = new CFLibDbKeyHash160(bB);
		assertTrue(CFLibDbKeyHash160.compareOrdered(valA, valB) < 0);
		assertTrue(CFLibDbKeyHash160.compareOrdered(valB, valA) > 0);

		CFLibDbKeyHash160 notNull = new CFLibDbKeyHash160(0);
		assertTrue(CFLibDbKeyHash160.compareOrdered(nullA, notNull) != 0);
		assertTrue(CFLibDbKeyHash160.compareOrdered(notNull, nullA) != 0);
    }
}
