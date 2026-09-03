package com.kan.jtrack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "jobs")
public class Job extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sprint_id")
    private Integer sprintId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sprint_id", insertable = false, updatable = false)
    private Sprint sprint;

    private String name;
    private String description;

    @Column(name = "type_code")
    private String typeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_code", insertable = false, updatable = false)
    private JobType type;

    @Column(name = "priority_code")
    private String priorityCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_code", insertable = false, updatable = false)
    private JobPriority priority;

    @Column(name = "status_code")
    private String statusCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_code", insertable = false, updatable = false)
    private JobStatus status;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "estimated_hours")
    private Double estimatedHours;

    @Column(name = "actual_hours")
    private Double actualHours;

    @Column(name = "parent_id")
    private Integer parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Job parent;
}
