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

public class CFLibDbKeyHash128Test {

    @Test
    void testGetHashAlgo() {
		assertEquals("MD5", CFLibDbKeyHash128.HASH_ALGO);
		CFLibDbKeyHash128 k = new CFLibDbKeyHash128();
		assertEquals("MD5", k.getHashAlgo());
    }

    @Test
    void testGetHashLength() {
		assertEquals(16, CFLibDbKeyHash128.HASH_LENGTH);
		CFLibDbKeyHash128 k = new CFLibDbKeyHash128();
		assertEquals(16, k.getHashLength());
    }

    @Test
    void testGetHashLengthString() {
		assertEquals(32, CFLibDbKeyHash128.HASH_LENGTH_STRING);
		CFLibDbKeyHash128 k = new CFLibDbKeyHash128();
		assertEquals(32, k.getHashLengthString());
    }

    @Test
    void testGetBytes() {
		CFLibDbKeyHash128 k = new CFLibDbKeyHash128();
		byte[] bytes = k.getBytes();
		assertNull(bytes);
		CFLibDbKeyHash128 k2 = CFLibDbKeyHash128.nullGet();
		byte[] bytes2 = k2.getBytes();
		assertNotNull(bytes2);
    }

    @Test
    void testNullGet() {
		CFLibDbKeyHash128 n = CFLibDbKeyHash128.nullGet();
		byte[] bytes = n.getBytes();
		assertNotNull(bytes);
		assertEquals(16, bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			assertEquals(0, bytes[i]);
		}
    }

	@Test
    void testCompareOrdered() {

		CFLibDbKeyHash128 nullA = CFLibDbKeyHash128.nullGet();
		CFLibDbKeyHash128 nullB = CFLibDbKeyHash128.nullGet();
		assertEquals(0, CFLibDbKeyHash128.compareOrdered(nullA, nullB));

		byte[] bA = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
		byte[] bB = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
		CFLibDbKeyHash128 valA = new CFLibDbKeyHash128(bA);
		assertEquals(0, CFLibDbKeyHash128.compareOrdered(valA, valA));
		CFLibDbKeyHash128 anotherA = new CFLibDbKeyHash128(bA);
		assertEquals(0, CFLibDbKeyHash128.compareOrdered(valA, anotherA));

		CFLibDbKeyHash128 valB = new CFLibDbKeyHash128(bB);
		assertTrue(CFLibDbKeyHash128.compareOrdered(valA, valB) < 0);
		assertTrue(CFLibDbKeyHash128.compareOrdered(valB, valA) > 0);

		CFLibDbKeyHash128 notNull = new CFLibDbKeyHash128(0);
		assertTrue(CFLibDbKeyHash128.compareOrdered(nullA, notNull) != 0);
		assertTrue(CFLibDbKeyHash128.compareOrdered(notNull, nullA) != 0);
    }
}
