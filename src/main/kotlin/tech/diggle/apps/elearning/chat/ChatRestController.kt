package tech.diggle.apps.elearning.chat

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import tech.diggle.apps.elearning.security.jwt.JwtTokenUtil
import tech.diggle.apps.elearning.security.jwt.JwtUser
import tech.diggle.apps.elearning.security.user.UserRepository
import javax.servlet.http.HttpServletRequest

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
class GroupMessageRestController(@Autowired val repository: GroupMessageRepository,
                                 @Autowired val jwtTokenUtil: JwtTokenUtil,
                                 @Autowired val userDetailsService: UserDetailsService,
                                 @Autowired val userRepository: UserRepository,
                                 @Autowired val  chatRoomRepository: ChatRoomRepository) {


    @Value("\${jwt.header}")
    private val tokenHeader: String? = null

    @GetMapping
    fun get() = repository.findAll()

    @PostMapping("{id}")
    fun sendMessage(@RequestBody message: GroupMessage, request: HttpServletRequest, @PathVariable id: Long): GroupMessage? {
//        todo: update this to use the currently logged in user as the sender, not what it is doing.
        val token = request.getHeader(tokenHeader).substring(7)
        val username = jwtTokenUtil.getUsernameFromToken(token)
        message.sender = userRepository.findByUsername(username)
        message.room = chatRoomRepository.findOne(id)
        return repository.save(message)
    }

    @GetMapping("{id}")
    fun getGroupMessages(@PathVariable id: Long): List<GroupMessage> {
        return repository.findByRoomId(id)
    }
}

@RequestMapping("dms")
@RestController
class DMRestController(@Autowired val repository: DMRepository) {
    @GetMapping
    fun get() = repository.findAll()

    @PostMapping
    fun add(@RequestBody message: DM) = repository.save(message)
}

