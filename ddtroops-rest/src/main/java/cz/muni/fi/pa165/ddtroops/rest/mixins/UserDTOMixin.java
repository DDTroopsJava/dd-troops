package cz.muni.fi.pa165.ddtroops.rest.mixins;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by pstanko.
 * @author pstanko
 */
@JsonIgnoreProperties({ "passwordHash"})
public class UserDTOMixin {

}
