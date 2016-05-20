package org.absolutegalaber.buildz.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Josip.Mihelko @ Gmail
 */
@Entity
@Table(name = "artifact")
@NoArgsConstructor
@EqualsAndHashCode(exclude = "environment")
@ToString(exclude = "environment")
public class Artifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "environment_id", nullable = false)
    @Getter
    @Setter
    private Environment environment;

    @Basic
    @Column(name = "project")
    @Getter
    @Setter
    private String project;

    @Basic
    @Column(name = "branch")
    @Getter
    @Setter
    private String branch;

    @ElementCollection
    @CollectionTable(name = "artifact_labels", joinColumns = @JoinColumn(name = "artifact_id"))
    @Getter
    @Setter
    private Map<String, String> labels = new HashMap<>();
}
