package tech.diggle.apps.elearning.security.user


import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByLevelId(id: Long): List<User>
}