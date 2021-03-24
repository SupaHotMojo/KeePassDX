/*
 * Copyright 2017 Brian Pellin, Jeremy Jamet / Kunzisoft.
 *
 * This file is part of KeePassDX.
 *
 *  KeePassDX is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  KeePassDX is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with KeePassDX.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.kunzisoft.keepass.stream

import com.kunzisoft.encrypt.UnsignedLong
import com.kunzisoft.encrypt.stream.NullOutputStream
import com.kunzisoft.encrypt.stream.write8BytesLong
import java.io.IOException
import java.security.DigestOutputStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object HmacBlockStream {
    fun getHmacKey64(key: ByteArray, blockIndex: UnsignedLong): ByteArray {
        val hash: MessageDigest
        try {
            hash = MessageDigest.getInstance("SHA-512")
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }

        val digestOutputStream = DigestOutputStream(NullOutputStream(), hash)
        try {
            digestOutputStream.write8BytesLong(blockIndex)
            digestOutputStream.write(key)
            digestOutputStream.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        return hash.digest()
    }
}
