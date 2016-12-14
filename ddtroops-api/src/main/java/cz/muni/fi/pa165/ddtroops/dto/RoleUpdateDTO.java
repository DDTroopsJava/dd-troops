package cz.muni.fi.pa165.ddtroops.dto;

/**
 *
 * @author Petr Kolacek
 */
public class RoleUpdateDTO extends RoleCreateDTO {
    
    private Long id;
    
    public Long getId(){
        return id;
    }
    
    public void setId(Long id){
        this.id = id;
    }
    
}
