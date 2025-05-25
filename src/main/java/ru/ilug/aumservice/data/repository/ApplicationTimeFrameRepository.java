package ru.ilug.aumservice.data.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.ilug.aumservice.data.model.ApplicationTimeFrame;

@Repository
public interface ApplicationTimeFrameRepository extends R2dbcRepository<ApplicationTimeFrame, String> {

    Flux<ApplicationTimeFrame> getApplicationTimeFramesByEndTimeGreaterThanEqual(long endTime);

    @Query("SELECT * FROM application_time_frame f WHERE ((f.start_time <= :endTime and f.start_time >= :startTime) or (f.end_time <= :endTime and f.end_time >= :startTime))")
    Flux<ApplicationTimeFrame> getApplicationTimeFramesInRange(long endTime, long startTime);

}
