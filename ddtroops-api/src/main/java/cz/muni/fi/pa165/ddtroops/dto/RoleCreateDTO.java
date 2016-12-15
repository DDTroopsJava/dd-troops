package cz.muni.fi.pa165.ddtroops.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Petr Kolacek.
 * @author Petr Kolacek
 */
public class RoleCreateDTO {

    @NotNull
    @Size(min = 3, max = 50)
    private String name;
    @NotNull
    private String description;
    @Min(0)
    private int attackPower;
    @Min(0)
    private int defensePower;
    
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public int getAttackPower(){
        return attackPower;
    }
    
    public void setAttackPower(int attackPower){
        this.attackPower = attackPower;
    }
    
    public int getDefensePower(){
        return defensePower;
    }
    
    public void setDefensePower(int defensePower){
        this.defensePower = defensePower;
    }
    
}
