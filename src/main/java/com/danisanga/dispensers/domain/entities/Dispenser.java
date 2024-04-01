package com.danisanga.dispensers.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "dispensers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dispenser {

    @Id
    @Type(type="org.hibernate.type.UUIDCharType")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
    private Double flowVolume;
    private Date createdAt;
    @OneToMany(mappedBy = "dispenser", fetch = FetchType.LAZY)
    private List<Usage> usages;
}
