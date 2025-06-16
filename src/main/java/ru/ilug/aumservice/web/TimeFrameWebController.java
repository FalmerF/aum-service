package ru.ilug.aumservice.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import ru.ilug.aumservice.data.model.ApplicationStatistic;
import ru.ilug.aumservice.data.model.ApplicationTimeFrame;
import ru.ilug.aumservice.service.ApplicationTimeFrameService;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@RestController
@RequestMapping("/timeframe")
@RequiredArgsConstructor
public class TimeFrameWebController {

    private final ApplicationTimeFrameService applicationTimeFrameService;

    @PostMapping(path = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postTimeFrames(@RequestBody List<ApplicationTimeFrame> frames, @AuthenticationPrincipal Jwt jwt) {
        long userId = jwt.getClaim("user-id");
        applicationTimeFrameService.addTimeFrames(userId, frames).subscribe();
    }

    @GetMapping(path = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ApplicationStatistic> getStatistics(@AuthenticationPrincipal Jwt jwt) {
        long userId = jwt.getClaim("user-id");
        long endTime = Instant.now().toEpochMilli();
        long startTime = endTime - TimeUnit.DAYS.toMillis(1);
        return applicationTimeFrameService.getStatistics(userId, startTime, endTime);
    }

}
