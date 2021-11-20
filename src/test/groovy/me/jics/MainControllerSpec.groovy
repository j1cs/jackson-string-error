package me.jics

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.extensions.spock.annotation.MicronautTest
import jakarta.inject.Inject
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

@MicronautTest
class MainControllerSpec extends Specification {

    @Shared
    @Inject
    EmbeddedServer embeddedServer

    @Shared
    @AutoCleanup
    @Inject
    @Client("/")
    HttpClient client

    void "test post"() {
        given:
        HttpRequest request = HttpRequest.POST("/main", Request.builder()
                .name("test")
                .date(LocalDateTime.now()).build())
        HttpResponse response = client.toBlocking().exchange(request, Argument.of(String))

        expect:
        response.status == HttpStatus.OK
    }
}
