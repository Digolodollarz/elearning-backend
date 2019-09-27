package tech.diggle.apps.elearning.stream

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("stream")
class StreamRestController(@Autowired val repository: StreamRepository) {
    @GetMapping
    fun getAll() = repository.findAll()

    @PostMapping
    fun add(@RequestBody stream: Stream) = repository.save(stream)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

}

@RestController
@RequestMapping("module")
class ModuleRestController(@Autowired val repository: ModuleRepository) {
    @GetMapping
    fun getAll() = repository.findAll()

    @PostMapping
    fun add(@RequestBody module: Module) = repository.save(module)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

}

@RestController
@RequestMapping("level")
class LevelRestController(@Autowired val repository: LevelRepository) {
    @GetMapping
    fun getAll() = repository.findAll()

    @PostMapping
    fun add(@RequestBody level: Level) = repository.save(level)

    @GetMapping("{id}")
    fun getOne(@PathVariable id: Long) = repository.findOne(id)

}