package tech.diggle.apps.elearning.stream

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import tech.diggle.apps.elearning.chat.ChatRoom
import tech.diggle.apps.elearning.chat.ChatRoomRepository

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
                           @Autowired val chatRoomRepository: ChatRoomRepository) {
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

}

@RestController
@RequestMapping("levels")
class LevelRestController(@Autowired val repository: LevelRepository) {
    @GetMapping
    fun getAll() = repository.findAll()

    @PostMapping
    fun add(@RequestBody level: Level) = repository.save(level)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

}

@RestController
@RequestMapping("class-work")
class ClassWorkController(@Autowired val repository: ClassWorkRepository){
    @GetMapping
    fun getAll() = repository.findAll()

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

    @PostMapping
    fun addNew(@RequestBody classWork: ClassWork): ClassWork? {
        return repository.save(classWork)
    }

    @PostMapping("{id}/answers")
    fun addAnswer(@RequestBody classWorkAnswer: ClassWorkAnswer){

    }
}