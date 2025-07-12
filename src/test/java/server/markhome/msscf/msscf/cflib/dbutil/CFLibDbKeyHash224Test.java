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
