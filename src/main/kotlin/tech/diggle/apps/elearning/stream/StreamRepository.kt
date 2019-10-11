package tech.diggle.apps.elearning.stream

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.PagingAndSortingRepository

interface StreamRepository: PagingAndSortingRepository<Stream, Long>

interface LevelRepository: PagingAndSortingRepository<Level, Long>

interface ModuleRepository: PagingAndSortingRepository<Module, Long>

interface ClassWorkRepository: PagingAndSortingRepository<ClassWork, Long>{
    fun findAllByModuleId(id: Long, page: PageRequest): Page<ClassWork>
}

interface ClassWorkAnswersRepository: PagingAndSortingRepository<ClassWorkAnswer, Long>{
    fun findByUserIdAndClassWorkId(userId: Long, classWorkId: Long): List<ClassWorkAnswer>
    fun findByUserId(userId: Long): List<ClassWorkAnswer>
}