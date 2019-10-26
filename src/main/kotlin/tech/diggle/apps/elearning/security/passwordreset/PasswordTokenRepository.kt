package tech.diggle.apps.elearning.security.passwordreset

import org.springframework.data.jpa.repository.JpaRepository
import tech.diggle.apps.elearning.security.user.User

interface PasswordTokenRepository: JpaRepository<PasswordResetToken, Long>{
    fun findByUser(appUser: User): PasswordResetToken?
}