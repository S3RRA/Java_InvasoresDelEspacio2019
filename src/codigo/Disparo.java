/*DISPARO DE LA NAVE ESPACIAL*/
package codigo;
   
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

 /*
 * @author Pablo Serrano Manzarbeitia
 */
public class Disparo {
    
    public Image imagen = null;
    public int x = 0;
    public int y = 2000;//pintamos el disparo muy por debajo de la pantalla para que no se vea
    /*Creo una variable buleana para saber si el disparo desaparece o no (no ir al infinito)*/
    public boolean disparado = false;


    public Disparo() {
        try {
         imagen = ImageIO.read(getClass().getResource("/imagenes/disparo.png"));
         } catch (IOException ex) {

         }
         
    }
    
    public void mueve() {
        if (disparado){
            y -= 3;
        }
    }
    
    public void posicionamientoDisparo (Nave _nave) {
        x = _nave.x + _nave.imagen.getWidth(null)/2 - imagen.getWidth(null)/2;
        y = _nave.y;
        disparado = true;
    }

}
