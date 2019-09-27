package tech.diggle.apps.elearning.security.jwt


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import tech.diggle.apps.elearning.security.authority.Authority
import tech.diggle.apps.elearning.security.user.User
import tech.diggle.apps.elearning.security.user.UserRepository
import java.util.*

@Service
@Qualifier("jwtUserDetailService")
class JwtUserDetailsServiceImpl : UserDetailsService {

    @Autowired
    private val userRepository: UserRepository? = null

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository?.findByUsername(username)

        return if (user == null) {
            throw UsernameNotFoundException(String.format("No user found with username '%s'.", username))
        } else {
            JwtUserFactory.create(user)
        }
    }

    fun createUser(usr: JwtUser): User {
        val user: User = User().apply {
            username = usr.username
            firstName = usr.firstname
            lastName = usr.lastname
            email = usr.email
            password = usr.password
            authorities = usr.authorities as List<Authority>
            enabled = usr.isEnabled
            this.lastPasswordResetDate = Date(1509494400)
        }
        return userRepository!!.save(user)
    }
}
