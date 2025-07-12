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

public class CFLibDbKeyHash256Test {

    @Test
    void testGetHashAlgo() {
		assertEquals("SHA-256", CFLibDbKeyHash256.HASH_ALGO);
		CFLibDbKeyHash256 k = new CFLibDbKeyHash256();
		assertEquals("SHA-256", k.getHashAlgo());
    }

    @Test
    void testGetHashLength() {
		assertEquals(32, CFLibDbKeyHash256.HASH_LENGTH);
		CFLibDbKeyHash256 k = new CFLibDbKeyHash256();
		assertEquals(32, k.getHashLength());
    }

    @Test
    void testGetHashLengthString() {
		assertEquals(64, CFLibDbKeyHash256.HASH_LENGTH_STRING);
		CFLibDbKeyHash256 k = new CFLibDbKeyHash256();
		assertEquals(64, k.getHashLengthString());
    }

    @Test
    void testGetBytes() {
		CFLibDbKeyHash256 k = new CFLibDbKeyHash256();
		byte[] bytes = k.getBytes();
		assertNull(bytes);
		CFLibDbKeyHash256 k2 = CFLibDbKeyHash256.nullGet();
		byte[] bytes2 = k2.getBytes();
		assertNotNull(bytes2);
    }

    @Test
    void testNullGet() {
		CFLibDbKeyHash256 n = CFLibDbKeyHash256.nullGet();
		byte[] bytes = n.getBytes();
		assertNotNull(bytes);
		assertEquals(32, bytes.length);
		for (int i = 0; i < bytes.length; i++) {
			assertEquals(0, bytes[i]);
		}
    }

	@Test
    void testCompareOrdered() {

		CFLibDbKeyHash256 nullA = CFLibDbKeyHash256.nullGet();
		CFLibDbKeyHash256 nullB = CFLibDbKeyHash256.nullGet();
		assertEquals(0, CFLibDbKeyHash256.compareOrdered(nullA, nullB));

		byte[] bA = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32};
		byte[] bB = { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33};
		CFLibDbKeyHash256 valA = new CFLibDbKeyHash256(bA);
		assertEquals(0, CFLibDbKeyHash256.compareOrdered(valA, valA));
		CFLibDbKeyHash256 anotherA = new CFLibDbKeyHash256(bA);
		assertEquals(0, CFLibDbKeyHash256.compareOrdered(valA, anotherA));

		CFLibDbKeyHash256 valB = new CFLibDbKeyHash256(bB);
		assertTrue(CFLibDbKeyHash256.compareOrdered(valA, valB) < 0);
		assertTrue(CFLibDbKeyHash256.compareOrdered(valB, valA) > 0);

		CFLibDbKeyHash256 notNull = new CFLibDbKeyHash256(0);
		assertTrue(CFLibDbKeyHash256.compareOrdered(nullA, notNull) != 0);
		assertTrue(CFLibDbKeyHash256.compareOrdered(notNull, nullA) != 0);
    }
}
