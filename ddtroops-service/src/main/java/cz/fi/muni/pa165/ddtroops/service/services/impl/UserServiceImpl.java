package cz.fi.muni.pa165.ddtroops.service.services.impl;

import cz.fi.muni.pa165.ddtroops.dao.UserDao;
import cz.fi.muni.pa165.ddtroops.entity.User;
import cz.fi.muni.pa165.ddtroops.service.exceptions.DDTroopsServiceException;
import cz.fi.muni.pa165.ddtroops.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

/**
 * @author pstanko
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    //see  https://crackstation.net/hashing-security.htm#javasourcecode
    private static String createHash(String password) {
        final int SALT_BYTE_SIZE = 24;
        final int HASH_BYTE_SIZE = 24;
        final int PBKDF2_ITERATIONS = 1000;
        // Generate a random salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);
        // Hash the password
        byte[] hash = pbkdf2(password.toCharArray(), salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
        // format iterations:salt:hash
        return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
            return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean validatePassword(String password, String correctHash) {
        if (password == null) return false;
        if (correctHash == null) throw new IllegalArgumentException("password hash is null");
        String[] params = correctHash.split(":");
        int iterations = Integer.parseInt(params[0]);
        byte[] salt = fromHex(params[1]);
        byte[] hash = fromHex(params[2]);
        byte[] testHash = pbkdf2(password.toCharArray(), salt, iterations, hash.length);
        return slowEquals(hash, testHash);
    }

    /**
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param a the first byte array
     * @param b the second byte array
     * @return true if both byte arrays are the same, false if not
     */
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        return paddingLength > 0 ? String.format("%0" + paddingLength + "d", 0) + hex : hex;
    }

    @Override
    public void register(User u, String unencryptedPassword) throws DDTroopsServiceException {
        if (u == null) {
            throw new IllegalArgumentException("User is null!");
        }
        try {
            u.setPasswordHash(createHash(unencryptedPassword));
            u.setJoinedDate(new Date());
            userDao.save(u);
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Cannot create user: " + u.getEmail(), ex);
        }
    }

    @Override
    public void update(User u) throws DDTroopsServiceException {
        if (u == null) {
            throw new IllegalArgumentException("User is null!");
        }
        try {
            userDao.save(u);
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Cannot update user: " + u.getId(), ex);
        }

    }

    @Override
    public boolean delete(User u) throws DDTroopsServiceException {
        if (u == null) {
            throw new IllegalArgumentException("User is null!");
        }
        try {
            userDao.delete(u);
            return true;
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Cannot delete user: " + u.getId(), ex);
        }
    }

    @Override
    public boolean updatePassword(User u, String oldPassword, String newPassword) throws DDTroopsServiceException {
        if (u == null) {
            throw new IllegalArgumentException("User is null!");
        }

        User user = userDao.findOne(u.getId()); // fresh

        try {
            if (validatePassword(oldPassword, user.getPasswordHash())) {
                u.setPasswordHash(createHash(newPassword));
                userDao.save(u);
                return true;
            }
            return false;
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Cannot update user: " + u.getId() + " password", ex);
        }
    }

    @Override
    public List<User> findAll() throws DDTroopsServiceException {
        try {
            return userDao.findAll();
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Could not receive list of users!", ex);
        }
    }

    @Override
    public boolean authenticate(User u, String password) throws DDTroopsServiceException {
        if (u == null) {
            throw new IllegalArgumentException("User is null!");
        }
        // Fresh data

        User user = findById(u.getId());

        return validatePassword(password, user.getPasswordHash());
    }

    @Override
    public boolean isAdmin(User u) throws DDTroopsServiceException {
        if (u == null) {
            throw new IllegalArgumentException("User is null!");
        }

        //must get a fresh copy from database
        try {
            return findById(u.getId()).isAdmin();
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Could not decide whether user: " + u + "is admin or not!", ex);
        }
    }

    @Override
    public User findById(Long userId) throws DDTroopsServiceException {
        try {
            return userDao.findOne(userId);
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Cannot find user with " + userId + " id.", ex);
        }
    }

    @Override
    public User findByEmail(String email) throws DDTroopsServiceException {
        try {
            return userDao.findByEmail(email);
        } catch (Throwable ex) {
            throw new DDTroopsServiceException("Cannot find user with \"" + email + "\" email.", ex);
        }
    }
}
