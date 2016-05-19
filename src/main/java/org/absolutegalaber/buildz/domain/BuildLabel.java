package org.absolutegalaber.buildz.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Table(name = "BUILD_LABEL")
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"build"})
@ToString(exclude = {"build"})
public class BuildLabel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "build_id", nullable = false)
    @Getter
    @Setter
    private Build build;

    @Basic(optional = false)
    @Getter
    @Setter
    @Column(name = "label_key", nullable = false)
    private String key;

    @Basic(optional = false)
    @Column(name = "label_value", nullable = false, length = 2000)
    @Getter
    @Setter
    private String value;
}
