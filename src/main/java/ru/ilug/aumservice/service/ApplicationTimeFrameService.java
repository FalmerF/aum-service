package ru.ilug.aumservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ilug.aumservice.data.model.ApplicationTimeFrame;
import ru.ilug.aumservice.data.repository.ApplicationTimeFrameRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationTimeFrameService {

    private final ApplicationTimeFrameRepository repository;

    @Transactional
    public Mono<Void> addTimeFrames(Collection<ApplicationTimeFrame> newFrames) {
        long minTime = getFramesMinTime(newFrames);

        return repository.getApplicationTimeFramesByEndTimeGreaterThanEqual(minTime)
                .collectList()
                .flatMap(frames -> mergeFrames(frames, newFrames));
    }

    private long getFramesMinTime(Collection<ApplicationTimeFrame> frames) {
        return frames.stream().mapToLong(ApplicationTimeFrame::getStartTime).min().orElse(0L);
    }

    private long getFramesMaxTime(Collection<ApplicationTimeFrame> frames) {
        return frames.stream().mapToLong(ApplicationTimeFrame::getStartTime).max().orElse(0L);
    }

    private Mono<Void> mergeFrames(Collection<ApplicationTimeFrame> frames, Collection<ApplicationTimeFrame> newFrames) {
        List<ApplicationTimeFrame> framesToRemove = new ArrayList<>();
        List<ApplicationTimeFrame> framesToSave = new ArrayList<>();

        for (ApplicationTimeFrame frame : frames) {
            List<ApplicationTimeFrame> framesToMerge = new ArrayList<>(newFrames.stream()
                    .filter(f -> f.getExePath().equals(frame.getExePath()))
                    .filter(f -> isIntersectingFrames(f, frame))
                    .toList());

            framesToRemove.addAll(framesToMerge);
            framesToMerge.add(0, frame);

            ApplicationTimeFrame mergedFrame = mergeFrames(framesToMerge);
            framesToSave.add(mergedFrame);
        }

        return Flux.fromIterable(framesToRemove)
                .flatMap(repository::delete)
                .thenMany(Flux.fromIterable(framesToSave))
                .flatMap(repository::save)
                .then();
    }

    private ApplicationTimeFrame mergeFrames(List<ApplicationTimeFrame> frames) {
        if (frames.size() == 1) {
            return frames.get(0);
        }

        long minTime = getFramesMinTime(frames);
        long maxTime = getFramesMaxTime(frames);

        ApplicationTimeFrame frame = frames.get(0);

        return ApplicationTimeFrame.builder()
                .exePath(frame.getExePath())
                .windowsClass(frame.getWindowsClass())
                .startTime(minTime)
                .endTime(maxTime)
                .build();
    }

    private boolean isIntersectingFrames(ApplicationTimeFrame frame1, ApplicationTimeFrame frame2) {
        return frame1.getStartTime() <= frame2.getEndTime() || frame1.getEndTime() >= frame2.getStartTime();
    }
}
