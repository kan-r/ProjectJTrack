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

    private String name;
    private String description;

    @Column(name = "type_code")
    private String typeCode;

    @Column(name = "priority_code")
    private String priorityCode;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "estimated_hours")
    private Double estimatedHours;

    @Column(name = "actual_hours")
    private Double actualHours;

    @Column(name = "parent_id")
    private Integer parentId;
}
