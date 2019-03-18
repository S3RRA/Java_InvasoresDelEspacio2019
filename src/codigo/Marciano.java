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
    public int vX = 1; //Aceleración
    
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


