package ru.ilug.aumservice.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ApplicationTimeFrame {

    @Id
    @JsonIgnore
    private long id;
    private long userId;
    private String exePath;
    private String windowsClass;
    private long startTime;
    private long endTime;

}
