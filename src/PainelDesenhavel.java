import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PainelDesenhavel extends JPanel {
    private int tamanho;
    private Color minhaCor = Color.BLACK;
    private final BufferedImage imagemPintada;
    private final Graphics g;

    public PainelDesenhavel(int x, int y){
        this.tamanho = 30;
        setBackground(Color.WHITE);
        imagemPintada = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
        g = imagemPintada.createGraphics();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, x, y);
    }

    public void desenhar(int x, int y) {
        g.setColor(minhaCor);
        g.fillOval(x, y, this.tamanho, this.tamanho);

        Graphics gg = getGraphics();
        gg.setColor(minhaCor);
        gg.fillOval(x, y, this.tamanho, this.tamanho);
    }

    public void setCor(Color novaCor){
        this.minhaCor = novaCor;
    }

    public int getTamanho(){
        return this.tamanho;
    }

    public void setTamanho(int novoTamanho){
        this.tamanho = novoTamanho;
    }

    public void salvarPainel(JFileChooser fileChooser) {
        try{
            ImageIO.write(imagemPintada, "JPG", fileChooser.getSelectedFile());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
