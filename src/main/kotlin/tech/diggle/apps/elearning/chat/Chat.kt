package tech.diggle.apps.elearning.chat

import tech.diggle.apps.elearning.security.user.User
import java.util.*
import javax.persistence.*

@Entity
abstract class BaseModel {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @Column(name = "created")
    val created = Date()

    @Column(name = "title")
    var title: String? = null

    @Column(name = "description")
    var description: String? = null

    override fun toString(): String {
        return if (this.title != null) this.title!! else super.toString()
    }
}

@Entity
class ChatRoom : BaseModel() {
    @ManyToMany
    var users: List<User> = listOf()
}


@Entity
open class ChatMessage : BaseModel() {
    @Column(name = "message")
    var message = ""

    @ManyToOne
    var sender: User? = null
}

@Entity
class GroupMessage : ChatMessage() {
    @ManyToOne
    var room: ChatRoom? = null
}

@Entity
class DM : ChatMessage(){
    @ManyToOne
    var recipient: User? = null
}