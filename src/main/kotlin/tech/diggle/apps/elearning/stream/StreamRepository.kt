package tech.diggle.apps.elearning.stream

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.PagingAndSortingRepository

interface StreamRepository : PagingAndSortingRepository<Stream, Long>

interface LevelRepository : PagingAndSortingRepository<Level, Long>

interface ModuleRepository : PagingAndSortingRepository<Module, Long> {
    fun findAllByLevelId(id: Long): List<Module>
    fun findAllByLevelIdAndDepartmentId(level: Long, department: Long): List<Module>
}

interface ClassWorkRepository : PagingAndSortingRepository<ClassWork, Long> {
    fun findAllByModuleId(id: Long): List<ClassWork>
}

interface ClassWorkAnswersRepository : PagingAndSortingRepository<ClassWorkAnswer, Long> {
    fun findByStudentIdAndClassWorkId(userId: Long, classWorkId: Long): List<ClassWorkAnswer>
    fun findByStudentId(userId: Long): List<ClassWorkAnswer>
    fun findByClassWorkId(classWorkId: Long): List<ClassWorkAnswer>
}