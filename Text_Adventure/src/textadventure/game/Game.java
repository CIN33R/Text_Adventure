package textadventure.game;

import java.util.ArrayList; 
import java.util.Random; 

public class Game {
    private Parser parser; 
    private Room currentRoom; 
    private Player player;
    private Player npc; 
    private CLS cls_var; 
    private Random rand; 
    private ArrayList<Room> roomList;
    
    public Game() {
        parser = new Parser();
        player = new Player("player"); 
        npc = new Player("npc"); 
        rand = new Random(); 
    }
    
    public static void main(String[] args) {
        Game game = new Game(); 
        game.setupGame(); 
        game.play(); 
    }
    
    public void printInformation() {
        System.out.println(currentRoom.getShortDescription());
        System.out.println(currentRoom.getExitString()); 
        System.out.println(currentRoom.getInventoryString()); 
        System.out.println(player.getInventoryString()); 
    }
    
    public void setupGame() {
        Room firstRoom = new Room("first name", "first room short", "first room long"); 
        Room secondRoom = new Room("second name", "second room short", "second room long");
        Room thirdRoom = new Room("third name", "third room short", "third room long");
        
        Item itemExample = new Item("name of item", "long description"); 
        Item itemExample2 = new Item("name of item", "long description");
        Item itemExample3 = new Item("name of item", "long description");
        
        itemExample.setDamage(33);
        
        currentRoom = firstRoom; 
        
        firstRoom.setExit("second", secondRoom); 
        firstRoom.setExit("third room", thirdRoom); 
        secondRoom.setExit("first room", firstRoom); 
        secondRoom.setExit("third room", thirdRoom);
        thirdRoom.setExit("second", secondRoom);
        
        firstRoom.setItem("example", itemExample); 
        firstRoom.setItem("example2", itemExample2);
        secondRoom.setItem("example3", itemExample3);
        
        
        roomList = new ArrayList<>();
        roomList.add(firstRoom); 
        roomList.add(secondRoom); 
        roomList.add(thirdRoom); 
        
        firstRoom.setNPC(npc); 
        /*
         * add the clear screen below during game setup. 
         */      
               
        try {
            cls_var.main(); 
        }catch(Exception e) {
            System.out.println(e); 
        }
        
        printInformation();
         
        play(); 

    }
    
    public void play() {
        while(true) {            
            Command command = parser.getCommand(); 
            try {
                cls_var.main(); 
            }catch(Exception e) {
                System.out.println(e); 
            }
            processCommand(command);
            printInformation();   
        }
    }
    
    public void processCommand(Command command) {
        String commandWord = command.getCommandWord().toLowerCase(); 
        
        switch(commandWord) {
            case "speak":
                System.out.println("you wanted me to speak this word, " + command.getSecondWord()); 
                break; 
            case "go":
                goRoom(command); 
                break; 
            case "grab":
                grab(command); 
                break; 
            case "drop":
                drop(command); 
                break;
            case "look":
                look(command); 
                break; 
                case "eat":
                    eat(command);
                    break; 
        }
    }
    
    public void eat(Command command) {
        String thingToEat = null; 
        if(!command.hasLine()) {
            thingToEat = command.getSecondWord();
        }
        else if(command.hasLine()) {
            thingToEat = command.getSecondWord()+command.getLine();
        } 
        
        Item eatMe = currentRoom.getItem(thingToEat);
        player.adjustHealth(eatMe.getDamage());
    }
    
    public void look(Command command) {
        String printString = "Looking at "; 
        String thingToLook = null;
        if(!command.hasSecondWord()) {
            System.out.println("look at what?");
            return; 
        }
        // we need more if structure to see if there is more than the second word
        if(!command.hasLine()) {
            thingToLook = command.getSecondWord();
        }
        else if(command.hasLine()) {
            thingToLook = command.getSecondWord()+command.getLine();
        } 
                
        if(thingToLook.equals(currentRoom.getName())) {
            printString += "the room: " + currentRoom.getName() + "\n" + currentRoom.getLongDescription(); 
        }
        else if (currentRoom.getItem(thingToLook) != null) {
            printString += "the item: " + currentRoom.getItem(thingToLook).getName() + "\n" + currentRoom.getItem(thingToLook).getDescription();       
        }
        else if (player.getItem(thingToLook) != null) {
            printString += "the item: " + player.getItem(thingToLook).getName() + "\n" + player.getItem(thingToLook).getDescription();
        }
        else {
            printString += "\nYou can't look at that"; 
        }
        
        System.out.println(printString); 
    }
    
        public void drop(Command command) {
            String item = null;
        if(!command.hasSecondWord()) {
            System.out.println("drop what?");
            return; 
        }
        if(!command.hasLine()) {
             item = command.getSecondWord(); 
        }
        else if(command.hasLine()) {
             item = command.getSecondWord()+command.getLine();
        }
        Item itemToGrab = player.removeItem(item);
        
        if(itemToGrab == null) {
            System.out.println("you can't drop that");
            return; 
        }
        else {
            currentRoom.setItem(item, itemToGrab); 
        }
    }
    
    public void grab(Command command) {
        String item = null; 
        if(!command.hasSecondWord()) {
            System.out.println("grab what?"); 
        }
        if(!command.hasLine()) {
             item = command.getSecondWord(); 
        }
        else if(command.hasLine()) {
             item = command.getSecondWord()+command.getLine();
        }
         
        Item itemToGrab = currentRoom.removeItem(item);
        
        if(itemToGrab == null) {
            System.out.println("you can't grab that"); 
        }
        else {
            player.setItem(item, itemToGrab); 
        }
    }
    Room oldRoom = currentRoom; 
    Room nextNPCRoom = currentRoom; 
    public void goRoom(Command command) {
        String direction = ""; 
        if(!command.hasSecondWord()) {
            System.out.println("go where?"); 
        }
        if(!command.hasLine()) {
            direction = command.getSecondWord(); 
        }
        else if(command.hasLine()) {
            direction = command.getSecondWord()+command.getLine();
        }
        
        Room nextRoom = currentRoom.getExit(direction);
        if(nextRoom == null) {
            System.out.println("you can't go there"); 
        }
        else {
            oldRoom = nextNPCRoom;  
            nextNPCRoom = roomList.get(rand.nextInt(3));
            // 
            nextNPCRoom.setNPC(oldRoom.getNPC(0)); 
            currentRoom = nextRoom; 
        }
    }
}