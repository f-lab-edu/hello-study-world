/*
 * kopringboot-multimodule-template
 * Distributed under MIT licence
 */
package com.flab.hsw.core.jdbc.user.repository

import com.flab.hsw.core.annotation.InfrastructureService
import com.flab.hsw.core.domain.user.User
import com.flab.hsw.core.domain.user.repository.UserRepository
import com.flab.hsw.core.jdbc.user.UserEntity
import com.flab.hsw.core.jdbc.user.dao.UserEntityDao
import com.flab.hsw.lib.annotation.VisibleForTesting
import com.flab.hsw.lib.util.FastCollectedLruCache
import java.util.*

/**
 * @since 2021-08-10
 */
@InfrastructureService
internal class UserRepositoryImpl(
    private val usersDao: UserEntityDao
) : UserRepository {
    @VisibleForTesting
    val idToUserCache = FastCollectedLruCache.create<UUID, User>(CACHE_CAPACITY)

    @VisibleForTesting
    val nicknameToUserCache = FastCollectedLruCache.create<String, User>(CACHE_CAPACITY)

    @VisibleForTesting
    val emailToUserCache = FastCollectedLruCache.create<String, User>(CACHE_CAPACITY)

    @VisibleForTesting
    val loginIdToUserCache = FastCollectedLruCache.create<String, User>(CACHE_CAPACITY)

    override fun findByUuid(uuid: UUID): User? =
        (idToUserCache.get(uuid) ?: usersDao.selectByUuid(uuid)?.let { updateCache(it) })

    override fun findByNickname(nickname: String): User? =
        (nicknameToUserCache.get(nickname) ?: usersDao.selectByNickname(nickname)?.let { updateCache(it) })

    override fun findByEmail(email: String): User? =
        (emailToUserCache.get(email) ?: usersDao.selectByEmail(email)?.let { updateCache(it) })

    override fun findByLoginId(loginId: String): User? =
        (loginIdToUserCache.get(loginId) ?: usersDao.selectByLoginId(loginId)?.let { updateCache(it) })

    override fun save(user: User): User {
        val savedUser = usersDao.selectByUuid(user.id)?.let {
            usersDao.update(it.id, UserEntity.from(user))
        } ?: UserEntity.from(user)

        return updateCache(savedUser)
    }

    override fun update(user: User): User {
        val updatedUser = usersDao.selectByUuid(user.id)?.let {
            usersDao.update(it.id, UserEntity.from(user))
        } ?: UserEntity.from(user)

        return updateCache(updatedUser)
    }

    private fun updateCache(userEntity: UserEntity): User {
        val user = userEntity.toUser()

        idToUserCache.put(user.id, user)
        nicknameToUserCache.put(user.nickname, user)
        emailToUserCache.put(user.email, user)
        loginIdToUserCache.put(user.loginId, user)

        return user
    }

    companion object {
        private const val CACHE_CAPACITY = 100
    }
}
