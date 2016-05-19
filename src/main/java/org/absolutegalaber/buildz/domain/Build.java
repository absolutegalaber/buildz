package org.absolutegalaber.buildz.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Table(
        name = "BUILD",
        uniqueConstraints = {
                @UniqueConstraint(name = "UNIQUE_BUILD", columnNames = {"project", "branch", "build_number"})
        }
)
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"labels"})
@ToString
public class Build {
    public Build(String project, String branch, Long buildnumber) {
        this.project = project;
        this.branch = branch;
        this.buildNumber = buildnumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Basic(optional = false)
    @Column(name = "project", nullable = false)
    @NotNull
    @Getter
    @Setter
    private String project;

    @Basic(optional = false)
    @Column(name = "branch", nullable = false)
    @NotNull
    @Getter
    @Setter
    private String branch;

    @Basic
    @Column(name = "build_number", nullable = false)
    @Getter
    @Setter
    private Long buildNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "build", cascade = CascadeType.ALL)
    @Getter
    @Setter
    private Set<BuildLabel> labels = new HashSet<>();
}
