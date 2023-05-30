package pl.nanocoder.testserver.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.nanocoder.testserver.web.dto.sub_dto.AnsweredQuestionDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class FinishQuizDto {

    @NotNull
    private Long quizInstanceId;

    @NotNull
    @Valid
    private List<AnsweredQuestionDto> data;
}
