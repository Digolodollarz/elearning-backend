package tech.diggle.apps.elearning.stream

import org.jetbrains.annotations.NotNull
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
    @NotNull
    var lecturer: User? = null

    @ManyToOne
    var tutor: User? = null

    @ManyToMany
    var modules: List<Module> = listOf()
}


@Entity
class Level {
    @Id
    @Column
    val id: Long = 0

    @Column
    val title = ""
}

@Entity
class Module{
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