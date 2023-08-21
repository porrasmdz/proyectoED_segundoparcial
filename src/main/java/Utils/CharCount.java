/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author ANDRES PORRAS
 */
public class CharCount {
    public final SimpleStringProperty character;
    public final SimpleIntegerProperty count;

    public CharCount(String character, int count) {
        this.character = new SimpleStringProperty(character);
        this.count = new SimpleIntegerProperty(count);
    }

    public String getCharacter() { return character.get(); }
    public void setCharacter(String character) { this.character.set(character); }
    public int getCount() { return count.get(); }
    public void setCount(int count) { this.count.set(count); }
}
