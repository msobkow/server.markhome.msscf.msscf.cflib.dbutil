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

import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 *
 * @author msobkow
 */
@Embeddable
public class CFLibDbKeyHash224 extends CFLibDbKeyHashBase<CFLibDbKeyHash224> implements Serializable {

  static final long serialVersionUID = 202505162145L;
  static final public  int HASH_LENGTH = 28; // hash size in bytes
  static final public int HASH_LENGTH_STRING = HASH_LENGTH * 2; // SHA-1 hash size as a string
  static final String HASH_ALGO = "SHA-224";

  @Override
  public int getHashLength() {
    return HASH_LENGTH;
  }

  @Override
  public int getHashLengthString() {
    return HASH_LENGTH_STRING;
  }

  @Override
  public String getHashAlgo() {
    return HASH_ALGO;
  }

  static MessageDigest[] m = null;
  @Override
  protected MessageDigest[] getM() {
    return m;
  }

  @Override
  public void initStatics() {
    if (m != null) {
      return;
    }
    super.initStatics();
    try {
      m = new MessageDigest[CONCURRENT_DIGESTS];
      for (int i = 0; i < CONCURRENT_DIGESTS; i++) {
        m[i] = MessageDigest.getInstance(HASH_ALGO);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  // @Convert(converter = CFLibDbKeyHash224Converter.class)
  @Column(name = "bytes", nullable = false)
  protected byte[] bytes;

  public static byte[] sbytesFromHex(String string) {
    if (string == null) {
      // allowed
    }
    else if (string.length() > HASH_LENGTH * 2) {
      throw new IllegalArgumentException("string length is " + string.length() + ".  Must be <= " + HASH_LENGTH * 2 + ".  string is '" + string + "'.");
    }
    byte[] b = new byte[HASH_LENGTH];
    if (string == null) {
      return b;
    }

    int n = string.length();
    for (int i = 0; i < n; i += 2) {
      b[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i + 1), 16));
    }
    return b;
  }

  public static CFLibDbKeyHash224 fromHex(String string) {
    byte[] b = sbytesFromHex(string);
    CFLibDbKeyHash224 h = new CFLibDbKeyHash224();
    h.bytes = b;
    return h;
  }

  public static Comparator<CFLibDbKeyHash224> getComparator() {

    return new Comparator<CFLibDbKeyHash224>() {
      @Override
      public int compare(CFLibDbKeyHash224 a, CFLibDbKeyHash224 b) {
        return compareOrdered(a, b);
      }
    };
  }

  public CFLibDbKeyHash224() {
    super();
  }

  /**
   * This is the hex code of the underlying ID. THIS IS NOT A HASHING FUNCTION.
   */
  public CFLibDbKeyHash224(String hexId) {
    super(hexId);
  }

  public CFLibDbKeyHash224(byte[] anId) {
    super(anId);
  }

  public CFLibDbKeyHash224(CFLibDbKeyHash224 otherKey) {
    super(otherKey);
  }

  public CFLibDbKeyHash224(CFLibDbKeyHash256 otherKey) {
    super();
    if (otherKey == null) {
      bytes = new byte[HASH_LENGTH];
      return;
    }
    byte[] _newId = new byte[HASH_LENGTH];
    System.arraycopy(otherKey.getBytes(), 0, _newId, 0, HASH_LENGTH);
    this.bytes = _newId;
  }

  public CFLibDbKeyHash224(CFLibDbKeyHash384 otherKey) {
    super();
    if (otherKey == null) {
      bytes = new byte[HASH_LENGTH];
      return;
    }
    byte[] _newId = new byte[HASH_LENGTH];
    System.arraycopy(otherKey.getBytes(), 0, _newId, 0, HASH_LENGTH);
    this.bytes = _newId;
  }

  public CFLibDbKeyHash224(CFLibDbKeyHash512 otherKey) {
    super();
    if (otherKey == null) {
      bytes = new byte[HASH_LENGTH];
      return;
    }
    byte[] _newId = new byte[HASH_LENGTH];
    System.arraycopy(otherKey.getBytes(), 0, _newId, 0, HASH_LENGTH);
    this.bytes = _newId;
  }

  public static CFLibDbKeyHash224 fromInt(int v) {
    CFLibDbKeyHash224 h = nullGet();
    h.bytes[3] = (byte) (v & 0xFF);
    h.bytes[2] = (byte) ((v >> 8) & 0xFF);
    h.bytes[1] = (byte) ((v >> 16) & 0xFF);
    h.bytes[0] = (byte) ((v >> 24) & 0xFF);
    return h;
  }

  public CFLibDbKeyHash224(int notUsed) {
    super(notUsed);
  }

  public static final boolean isNull(CFLibDbKeyHash224 anId) {
    return anId == null || anId.isNull();
  }

  public static final String getJava(CFLibDbKeyHash224 id) {
    if (id == null) {
      return "null";
    }
    else {
      return String.format("new CFLibDbKeyHash224(\"%s\")", id.toString());
    }
  }

  @Override
  public byte[] getBytes() {
    return bytes;
  }

  /**
   * Get a new hash object with the key set to all 0s
   */
  static public CFLibDbKeyHash224 nullGet() {
    CFLibDbKeyHash224 k = new CFLibDbKeyHash224();
    k.bytes = new byte[HASH_LENGTH];
    return k;
  }

  static public String getNullString() {
    return "00000000000000000000000000000000000000000000000000000000";
  }

  /**
   * We want DbKeyHashXX to be immutable so this method shouldn't even exist;
   * however it is necessary for JPA. This is the only time it should be used.
   *
   * @param newBytes to be copied from.
   */
  @Override
  public void setBytes(byte[] newBytes) {
    if (newBytes == null) {
      throw new NullPointerException("newBytes must not be null.");
    }
    if (newBytes.length != HASH_LENGTH) {
      throw new IllegalArgumentException("newBytes must be of length " + HASH_LENGTH + ".");
    }
    bytes = newBytes.clone();
  }

  /** Copy into existing key */
  @Override
  public void setBytes(byte[] newBytes, int offset,  int length) {
      System.arraycopy(newBytes, offset, bytes, 0, Math.min(HASH_LENGTH,length));
  }

  static public int compareOrdered(CFLibDbKeyHash224 h1, CFLibDbKeyHash224 h2) {
    if (h1 == null) {
      if (h2 == null) {
        return 0;
      }
      else {
        return -1;
      }
    }
    else {
      if (h2 == null) {
        return 1;
      }
      else {
        for (int i = 0; i < HASH_LENGTH; i++) {
          int v1 = h1.bytes[i] + 256;
          int v2 = h2.bytes[i] + 256;
          if (v1 < v2) return -1;
          if (v1 > v2) return 1;
        }
      }
    }
    return 0;
  }

  public static CFLibDbKeyHash224 hash(String text) {
    if (text != null) {
      try {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
        byte[] buf = text.getBytes("UTF-8");
        md.update(buf);

        return new CFLibDbKeyHash224(md.digest());
      }
      catch (Exception ex) {
      }
    }
    return new CFLibDbKeyHash224(0);
  }

  public static CFLibDbKeyHash224 hash(byte[] payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      md.update(payload);

      return new CFLibDbKeyHash224(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash224(0);
  }

  public static CFLibDbKeyHash224 hash(byte[]... payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      for (byte[] bs : payload) {
        md.update(bs);
      }

      return new CFLibDbKeyHash224(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash224(0);
  }

  public static CFLibDbKeyHash224 hash(CFLibDbKeyHash224... payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      for (CFLibDbKeyHash224 k : payload) {
        md.update(k.bytes);
      }
      return new CFLibDbKeyHash224(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash224(0);
  }

  public static CFLibDbKeyHash224 hash(int[] payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      for (int x : payload) {
        md.update((byte) ((x >>> 24) & 255));
        md.update((byte) ((x >>> 16) & 255));
        md.update((byte) ((x >>> 8) & 255));
        md.update((byte) (x & 255));
      }

      return new CFLibDbKeyHash224(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash224(0);
  }

  @Override
  public CFLibDbKeyHash224 deepClone() {
    return new CFLibDbKeyHash224(this);
  }

  static public CFLibDbKeyHash224 fromHexQuick(String string) {
    if (string == null) {
      return null;
    }
    if (string.length() != HASH_LENGTH * 2) {
      return null;
    }
    for (int i = 0; i < string.length(); i++) {
      try {
        if (Character.digit(string.charAt(i), 16) < 0) {
          return null;
        }
      }
      catch (Exception e) {
        return null;
      }

    }
    try {
      return fromHex(string);
    }
    catch (Exception e) {
      return null;
    }
  }

  public static final CFLibDbKeyHash224[] toCFLibDbKeyHash224(String[] ids) {
    if (ids == null) {
      return null;
    }
    if (ids.length == 0) {
      return new CFLibDbKeyHash224[0];
    }
    CFLibDbKeyHash224[] r = new CFLibDbKeyHash224[ids.length];
    for (int i = 0; i < ids.length; i++) {
      r[i] = new CFLibDbKeyHash224(ids[i]);
    }
    return r;
  }

  public static final List<CFLibDbKeyHash224> toCFLibDbKeyHash224List(String[] ids) {

    if (ids == null) {
      return null;
    }
    if (ids.length == 0) {
      return Collections.emptyList();
    }
    List<CFLibDbKeyHash224> r = new ArrayList<CFLibDbKeyHash224>(ids.length);
    for (int i = 0; i < ids.length; i++) {
      r.add(new CFLibDbKeyHash224(ids[i]));
    }
    return r;

  }

  public static final Set<CFLibDbKeyHash224> toCFLibDbKeyHash224Set(String[] ids) {

    if (ids == null) {
      return null;
    }
    if (ids.length == 0) {
      return Collections.emptySet();
    }
    Set<CFLibDbKeyHash224> r = new HashSet<CFLibDbKeyHash224>(ids.length);
    for (int i = 0; i < ids.length; i++) {
      r.add(new CFLibDbKeyHash224(ids[i]));
    }
    return r;

  }

  public static String getUString() {
    return new CFLibDbKeyHash224(23934).toString();
  }
}
