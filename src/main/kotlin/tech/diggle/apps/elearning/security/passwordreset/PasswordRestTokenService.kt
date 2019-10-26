package tech.diggle.apps.elearning.security.passwordreset

import tech.diggle.apps.elearning.security.user.User


interface PasswordRestTokenService {
    fun createToken(appUser: User): PasswordResetToken
    fun validateToken(token: PasswordResetToken, request: PasswordResetRequest): Boolean
    fun findByRequest(request: PasswordResetRequest): PasswordResetToken?
    fun findByUser(appUser: User): PasswordResetToken?
}