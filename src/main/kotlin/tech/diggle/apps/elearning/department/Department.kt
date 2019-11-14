package tech.diggle.apps.elearning.department

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.web.bind.annotation.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Department {
    @GeneratedValue
    @Id
    var id: Long? = 0

    @Column
    var title: String = ""
}


interface DepartmentRepository : PagingAndSortingRepository<Department, Long>


@RestController
@RequestMapping("departments")
class DepartmentRestController(@Autowired val repository: DepartmentRepository) {
    @GetMapping
    fun getAll() = repository.findAll()

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

    @PostMapping
    fun add(@RequestBody department: Department) = repository.save(department)
}