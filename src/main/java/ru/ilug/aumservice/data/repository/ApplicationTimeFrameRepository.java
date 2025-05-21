package ru.ilug.aumservice.data.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.ilug.aumservice.data.model.ApplicationTimeFrame;

@Repository
public interface ApplicationTimeFrameRepository extends R2dbcRepository<ApplicationTimeFrame, String> {

    Flux<ApplicationTimeFrame> getApplicationTimeFramesByEndTimeGreaterThanEqual(long endTimeIsGreaterThan);

}
