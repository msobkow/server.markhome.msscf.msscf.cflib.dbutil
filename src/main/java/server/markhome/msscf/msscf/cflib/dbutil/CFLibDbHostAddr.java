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

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * The CFLibDbHostAddr is a packed address for either IPV4 or IPV6 addresses, with indicators and detectors for which is which.
 * @author msobkow
 */
public final class CFLibDbHostAddr {
    public static final int IPV6_LENGTH = 16;
    public static final int IPV4_LENGTH = 4;
    public static final int IPV4_PAD = IPV6_LENGTH - IPV4_LENGTH;

    private static boolean addrHeaderInitialized = false;
    private static byte[] addrHeader = new byte[IPV6_LENGTH];
    static {
        for (int i = 0; i < IPV4_LENGTH; i++) {
            addrHeader[i] = 0;
        }
        for (int i = IPV4_LENGTH; i < IPV6_LENGTH; i++) {
            addrHeader[i] = -1;
        }
    }

    /**
     * Is the address header initialized?
     * 
     * @return
     */
    public static boolean isAddrHeaderInitialized() {
        if (addrHeaderInitialized) {
            return true;
        }
    
        // Check if addrHeader is already initialized
        for (int i = 0; i < IPV4_LENGTH; i++) {
            if (addrHeader[i] != 0) {
                addrHeaderInitialized = true;
                break;
            }
        }

        if (!addrHeaderInitialized) {
            for (int i = IPV4_LENGTH; i < IPV6_LENGTH; i++) {
                if (addrHeader[i] != -1) {
                    addrHeaderInitialized = true;
                    break;
                }
            }
        }

        return addrHeaderInitialized;
    }

    /**
     * Is the address header initialized with an IPV4 part but no IPV6 part?
     * 
     * @return
     */
    public static boolean hasIPv4AddrHeader() {
        if (!isAddrHeaderInitialized()) {
            return false;
        }
        boolean hasIPv4Part = false;
        for (int i = 0; i < IPV4_LENGTH; i++) {
            if (addrHeader[i] != 0) {
                hasIPv4Part = true;
                break;
            }
        }
        for (int i = IPV4_LENGTH; i < IPV6_LENGTH; i++) {
            if (addrHeader[i] != -1) {
                return false;
            }
        }
        return hasIPv4Part;
    }

    /**
     * Is the address header initialized with an IPV6 part but no IPV4 part?
     * 
     * @return
     */
    public static boolean hasIPv6AddrHeader() {
        if (!isAddrHeaderInitialized()) {
            return false;
        }
        boolean hasIPv4Part = false;
        for (int i = 0; i < IPV4_LENGTH; i++) {
            if (addrHeader[i] != 0) {
                hasIPv4Part = true;
                break;
            }
        }
        for (int i = IPV4_LENGTH; i < IPV6_LENGTH; i++) {
            if (addrHeader[i] != -1) {
                return hasIPv4Part;
            }
        }
        return false;
    }
    
    /**
     * Initialize the address header with the server's IP address.
     * If the server's IP address is not available, it will use the loopback address.
     * Does not force reinitialization.
     */
    public static void initAddrHeader() {
        initAddrHeader(false);
    }

    /**
     * Initialize the address header with the server's IP address.
     * If the server's IP address is not available, it will use the loopback address.
     * Allows you to force reinitialization even if already initialized; required after network connections are reset and rebound, such as in a restart of a service, or a migration to another server in the cluster.
     *
     * @param reinit
     */
    public static void initAddrHeader(boolean reinit) {
        if (isAddrHeaderInitialized() && !reinit) {
            return;
        }

        // Try to initialize addrHeader with the server's IP address
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            byte[] address = inetAddress.getAddress();

            if (address.length == IPV6_LENGTH) {
                System.arraycopy(address, 0, addrHeader, 0, IPV6_LENGTH);
                addrHeaderInitialized = true;
            } else if (address.length == IPV4_LENGTH) {
                System.arraycopy(address, 0, addrHeader, 0, IPV4_LENGTH);
                for (int i = IPV4_LENGTH; i < IPV6_LENGTH; i++) {
                    addrHeader[i] = -1;
                }
                addrHeaderInitialized = true;
            }
        } catch (UnknownHostException e) {
            // Fallback to IPv4 127.0.0.1 (loopback) in worst case
            addrHeader[0] = 127;
            addrHeader[1] = 0;
            addrHeader[2] = 0;
            addrHeader[3] = 1;
            for (int i = IPV4_LENGTH; i < IPV6_LENGTH; i++) {
                addrHeader[i] = -1;
            }
            addrHeaderInitialized = true;
        }
    }

    /**
     * Get the address header.
     * 
     * @param reinit
     * @return
     */
    protected static byte[] getAddrHeader(boolean reinit) {
        if (reinit || !isAddrHeaderInitialized()) {
            initAddrHeader(reinit);
        }
        return addrHeader;
    }

    /**
     * Copy the address header into the destination buffer, initializing addrHeader if necessary.
     * IPV6_LENGTH bytes will be copied.
     * 
     * @param dstBuff
     * @param dstOffset
     */
    public static void copyAddrHeaderTo(byte[] dstBuff, int dstOffset) {
        if (dstBuff == null || dstBuff.length < IPV6_LENGTH + dstOffset) {
            throw new IllegalArgumentException("Destination buffer is null or too small");
        }
        byte[] addrHeader = getAddrHeader(false);
        System.arraycopy(addrHeader, 0, dstBuff, dstOffset, IPV6_LENGTH);
    }
}
