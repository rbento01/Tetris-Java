/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ipt.poo.proj.blocks;

import edu.ipt.poo.proj.libs.Peca;
import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * @author bento
 */
public class BlocoVazio extends Peca{
    
    /**
     * Cria o tabuleiro cheio de blocos vazios
     * 
     * @param gr: contexto gráfico
     * @param x: coordenada X do canto superior esquerdo
     * @param y: coordenada Y do canto superior esquerdo
     * @param dx: largura dos blocos
     * @param dy: altura dos blocos
     */
    public void draw(Graphics gr, int x, int y, int dx, int dy){
        //cor da border dos blocos vazios
        gr.setColor(Color.WHITE);
        gr.drawRect(x, y, dx, dy);
    }
}
