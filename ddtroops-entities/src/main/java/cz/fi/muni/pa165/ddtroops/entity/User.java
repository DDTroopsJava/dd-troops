package cz.fi.muni.pa165.ddtroops.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 * @author
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable=false,unique=true)
    private String name;

    // TODO

}
