package org.example;

import jakarta.persistence.*;

import org.example.Director;

import java.util.Objects;

@Entity
public class Film extends BaseEntity{

    /*
    Remove duplicate id field that shadows BaseEntity's @Id mapping

    Film extends BaseEntity, which already defines @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id.
    The duplicate private Long id declaration on line 12 shadows the inherited id and breaks JPA entity mapping.
    The corresponding setId/getId methods (lines 36–42) operate on this shadow field instead of the actual mapped id from BaseEntity.
     */
    private Long id;

    private String title;
    @ManyToOne


    private Director director;

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {

        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Film)) return false;
        Film other = (Film) o;
        return id != null && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
