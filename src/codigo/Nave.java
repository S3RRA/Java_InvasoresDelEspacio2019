/*LA NAVE ESPACIAL DEL JUEGO*/
package codigo;

import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/*
 * @author Pablo Serrano Manzarbeitia
 */

public class Nave {
    //Invocamos la imagen e indicamos su posicion
    public Image imagen = null;
    public int x = 0;
    public int y = 0;
    private boolean pulsadoIzquierda = false;
    private boolean pulsadoDerecha = false;
   
    /*Esto lo que hace es iniciar el public Image, con la imagen seleccionada*/
    public Nave() {
        try {
            imagen = ImageIO.read(getClass().getResource("/imagenes/nave.png"));
                    } catch (IOException ex) {
        }
    }
    
    /*Ordenes de movimiento para la nave*/
    public void mueve() {
        if(pulsadoIzquierda && x > 0) {
            x = x - 10;
        }
        if(pulsadoDerecha && x < VentanaJuego.ANCHOPANTALLA - imagen.getWidth(null)) {
            x = x + 10;
        }
    }

    public boolean isPulsadoIzquierda() {
        return pulsadoIzquierda;
    }

    public void setPulsadoIzquierda(boolean pulsadoIzquierda) {
        //this. es la que esta en la nave, la comparamos con un buleano nuevo con el q operar
        this.pulsadoIzquierda = pulsadoIzquierda;
        this.pulsadoDerecha = false;
    }

    public boolean isPulsadoDerecha() {
        return pulsadoDerecha;
    }

    public void setPulsadoDerecha(boolean pulsadoDerecha) {
        this.pulsadoDerecha = pulsadoDerecha;
        this.pulsadoIzquierda = false;
    }
    
}
