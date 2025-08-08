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
public class CFLibDbKeyHash512 extends CFLibDbKeyHashBase<CFLibDbKeyHash512> implements Serializable {

  static final long serialVersionUID = 202505130953L;
  static final public  int HASH_LENGTH = 64; // hash size in bytes
  static final public int HASH_LENGTH_STRING = HASH_LENGTH * 2; // SHA-1 hash size as a string
  static final String HASH_ALGO = "SHA-512";

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

  // @Convert(converter = CFLibDbKeyHash512Converter.class)
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

  public static CFLibDbKeyHash512 fromHex(String string) {
    byte[] b = sbytesFromHex(string);
    CFLibDbKeyHash512 h = new CFLibDbKeyHash512();
    h.bytes = b;
    return h;
  }

  public static Comparator<CFLibDbKeyHash512> getComparator() {

    return new Comparator<CFLibDbKeyHash512>() {
      @Override
      public int compare(CFLibDbKeyHash512 a, CFLibDbKeyHash512 b) {
        return compareOrdered(a, b);
      }
    };
  }

  public CFLibDbKeyHash512() {
    super();
  }

  /**
   * This is the hex code of the underlying ID. THIS IS NOT A HASHING FUNCTION.
   */
  public CFLibDbKeyHash512(String hexId) {
    super(hexId);
  }

  public CFLibDbKeyHash512(byte[] anId) {
    super(anId);
  }

  public CFLibDbKeyHash512(CFLibDbKeyHash512 otherKey) {
    super(otherKey);
  }

  public static CFLibDbKeyHash512 fromInt(int v) {
    CFLibDbKeyHash512 h = nullGet();
    h.bytes[3] = (byte) (v & 0xFF);
    h.bytes[2] = (byte) ((v >> 8) & 0xFF);
    h.bytes[1] = (byte) ((v >> 16) & 0xFF);
    h.bytes[0] = (byte) ((v >> 24) & 0xFF);
    return h;
  }

  public CFLibDbKeyHash512(int notUsed) {
    super(notUsed);
  }

  public static final boolean isNull(CFLibDbKeyHash512 anId) {
    return anId == null || anId.isNull();
  }

  @Override
  public byte[] getBytes() {
    return bytes;
  }

  /**
   * Get a new hash object with the key set to all 0s
   */
  static public CFLibDbKeyHash512 nullGet() {
    CFLibDbKeyHash512 k = new CFLibDbKeyHash512();
    k.bytes = new byte[HASH_LENGTH];
    return k;
  }

  static public String getNullString() {
    return "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000";
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

  static public int compareOrdered(CFLibDbKeyHash512 h1, CFLibDbKeyHash512 h2) {
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

  public static CFLibDbKeyHash512 hash(String text) {
    if (text != null) {
      try {
        MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
        byte[] buf = text.getBytes("UTF-8");
        md.update(buf);

        return new CFLibDbKeyHash512(md.digest());
      }
      catch (Exception ex) {
      }
    }
    return new CFLibDbKeyHash512(0);
  }

  public static CFLibDbKeyHash512 hash(byte[] payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      md.update(payload);

      return new CFLibDbKeyHash512(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash512(0);
  }

  public static CFLibDbKeyHash512 hash(byte[]... payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      for (byte[] bs : payload) {
        md.update(bs);
      }

      return new CFLibDbKeyHash512(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash512(0);
  }

  public static CFLibDbKeyHash512 hash(CFLibDbKeyHash512... payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      for (CFLibDbKeyHash512 k : payload) {
        md.update(k.bytes);
      }
      return new CFLibDbKeyHash512(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash512(0);
  }

  public static CFLibDbKeyHash512 hash(int[] payload) {
    try {
      MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
      for (int x : payload) {
        md.update((byte) ((x >>> 24) & 255));
        md.update((byte) ((x >>> 16) & 255));
        md.update((byte) ((x >>> 8) & 255));
        md.update((byte) (x & 255));
      }

      return new CFLibDbKeyHash512(md.digest());
    }
    catch (Exception ex) {
    }
    return new CFLibDbKeyHash512(0);
  }

  @Override
  public CFLibDbKeyHash512 deepClone() {
    return new CFLibDbKeyHash512(this);
  }

  static public CFLibDbKeyHash512 fromHexQuick(String string) {
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

  public static final CFLibDbKeyHash512[] toCFLibDbKeyHash512(String[] ids) {
    if (ids == null) {
      return null;
    }
    if (ids.length == 0) {
      return new CFLibDbKeyHash512[0];
    }
    CFLibDbKeyHash512[] r = new CFLibDbKeyHash512[ids.length];
    for (int i = 0; i < ids.length; i++) {
      r[i] = new CFLibDbKeyHash512(ids[i]);
    }
    return r;
  }

  public static final List<CFLibDbKeyHash512> toCFLibDbKeyHash512List(String[] ids) {

    if (ids == null) {
      return null;
    }
    if (ids.length == 0) {
      return Collections.emptyList();
    }
    List<CFLibDbKeyHash512> r = new ArrayList<CFLibDbKeyHash512>(ids.length);
    for (int i = 0; i < ids.length; i++) {
      r.add(new CFLibDbKeyHash512(ids[i]));
    }
    return r;

  }

  public static final Set<CFLibDbKeyHash512> toCFLibDbKeyHash512Set(String[] ids) {

    if (ids == null) {
      return null;
    }
    if (ids.length == 0) {
      return Collections.emptySet();
    }
    Set<CFLibDbKeyHash512> r = new HashSet<CFLibDbKeyHash512>(ids.length);
    for (int i = 0; i < ids.length; i++) {
      r.add(new CFLibDbKeyHash512(ids[i]));
    }
    return r;

  }
}
