package jp.co.gh.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "timezones")
public class Timezones implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "abbrev")
    private String abbrev;

    @Column(name = "utc_offset")
    private java.time.Duration utcOffset;

    @Column(name = "is_dst")
    private Boolean isDst;
}
