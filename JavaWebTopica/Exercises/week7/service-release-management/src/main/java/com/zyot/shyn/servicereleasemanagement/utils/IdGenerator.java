package com.zyot.shyn.servicereleasemanagement.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IdGenerator implements IdentifierGenerator {
    private static final Logger logger = Logger.getLogger(IdGenerator.class.getSimpleName());

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        String hashId = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(String.valueOf(System.currentTimeMillis()).getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hashId = new StringBuilder(no.toString(16)).toString();
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return hashId;
    }
}
