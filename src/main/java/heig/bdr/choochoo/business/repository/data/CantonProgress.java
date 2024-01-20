package heig.bdr.choochoo.business.repository.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Getter
public class CantonProgress {

    @Id
    private String cantonCode;
    private String cantonName;
    private long travelledCount;
    private long totalCount;
}
