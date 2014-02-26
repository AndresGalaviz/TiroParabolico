/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tiroparabolico;

/**
 *
 * @author Alberto
 */
public class Canasta extends Base {
    public Canasta(int posX, int posY) {
        super(posX, posY, crearAnimacionCanasta());
    }
    
    private static Animacion crearAnimacionCanasta() {
        Animacion anim = new Animacion();
        return anim;
    }
}
