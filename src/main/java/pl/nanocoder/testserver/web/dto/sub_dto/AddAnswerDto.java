package pl.nanocoder.testserver.web.dto.sub_dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AddAnswerDto {

    @NotBlank
    private String value;

    @NotNull
    private boolean correct;
}
