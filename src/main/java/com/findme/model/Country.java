package com.findme.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRY")
@Getter @Setter @ToString
@EqualsAndHashCode(callSuper = true)

public class Country extends GeneralModel{

    @Column(name = "NAME")
    private String name;

}
