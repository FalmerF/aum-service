package ru.ilug.aumservice.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ilug.aumservice.data.model.ApplicationTimeFrame;
import ru.ilug.aumservice.service.ApplicationTimeFrameService;

import java.util.List;

@RestController
@RequestMapping("/time-frame")
@RequiredArgsConstructor
public class TimeFrameWebController {

    private final ApplicationTimeFrameService applicationTimeFrameService;

    @PostMapping(path = "/post", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postTimeFrames(@RequestBody List<ApplicationTimeFrame> frames) {
        applicationTimeFrameService.addTimeFrames(frames).subscribe();
    }

}
