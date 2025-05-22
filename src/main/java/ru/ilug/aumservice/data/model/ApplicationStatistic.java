package ru.ilug.aumservice.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApplicationStatistic {

    private String exePath;
    private long seconds;

    public void addSeconds(long seconds) {
        this.seconds += seconds;
    }

}
