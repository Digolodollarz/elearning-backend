package tech.diggle.apps.elearning.stream

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Required
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import tech.diggle.apps.elearning.chat.ChatRoom
import tech.diggle.apps.elearning.chat.ChatRoomRepository
import tech.diggle.apps.elearning.security.jwt.JwtTokenUtil
import tech.diggle.apps.elearning.security.user.UserRepository
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("streams")
class StreamRestController(@Autowired val repository: StreamRepository) {
    @GetMapping
    fun getAll() = repository.findAll()

    @PostMapping
    fun add(@RequestBody stream: Stream) = repository.save(stream)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

}

@RestController
@RequestMapping("modules")
class ModuleRestController(@Autowired val repository: ModuleRepository,
                           @Autowired val chatRoomRepository: ChatRoomRepository,
                           @Autowired val classWorkRepository: ClassWorkRepository,
                           @Autowired val userRepository: UserRepository) {
    @GetMapping
    fun getAll() = repository.findAll()

    @PostMapping
    fun add(@RequestBody module: Module): Module? {
        var chat = ChatRoom()
        chat.description = module.description
        chat.title = module.title
        chat = chatRoomRepository.save(chat)
        module.chat = chat
        return repository.save(module)
    }

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

    @GetMapping("{id}/classwork")
    fun getClassWork(@PathVariable id: Long) = classWorkRepository.findAllByModuleId(id)

    @GetMapping("{id}/users")
    fun getUsers(@PathVariable id: Long) = repository.findOne(id).students

    @GetMapping("{id}/findUsers")
    fun findUsers(@PathVariable id: Long) = userRepository.findByLevelId(id)

    @GetMapping("delete/{id}")
    fun deleteModule(@PathVariable id: Long) = repository.delete(id)
}

@RestController
@RequestMapping("levels")
class LevelRestController(@Autowired val repository: LevelRepository,
                          @Autowired val moduleRepository: ModuleRepository) {
    @GetMapping
    fun getAll() = repository.findAll()

    @PostMapping
    fun add(@RequestBody level: Level) = repository.save(level)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

    @GetMapping("{id}/modules")
    fun getModules(@PathVariable id: Long): List<Module> {
        return moduleRepository.findAllByLevelId(id)
    }

    @GetMapping("{id}/{department}/modules")
    fun getDepartmentModules(@PathVariable id: Long, @PathVariable department: Long): List<Module> {
        return moduleRepository.findAllByLevelIdAndDepartmentId(id, department)
    }
}

@RestController
@RequestMapping("class-work")
class ClassWorkController(@Autowired val repository: ClassWorkRepository,
                          @Autowired val answersRepository: ClassWorkAnswersRepository,
                          @Autowired val userRepository: UserRepository,
                          @Autowired val jwtTokenUtil: JwtTokenUtil,
                          @Value("\${jwt.header}") val tokenHeader: String) {
    @GetMapping
    fun getAll() = repository.findAll()

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

    @PostMapping("{id}")
    fun update(@RequestBody work: ClassWork) = repository.save(work)

    @PostMapping
    fun addNew(@RequestBody classWork: ClassWork): ClassWork? {
        return repository.save(classWork)
    }

    @GetMapping("search")
    fun findByModule(@RequestParam moduleId: Optional<Long>): List<ClassWork> {
        if (!moduleId.isPresent) throw IllegalArgumentException()
        return repository.findAllByModuleId(moduleId.get())
    }

    @PostMapping("{id}/answers")
    fun addAnswer(@RequestBody classWorkAnswer: ClassWorkAnswer, request: HttpServletRequest): ClassWorkAnswer? {
        val token = request.getHeader(tokenHeader).substring(7)
        val username = jwtTokenUtil.getUsernameFromToken(token)
        classWorkAnswer.student = userRepository.findByUsername(username)
        return answersRepository.save(classWorkAnswer)
    }

    @GetMapping("{id}/answers")
    fun getAnswers(@PathVariable id: Long): List<ClassWorkAnswer> {
        return answersRepository.findByClassWorkId(id)
    }
}