package com.jacksonbarreto.classes;

import org.jetbrains.annotations.NotNull;

public abstract class Helpers {


    public static String normalizeMatricula(@NotNull String matricula){
        matricula = sanitizeString(matricula);
        matricula = matricula.replaceAll("^ +| +$|( )+","");
        matricula = matricula.toUpperCase();
        StringBuilder matriculaBuilder = new StringBuilder();
        if (matricula.length()==6){
            for (int i=0, j=0; i<8; i++){
                if (i==2 || i== 5)
                    matriculaBuilder.append("-");
                else{
                    matriculaBuilder.append(matricula.charAt(j));
                    j++;
                }
            }
            matricula = matriculaBuilder.toString();
        }
        return matricula;
    }

    /**
     * Este método limpa a String, removendo caracteres que não sejam letras, números e hífem.
     * Remove ainda os espaços em excesso, inclusive das extremidades.
     * @param text Texto a ser limpo
     * @return String sanitizada
     */
    public static String sanitizeString(@NotNull String text){
        char c;

        for (int i=0; i<text.length(); i++){
            c = text.charAt(i);
            if(! Character.isDigit(c) && ! Character.isAlphabetic(c) && c != '-' )
                text = text.substring(0,i) + ' ' + text.substring(i+1);
        }
        return text.replaceAll("^ +| +$|( )+","$1");
    }

    /**
     * Este método normaliza a nome, deixando apenas as primeiras letras de cada palavra em maiúcula.
     * @param nome nome a ser normalizada
     * @return nome com as primeiras letras de cada palavra em maiúscula.
     */
    public static String normalizeNome(@NotNull String nome){
        nome = nome.toLowerCase();
        String [] partes = nome.split(" ");
        StringBuilder nomeBuilder = new StringBuilder();
        for (String s : partes ){
            if(s.length() > 2)
                nomeBuilder.append(String.format("%c%s ", Character.toUpperCase(s.charAt(0)), s.substring(1)));
            else
                nomeBuilder.append(String.format("%s ",s));
        }
        nome = nomeBuilder.toString();

        return nome.replaceAll("^ +| +$|( )+","$1");
    }

    public static boolean matriculaIsInvalid(String matricula){
        if (matricula.isEmpty())
            return true;
        matricula = Helpers.sanitizeString(matricula);
        matricula = matricula.replaceAll("^ +| +$|( )+","");
        matricula = matricula.toUpperCase();

        if (matricula.matches("[A-Z0-9]{2}-{1}[A-Z0-9]{2}-{1}[A-Z0-9]{2}") || matricula.matches("[A-Z0-9]{6}"))
            return false;
        return true;
    }
}
