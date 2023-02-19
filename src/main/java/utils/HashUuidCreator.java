package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Utility class that creates UUIDv3 (MD5) and UUIDv5 (SHA1).
 * Used to create a unique uuid - a shortened id for reward objects etc.
 * Using a HashMap.
 *
 * The code is taken from:
 * https://gist.github.com/fabiolimace/d43ef0ffb4f2e639bb0d81906963bb35
 *
 * Used by Shiri Rave,
 * @since January 2023.
 */
public class HashUuidCreator {


    private static final int VERSION_3 = 3; // UUIDv3 MD5
    private static final int VERSION_5 = 5; // UUIDv5 SHA1

    private static final String MESSAGE_DIGEST_MD5 = "MD5"; // UUIDv3
    private static final String MESSAGE_DIGEST_SHA1 = "SHA-1"; // UUIDv5

    private static UUID getHashUuid(UUID namespace, String name, String algorithm, int version) {

        final byte[] hash;
        final MessageDigest hasher;

        try {
            // Instantiate a message digest for the chosen algorithm
            hasher = MessageDigest.getInstance(algorithm);

            // Insert name space if NOT NULL
            if (namespace != null) {
                hasher.update(toBytes(namespace.getMostSignificantBits()));
                hasher.update(toBytes(namespace.getLeastSignificantBits()));
            }

            // Generate the hash
            hash = hasher.digest(name.getBytes(StandardCharsets.UTF_8));

            // Split the hash into two parts: MSB and LSB
            long msb = toNumber(hash, 0, 8); // first 8 bytes for MSB
            long lsb = toNumber(hash, 8, 16); // last 8 bytes for LSB

            // Apply version and variant bits (required for RFC-4122 compliance)
            msb = (msb & 0xffffffffffff0fffL) | (version & 0x0f) << 12; // apply version bits
            lsb = (lsb & 0x3fffffffffffffffL) | 0x8000000000000000L; // apply variant bits

            // Return the UUID
            return new UUID(msb, lsb);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Message digest algorithm not supported.");
        }
    }

    public static UUID getSha1Uuid(String string) {
        return getHashUuid(null, string, MESSAGE_DIGEST_SHA1, VERSION_5);
    }

    private static byte[] toBytes(final long number) {
        return new byte[] { (byte) (number >>> 56), (byte) (number >>> 48), (byte) (number >>> 40),
                (byte) (number >>> 32), (byte) (number >>> 24), (byte) (number >>> 16), (byte) (number >>> 8),
                (byte) (number) };
    }

    private static long toNumber(final byte[] bytes, final int start, final int length) {
        long result = 0;
        for (int i = start; i < length; i++) {
            result = (result << 8) | (bytes[i] & 0xff);
        }
        return result;
    }
}