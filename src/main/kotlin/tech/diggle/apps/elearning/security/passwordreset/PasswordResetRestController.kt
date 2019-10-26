package tech.diggle.apps.elearning.security.passwordreset

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.diggle.apps.elearning.InvalidArgumentStateException
import tech.diggle.apps.elearning.NullArgumentException
import tech.diggle.apps.elearning.security.user.User
import tech.diggle.apps.elearning.security.user.UserDetailServiceImpl
import java.security.Principal
import java.util.*


@RestController
class PasswordResetRestController(@Autowired val userDetails: UserDetailServiceImpl,
                                  @Autowired val tokenService: PasswordRestTokenService) {
    /**
     * Method is used to request for password reset token
     * The parameter passed is either username or email. If a user is not found an exception is thrown.
     * No one knows the exception.
     * TODO: Throw 4xx error instead!
     */

    @PostMapping("auth/request-pass")
    fun getToken(@RequestBody request: PasswordResetRequest): Map<String, Date> {
        val appUser: User = userDetails.findByUsernameOrEmail(request.email!!)
                ?: throw IllegalArgumentException("Username not found. Please try again")
        val token = tokenService.createToken(appUser)

        return hashMapOf("expires" to token.expiryDate)
    }

    @PostMapping("auth/update")
    fun changePassword(@RequestBody request: PasswordResetRequest): Map<String, Any> {
        if (request.username == null && request.email == null) throw NullArgumentException("Username is empty")
        if (request.token == null) throw NullArgumentException("Token is empty")
        if (request.newPassword == null) throw NullArgumentException("New Password empty")
        if (request.newPassword.length < 6) throw InvalidArgumentStateException("Password too short")
        val user = userDetails.findByUsernameOrEmail(request.username ?: request.email!!)
                ?: throw Exception("AppUser not found")
        val token = tokenService.findByUser(user) ?: throw InvalidArgumentStateException("Invalid token")
        if (tokenService.validateToken(token, request)) {
            userDetails.update(userDetails.updatePassword(user, request.newPassword))
            return hashMapOf("message" to "done")
        }
        throw InvalidArgumentStateException("Expired or Invalid Token")
    }

    /**
     * Change password the normal way
     * Parameters: Current password, username, new password
     * Requires authentication
     * TODO: Do it in another controller
     */
    @PostMapping("change")
    fun updatePassword(@AuthenticationPrincipal user: Principal, @RequestBody request: PasswordResetRequest) {
        if (user == null) throw NullPointerException()

    }

}