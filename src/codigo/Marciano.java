/*MARCIANITOS DEL JUEGO*/
package codigo;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Pablo Serrano Manzarbeitia
 */
public class Marciano {
    
    public Image imagen1, imagen2 = null;
    public int x = 0;
    public int y = 0;
    
    private int vX = 1; //Aceleración

    /*Con esta podemos cambiar la variable en otras clases*/
    public void setvX(int vX) {
        this.vX = vX;
    }
    
    /*Con esta podemos leer la variable cambiada con set*/
    public int getvX() {
        return vX;
    }
    
    public Marciano() {
        
         try {
         imagen1 = ImageIO.read(getClass().getResource("/imagenes/marcianito1.png"));
         imagen2 = ImageIO.read(getClass().getResource("/imagenes/marcianito2.png"));
         } catch (IOException ex) {

         }
         
    }
    
    public void mueve() {
        
        x += vX;
        
    }
    
    
    
    
}


