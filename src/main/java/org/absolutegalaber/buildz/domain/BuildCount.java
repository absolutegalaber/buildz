package org.absolutegalaber.buildz.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Table(
        name = "BUILD_COUNT",
        uniqueConstraints = {@UniqueConstraint(name = "UNIQUE_PROJECT_BRANCH", columnNames = {"project", "branch"})}
)
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BuildCount {

    public BuildCount(String project, String branch) {
        this.project = project;
        this.branch = branch;
        this.count = 0L;
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
    @Column(name = "counter", nullable = false)
    @Min(1)
    @Getter
    @Setter
    private Long count;

    public void increment() {
        count++;
    }
}
