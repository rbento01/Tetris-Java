/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ipt.poo.proj.libs;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/**
 * 
 * @author bento
 */
public class Peca extends JComponent{
    //cor que a peça vai ter
    Color myColor;
    
    public Peca(){
       
    }

    /**
     * construtor em que se determina com que cor fica a peça
     * @param c: cor escolhida para ser a cor da peça
     */
    public Peca(Color c){
      this.myColor = c;
    }

    /**
     * Desenha as peças
     * 
     * @param gr: contexto gráfico
     * @param x: coordenada X do canto superior esquerdo
     * @param y: coordenada Y do canto superior esquerdo
     * @param dx: largura dos blocos
     * @param dy: altura dos blocos
     */
    public void draw(Graphics gr, int x, int y, int dx, int dy) {
        //cor do bloco
        gr.setColor(myColor);
        //pinta o quadrado com a cor
        gr.fillRect(x, y, dx, dy);
        //seleciona a cor da border que os blocos vai ter
        gr.setColor(Color.GREEN);
        //pinta as borders de cada quadrado
        gr.drawRect(x, y, dx, dy);
    }
}
