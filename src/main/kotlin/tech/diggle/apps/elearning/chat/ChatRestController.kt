package tech.diggle.apps.elearning.chat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping("chat-rooms")
@RestController
class ChatRoomRestController(@Autowired val chatRoomRepository: ChatRoomRepository) {
    @GetMapping
    fun get() = chatRoomRepository.findAll()

    @PostMapping
    fun add(@RequestBody room: ChatRoom) = chatRoomRepository.save(room)

    @GetMapping("{id}")
    fun getRoom(@PathVariable id: Long): ChatRoom? {
        return chatRoomRepository.findOne(id)
    }
}

@RequestMapping("group-messages")
@RestController
class GroupMessageRestController(@Autowired val repository: GroupMessageRepository) {
    @GetMapping
    fun get() = repository.findAll()

    @PostMapping
    fun add(@RequestBody message: GroupMessage) = repository.save(message)
}

@RequestMapping("dms")
@RestController
class DMRestController(@Autowired val repository: DMRepository){
    @GetMapping
    fun get() = repository.findAll()

    @PostMapping
    fun add(@RequestBody message: DM) = repository.save(message)
}

