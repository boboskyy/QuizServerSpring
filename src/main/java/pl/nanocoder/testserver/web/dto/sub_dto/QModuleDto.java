package pl.nanocoder.testserver.web.dto.sub_dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
public class QModuleDto {

    @NotNull
    private Long groupId;

    @NotNull
    @Min(0)
    private Integer count;
}
