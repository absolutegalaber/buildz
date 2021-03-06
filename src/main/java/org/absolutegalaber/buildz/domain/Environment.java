package org.absolutegalaber.buildz.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Table(name = "environment", uniqueConstraints = {@UniqueConstraint(name = "UNIQUE_ENVIRONMENT_NAME", columnNames = "name")})
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Environment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Basic(optional = false)
    @Column(name = "name")
    @Getter
    @Setter
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "environment", cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Set<Artifact> artifacts = new HashSet<>();
}
