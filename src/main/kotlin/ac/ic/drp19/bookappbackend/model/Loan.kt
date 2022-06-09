package ac.ic.drp19.bookappbackend.model

import com.fasterxml.jackson.annotation.JsonFormat
import java.sql.Date
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

@Entity
class Loan(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @ManyToOne
    var fromUser: User,

    @ManyToOne
    var toUser: User,

    @ManyToOne
    var book: Book,

    @NotNull
    @Min(1)
    var copies: Int,

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    var date: Date = Date.valueOf(LocalDate.now()),

    var returnDate: Date,

    @OneToMany(mappedBy = "loan")
    var returns: List<Return> = emptyList()
)
