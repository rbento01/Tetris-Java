/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ipt.poo.proj.libs;

import edu.ipt.poo.proj.blocks.BlocoVazio;
import java.awt.Color;

/**
 *
 * @author bento
 */
public class PecaJ extends TetrisPeca{
    
    /**
     *  cria-se um array de Peca querendo dizer que vão ter uma cor e que vão ser pintadas no tabuleiro
     */
    static Peca[][] T = {
        {new BlocoVazio(), new Peca(Color.PINK)},
        {new BlocoVazio(), new Peca(Color.PINK)},
        {new Peca(Color.PINK), new Peca(Color.PINK)}    
    };
    
    /**
     *  envia-se o array para o construtor da classe TetrisPeca que transforma o array na var matriz dessa mesma classe
     */
    public PecaJ(){ 
        super(T);
    }
}
