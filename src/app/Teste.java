package app;

import lib.ArvoreBinariaExemplo;
import  app.Aluno;
import lib.IArvoreBinaria;
import app.ComparadorAlunoPorNome;


public class Teste {
    public static void main(String[] args){
        ArvoreBinariaExemplo arvore = new ArvoreBinariaExemplo(new ComparadorAlunoPorMatricula());

        arvore.adicionar(new Aluno(001,"Maya"));
        arvore.adicionar(new Aluno(002,"Matheus"));
        arvore.adicionar(new Aluno(003,"Mariana"));
        arvore.adicionar(new Aluno(004,"Marina"));

        System.out.println(arvore.pesquisar(new Aluno(003,"")));

        System.out.println(arvore.pesquisar(new Aluno(001,"Marina"),new ComparadorAlunoPorNome()));
    }
}
