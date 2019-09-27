package tech.diggle.apps.elearning.security.authority

import org.springframework.data.repository.CrudRepository

interface AuthorityRepository : CrudRepository<Authority, Long> {
    fun findByName(name: AuthorityName): Authority
}