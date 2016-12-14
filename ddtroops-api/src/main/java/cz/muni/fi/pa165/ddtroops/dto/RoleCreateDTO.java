package cz.muni.fi.pa165.ddtroops.dto;

/**
 * Created by Petr Kolacek.
 * @author Petr Kolacek
 */
public class RoleCreateDTO {
    
    private String name;
    private String description;
    private int attackPower;
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
