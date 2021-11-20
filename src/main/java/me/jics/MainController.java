package me.jics;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Controller("/main")
@RequiredArgsConstructor
@Validated
@Slf4j
public class MainController {

    private final ObjectMapper mapper;

    @Post
    public String index(@Body @Valid Request request) throws JsonProcessingException {
        log.info("{}", mapper.writeValueAsString(request));
        return request.getName();
    }
}