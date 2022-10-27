package com.flab.hsw.core.domain.user.aggregate

import org.mindrot.jbcrypt.BCrypt

interface PasswordEncryptor {

    fun encrypt(plainText: String): String

    fun isMatched(plainText: String, hashedPassword: String): Boolean

    companion object {
        fun newInstance(): PasswordEncryptor = PasswordEncryptorImplByBCrypt()
    }
}

internal class PasswordEncryptorImplByBCrypt : PasswordEncryptor {

    override fun encrypt(plainText: String): String {
        return BCrypt.hashpw(plainText, BCrypt.gensalt());
    }

    override fun isMatched(plainText: String, hashedPassword: String): Boolean {
        return BCrypt.checkpw(plainText, hashedPassword);
    }
}
