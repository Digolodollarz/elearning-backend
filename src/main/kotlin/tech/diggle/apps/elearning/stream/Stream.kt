package tech.diggle.apps.elearning.stream

import tech.diggle.apps.elearning.chat.ChatRoom
import tech.diggle.apps.elearning.security.user.User
import java.util.*
import javax.persistence.*

@Entity
class Stream {
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

    @ManyToOne
    val level: Level? = null

    @ManyToMany
    var students: List<User> = listOf()

    @ManyToOne
    var lecturer: User? = null

    @ManyToOne
    var tutor: User? = null

    @ManyToMany
    var modules: List<Module> = listOf()

    @OneToOne
    var chat: ChatRoom? = null
}


@Entity
class Level {
    @Id
    @Column
    val id: Long? = null

    @Column
    val title = ""
}

@Entity
class Module {
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

    @ManyToOne
    val level: Level? = null

    @ManyToMany(fetch = FetchType.EAGER)
    var students: Set<User> = setOf()

    @ManyToOne
    var lecturer: User? = null

    @ManyToOne
    var tutor: User? = null

    @OneToOne
    var chat: ChatRoom? = null

    override fun toString(): String {
        return if (this.title != null) this.title!! else super.toString()
    }

    @OneToMany(fetch = FetchType.EAGER)
    var classWork: Set<ClassWork> = setOf()
}

@Entity
class ClassWork {
    @Id
    @GeneratedValue
    val id: Long? = null

    @ManyToOne
    val module: Module? = null

    @Column
    val created = Date()

    @Column
    val title = ""

    @Column
    val description = ""

    @Column
    val dueDate: Date? = null

    @Column
    val extendedDeadline: Date? = null

    @OneToMany(fetch = FetchType.EAGER)
    val answers: Set<ClassWorkAnswer> = setOf()

    @Column
    val attachment: String? = null
}

@Entity
class ClassWorkAnswer {
    @Id
    @GeneratedValue
    val id: Long? = null

    @Column
    val title = ""

    @Column
    val description = ""

    @Column
    val submissionDate: Date = Date()

    @ManyToOne
    var student: User? = null

    @ManyToOne
    var classWork: ClassWork? = null

    @Column
    val attachment: String? = null
}