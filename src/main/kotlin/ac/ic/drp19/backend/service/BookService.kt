package ac.ic.drp19.backend.service

import ac.ic.drp19.backend.model.Book
import ac.ic.drp19.backend.repository.BookRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import kotlin.jvm.Throws

@Service
class BookService(
    val db: BookRepository,
    val olService: OpenLibraryService
) {

    fun findBooks(): List<Book> = db.findBooks()

    fun findBookById(id: Long): Book? = db.findByIdOrNull(id)

    fun findBookByIsbn(isbn: String): Book? = db.findByIsbn(isbn)

    @Throws(ResponseStatusException::class)
    fun postBookByIsbn(isbn: String): Book {
        var book = findBookByIsbn(isbn)
        if (book == null) {
            book = olService
                .retrieveBookObject(isbn)
                .let {
                    Book(
                        isbn = isbn,
                        title = it.title,
                        published = it.publishDate
                    )
                }
            db.save(book)
        }
        return book
    }
}
