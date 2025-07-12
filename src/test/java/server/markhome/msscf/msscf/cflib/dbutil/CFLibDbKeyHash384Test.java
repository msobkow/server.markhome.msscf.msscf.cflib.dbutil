/*
 *	MSS Code Factory CFLib DbUtil
 *
 *	Copyright (c) 2025 Mark Stephen Sobkow
 *
 *	This file is part of MSS Code Factory 3.0.
 *
 *	MSS Code Factory 3.0 is free software: you can redistribute it and/or modify
 *	it under the terms of the Apache v2.0 License as published by the Apache Foundation.
 *
 *	MSS Code Factory 3.0 is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 *	You should have received a copy of the Apache v2.0 License along with
 *	MSS Code Factory.  If not, see https://www.apache.org/licenses/LICENSE-2.0
 *
 *	Contact Mark Stephen Sobkow at mark.sobkow@gmail.com for commercial licensing or
 *  customization.
 */

package server.markhome.msscf.msscf.cflib.dbutil;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CFLibDbKeyHash384Test {

    @Test
    void testGetHashAlgo() {
		assertEquals("SHA-384", CFLibDbKeyHash384.HASH_ALGO);
		CFLibDbKeyHash384 k = new CFLibDbKeyHash384();
		assertEquals("SHA-384", k.getHashAlgo());
    }

    @Test
    void testGetHashLength() {
		assertEquals(48, CFLibDbKeyHash384.HASH_LENGTH);
		CFLibDbKeyHash384 k = new CFLibDbKeyHash384();
		assertEquals(48, k.getHashLength());
    }

    @Test
    void testGetHashLengthString() {
		assertEquals(96, CFLibDbKeyHash384.HASH_LENGTH_STRING);
		CFLibDbKeyHash384 k = new CFLibDbKeyHash384();
		assertEquals(96, k.getHashLengthString());
    }

    @Test
    void testGetBytes() {
		CFLibDbKeyHash384 k = new CFLibDbKeyHash384();
		byte[] bytes = k.getBytes();
		assertNull(bytes);
		CFLibDbKeyHash384 k2 = CFLibDbKeyHash384.nullGet();
		byte[] bytes2 = k2.getBytes();
		assertNotNull(bytes2);
    }

    @Test
    void testNullGet() {
		CFLibDbKeyHash384 n = CFLibDbKeyHash384.nullGet();
		byte[] bytes = n.getBytes();
		assertNotNull(bytes);
		assertEquals(48, bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			assertEquals(0, bytes[i]);
		}
    }

	@Test
    void testCompareOrdered() {

		CFLibDbKeyHash384 nullA = CFLibDbKeyHash384.nullGet();
		CFLibDbKeyHash384 nullB = CFLibDbKeyHash384.nullGet();
		assertEquals(0, CFLibDbKeyHash384.compareOrdered(nullA, nullB));

		byte[] bA = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48};
		byte[] bB = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49};
		CFLibDbKeyHash384 valA = new CFLibDbKeyHash384(bA);
		assertEquals(0, CFLibDbKeyHash384.compareOrdered(valA, valA));
		CFLibDbKeyHash384 anotherA = new CFLibDbKeyHash384(bA);
		assertEquals(0, CFLibDbKeyHash384.compareOrdered(valA, anotherA));

		CFLibDbKeyHash384 valB = new CFLibDbKeyHash384(bB);
		assertTrue(CFLibDbKeyHash384.compareOrdered(valA, valB) < 0);
		assertTrue(CFLibDbKeyHash384.compareOrdered(valB, valA) > 0);

		CFLibDbKeyHash384 notNull = new CFLibDbKeyHash384(0);
		assertTrue(CFLibDbKeyHash384.compareOrdered(nullA, notNull) != 0);
		assertTrue(CFLibDbKeyHash384.compareOrdered(notNull, nullA) != 0);
    }
}
