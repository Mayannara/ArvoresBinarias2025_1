package app;

import lib.ArvoreBinaria;


public class Teste {
    public static void main(String[] args){
        ArvoreBinaria<Aluno> arvore = new ArvoreBinaria<Aluno>(new ComparadorAlunoPorMatricula());

        arvore.adicionar(new Aluno(100,"Maya"));
        arvore.adicionar(new Aluno(50,"Matheus"));
        arvore.adicionar(new Aluno(150,"Mariana"));
        arvore.adicionar(new Aluno(80,"Marina"));
        arvore.adicionar(new Aluno(30,"Maria"));
        arvore.adicionar(new Aluno(120,"Mariana"));

        System.out.println(arvore.pesquisar(new Aluno(100,"")));

        System.out.println(arvore.pesquisar(new Aluno(150,"Marina"),new ComparadorAlunoPorNome()));

        System.out.println(arvore.altura());
        System.out.println(arvore.quantidadeNos());
        System.out.println(arvore.remover(new Aluno(120,"Marina")));
        System.out.println(arvore.quantidadeNos());
    }
}
