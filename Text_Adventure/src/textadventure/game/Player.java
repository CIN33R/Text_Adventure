package textadventure.game;

import java.util.HashMap; 
import java.util.Set; 

public class Player {
    private HashMap<String, Item> inventory; 
    int health; 
    private String name; 
    
    public Player (String name) {
        this.name = name; 
        inventory = new HashMap<>(); 
        health = 100; 
    }
    
    public String getName() {
        return name; 
    }
    
    public int getHealth() {
        return health; 
    }
    
    public void adjustHealth(int value) {
        health += value; 
    }
    
    public void setItem(String name, Item item) {
        inventory.put(name, item); 
    }
    
    public Item removeItem(String key) {
        return inventory.remove(key);  
    }
    
    public Item getItem(String key) {
        return inventory.get(key);
    }
    
    public String getInventoryString() {
         String returnString = "Player Inventory: "; 
        
        Set<String> keys = inventory.keySet(); 
        for(String item: keys) {
            returnString += " " + item; 
        }
        
        System.out.println(" player health is " + health); 
        return returnString; 
    }
    
}