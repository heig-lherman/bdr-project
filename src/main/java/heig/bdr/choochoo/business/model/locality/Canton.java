package heig.bdr.choochoo.business.model.locality;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "canton")
@Getter
@Setter
@Accessors(chain = true)
public class Canton {

    @Id
    @Column(name = "code", length = 2, nullable = false)
    private String isoCode;

    @Column(name = "name", nullable = false)
    private String name;
}
