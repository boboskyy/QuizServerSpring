package pl.nanocoder.testserver.persistence.pojos;

import lombok.*;
import pl.nanocoder.testserver.persistence.model.Group;
import pl.nanocoder.testserver.persistence.model.Person;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupedPeople {
    private Group group;
    private List<Person> people;
}
