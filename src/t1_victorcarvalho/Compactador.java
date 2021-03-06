/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t1_victorcarvalho;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 *
 * @author victor lopes de carvalho
 */
public class Compactador {

    public static void main(String[] args) {
        ListaEncadeada lst = new ListaEncadeada(); //declara o compactador 
        String[] palavrasDaLinha; //lista de palavras
        String textoCompactado = ""; //texto final

        int indiceDaPalavra;
        try {
            List<String> textoCompleto = Files.readAllLines(Paths.get("compactar.txt")); //lê o arquivo inteiro e seta na list textoCompleto
            textoCompactado = String.join("\n", textoCompleto); //texto final já copiado

            BufferedWriter writer = new BufferedWriter(new FileWriter("saida.txt")); //gera o arquivo saida e declara o writer

            for (String linha : textoCompleto) { //anda pelo texto completo
                if (linha.equals("0")) {//condição de parada da leitura
                    break;
                }

                palavrasDaLinha = linha.split("\\W+"); //splita as palavras da linha atual

                for (String palavra : palavrasDaLinha) { //anda na linha atual
                    indiceDaPalavra = lst.buscaIndex(palavra);//verifica se a palavra está na lista encadeada e retorna a posição
                    if (indiceDaPalavra == -1) {//se for igual a -1, ele adiciona a palavra na lista encadeada
                        if (palavra.equals("")) {//.split(\\W+) não identifica o identador, se for "", não faz nada
                        } else {
                            lst.insereInicio(palavra); //adiciona a palavra na lista encadeada
                        }
                    } else {

                        int indiceDaPrimeiraOcorrencia = textoCompactado.indexOf(palavra); //indice da primeira ocorrencia da palavra no texto final
                        String inicioDoTexto = textoCompactado.substring(0, indiceDaPrimeiraOcorrencia + palavra.length());//separa a primeira parte do texto antes da segunda ocorrencia
                        String finalDoTexto = textoCompactado.substring(indiceDaPrimeiraOcorrencia + palavra.length());//separa a segunda a parte do texto junto da segunda ocorrencia

                        textoCompactado = inicioDoTexto + finalDoTexto.replaceFirst(palavra, Integer.toString(indiceDaPalavra));
                        lst.remove(palavra);//remove a palavra da lista
                        lst.insereInicio(palavra);//insera a palavra no inicio da lista novamente
                        indiceDaPalavra = 0;//zera o indice da palavra
                    }
                }
            }

            writer.write(textoCompactado);

            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
