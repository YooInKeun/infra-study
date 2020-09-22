package com3.demo.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column
    private String key;

    @Column
    private Long size;

    public File(String key, long size) {
        this.key = key;
        this.size = size;
    }
}
