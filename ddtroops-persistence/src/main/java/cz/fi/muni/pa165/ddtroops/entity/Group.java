package cz.fi.muni.pa165.ddtroops.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author
 */
@Entity
public class Group {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
}
