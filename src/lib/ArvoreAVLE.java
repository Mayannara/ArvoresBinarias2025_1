package lib;

import java.util.Comparator;

public class ArvoreAVLE<T> extends ArvoreBinaria<T> {
    public ArvoreAVLE(Comparator<T> comparator) {
        super(comparator);
    }

    //Implementar métodos para efetuar o balanceamento e sobrescrever método de adicionar elemento...

    @Override
    public void adicionar (T valor) {

    }

    public No<T> remover(T valor)  {

    }


    private No<T> rotacaoEsquerda(No<T> no) {
        No<T> f = no.getFilhoDireita();
        no.setFilhoDireita(f.getFilhoEsquerda());
        f.setFilhoEsquerda(no);

        return f;
    }

    private No<T> rotacaoDireita(No<T> r) {
        No<T> f = r.getFilhoEsquerda();
        r.setFilhoEsquerda(f.getFilhoDireita());
        f.setFilhoDireita(r);

        return f;
    }

    private No<T> rotacaoDireitaEsquerda(No<T> r) {
        r.setFilhoDireita(rotacaoDireita(r.getFilhoDireita()));
        return rotacaoEsquerda(r);
    }

    private No<T> rotacaoEsquerdaDireita(No<T> r) {
        r.setFilhoEsquerda(rotacaoEsquerda(r.getFilhoEsquerda()));
        return rotacaoDireita(r);
    }
    

}
