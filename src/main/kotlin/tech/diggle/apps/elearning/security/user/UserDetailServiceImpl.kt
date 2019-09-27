package tech.diggle.apps.elearning.security.user

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import tech.diggle.apps.elearning.security.authority.AuthorityName
import tech.diggle.apps.elearning.security.authority.AuthorityRepository
import java.util.*

@Service
class UserDetailServiceImpl(val userRepository: UserRepository,
                            val authorityRepository: AuthorityRepository) {
    val bCrypt: BCryptPasswordEncoder = BCryptPasswordEncoder()
    fun create(user: User): User {
        if (user.username == null) user.username = user.email
        if (user.username == null) throw NullPointerException("Username cannot be null")
        if (user.password == null) throw NullPointerException("Password yako cannot be nothing")
        user.password = bCrypt.encode(user.password)

        if (user.firstName == null && user.fullName != null) {
            if (user.fullName!!.indexOf(' ') > 0) {
                user.lastName = user.fullName!!.substringAfterLast(' ')
                user.firstName = user.fullName!!.substringBeforeLast(' ')
            } else {
                user.firstName = user.fullName
                user.lastName = user.fullName
            }
        }
        if (user.firstName == null) throw NullPointerException("First Name cannot be empty")
        if (user.lastName == null) throw NullPointerException("Last name cannot be empty")
        if (user.email == null) throw NullPointerException("Email address cannot be empty")
        user.enabled = true
        if (user.lastPasswordResetDate == null) user.lastPasswordResetDate = Date(1509494400)
        val roleUser = authorityRepository.findByName(AuthorityName.ROLE_USER)
        val authorities = authorityRepository.findAll()
        user.authorities = listOf(roleUser)
        if (user.authorities == null || user.authorities!!.isEmpty()) throw IllegalArgumentException("Failed to set user. Retry")
        if (userRepository.findByUsername(user.username!!) != null) throw IllegalArgumentException("Username taken")
        return userRepository.save(user)
    }

    fun update(user: User): User {
        val usr = userRepository.findByUsername(user.username!!) ?: throw IllegalArgumentException("Invalid user")

        return userRepository.save(usr)
    }
}