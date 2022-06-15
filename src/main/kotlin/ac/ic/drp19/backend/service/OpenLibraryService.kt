package ac.ic.drp19.backend.service

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpHeaders.*
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.*
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient
import kotlin.jvm.Throws

@Service
final class OpenLibraryService {

    private val webClient = WebClient
        .builder()
        .baseUrl("https://openlibrary.org")
        .clientConnector(
            ReactorClientHttpConnector(
                HttpClient.create().followRedirect(true)
            )
        )
        .build()

    fun retrieveBookObject(isbn: String): Mono<OpenLibraryBook?> {
        return webClient
            .get()
            .uri("/isbn/{isbn}.json", isbn)
            .accept(APPLICATION_JSON)
            .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .retrieve()
            .bodyToMono(OpenLibraryBook::class.java)
            .onErrorResume { Mono.empty() }
    }
}

class OpenLibraryBook(
    val title: String,
    @JsonProperty("publish_date") val publishDate: String = ""
)
