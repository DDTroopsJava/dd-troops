package cz.muni.fi.pa165.ddtroops.dto;

/**
 * Created by Peter Zaoral
 *
 * @author Peter Zaoral.
 */
public class HeroUpdateDTO extends HeroCreateDTO {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
