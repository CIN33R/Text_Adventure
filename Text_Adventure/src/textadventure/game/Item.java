package textadventure.game;

public class Item {
    private String name; 
    private String description; 
    
    int damage; 
    
    public Item(String name, String description) {
        this.name = name; 
        this.description = description; 
        damage = 0; 
    }
    
    public String getName() {
        return name; 
    }
    
    public String getDescription() {
        return description; 
    }
    
    public void setDamage(int value) {
        damage = value; 
    }
    
    public int getDamage() {
        return damage; 
    }
    
    
}