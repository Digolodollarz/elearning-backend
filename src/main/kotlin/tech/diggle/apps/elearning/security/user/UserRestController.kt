package tech.diggle.apps.elearning.security.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import tech.diggle.apps.elearning.security.authority.AuthorityRepository
import tech.diggle.apps.elearning.security.jwt.JwtTokenUtil
import tech.diggle.apps.elearning.security.jwt.JwtUser

import javax.servlet.http.HttpServletRequest

@RestController
class UserRestController(@Autowired val repository: UserRepository,
                         @Autowired val authorityRepository: AuthorityRepository) {

    @Value("\${jwt.header}")
    private val tokenHeader: String? = null

    @Autowired
    private val jwtTokenUtil: JwtTokenUtil? = null

    @Autowired
    @Qualifier("jwtUserDetailService")
    private val userDetailsService: UserDetailsService? = null

    @Autowired
    private val userDetailService: UserDetailServiceImpl? = null

    @RequestMapping("user", method = [(RequestMethod.GET)])
    fun getAuthenticatedUser(request: HttpServletRequest): User? {
        val token = request.getHeader(tokenHeader).substring(7)
        val username = jwtTokenUtil!!.getUsernameFromToken(token)
        return repository.findByUsername(username)
    }

    @GetMapping("user/{userName}")
    fun getUser(@PathVariable userName: String): JwtUser {
        return userDetailsService!!.loadUserByUsername(userName) as JwtUser
    }

    @PostMapping("user")
    @PreAuthorize("hasRole('ADMIN')")
    fun addUser(@RequestBody user: User): UserDetails? {
        val usr = userDetailService!!.create(user)
        return userDetailsService!!.loadUserByUsername(usr.username)
    }

    @GetMapping("users")
    @PreAuthorize("hasRole('ADMIN')")
    fun listUsers(): List<User> {
        return repository.findAll()
    }

    @PostMapping("users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateUser(@RequestBody user: User): User? {
        return repository.save(user)
    }

    @GetMapping("authorities")
    fun getAuthorities() = authorityRepository.findAll()
}
