/*
 * Copyright (C) 2005, 2006, 2007, 2008, 2009, 2010 Sly Technologies, Inc.
 *
 * This file is part of jNetPcap.
 *
 * jNetPcap is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jnetpcap.packet.annotate;

import org.jnetpcap.protocol.JProtocol.Suite;

// TODO: Auto-generated Javadoc
/**
 * Standard protocol suite names. This table is mainly used in annotations, but
 * can be also used to retrieve meta data about a protocol.
 * 
 * @author Mark Bednarczyk
 * @author Sly Technologies, Inc.
 */
public enum ProtocolSuite implements Suite {

	/**
	 * OSI application layer set of protocols.
	 */
	APPLICATION,
	/**
	 * Tcp/Ip family of protocols.
	 */
	TCP_IP,

	/**
	 * Security related family of protocols.
	 */
	SECURITY,

	/**
	 * Tunneling family of protocols.
	 */
	VPN,

	/**
	 * Mobile communication device family of protocols.
	 */
	MOBILE,

	/**
	 * OSI network layer family of protocols.
	 */
	NETWORK,

	/**
	 * Wireless family of protocols.
	 */
	WIRELESS,

	/**
	 * Voice over IP family of protocols.
	 */
	VOIP,

	/**
	 * Local Area Network family of protocols.
	 */
	LAN,

	/**
	 * Metropolitan Area Network family of protocols.
	 */
	MAN,

	/**
	 * Wide Area Network family of protocols.
	 */
	WAN,
	/**
	 * Storage Area Network family of protocols.
	 */
	SAN,

	/**
	 * ISO family of protocols.
	 */

	ISO,

	/**
	 * SS7 family of protocols.
	 */
	SS7,

	/**
	 * Cisco Systems family of protocols.
	 */
	CISCO,

	/**
	 * IBM family of protocols.
	 */
	IBM,

	/**
	 * Microsoft Corp family of protocols.
	 */
	MICROSOFT,

	/**
	 * Novell family of protocols.
	 */
	NOVELL,

	/**
	 * Apple Corp family of protocols.
	 */
	APPLE,

	/**
	 * Hewlet Packard Corp family of protocols.
	 */
	HP,

	/**
	 * Sun Microsystems Corp family of protocols.
	 */
	SUN,

	/**
	 * Catch all suite for other types of protocols.
	 */
	OTHER,
}