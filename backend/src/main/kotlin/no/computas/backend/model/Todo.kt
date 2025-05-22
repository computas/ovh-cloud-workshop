package no.computas.backend.model

import jakarta.persistence.*

@Entity
@Table(name = "todos")
open class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0,

    @Column(nullable = false)
    open var title: String = "",

    @Column(nullable = false)
    open var description: String = ""
)
