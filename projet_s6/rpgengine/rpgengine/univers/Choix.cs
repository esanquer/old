﻿//------------------------------------------------------------------------------
// <auto-generated>
//     Ce code a été généré par un outil
//     Les modifications apportées à ce fichier seront perdues si le code est régénéré.
// </auto-generated>
//------------------------------------------------------------------------------
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

public class Choix
{

    public static int maxRandValue = 18;
    private static Random rand = new Random();
    private int difficulte;

    public int Difficulte
    {
        get { return difficulte; }
        set { difficulte = value; }
    }

    private List<Caracteristique> caracteristiquesImpliquees; // les caracteristiques impliquees définisse les atous que le personnage doit mettre en avant pour réussir

    public List<Caracteristique> CaracteristiquesImpliquees
    {
        get { return caracteristiquesImpliquees; }
        set { caracteristiquesImpliquees = value; }
    }

    private List<Competence> competencesRequises; // les compétences requises définissent si le joueur peut faire le choix

    public List<Competence> CompetencesRequises
    {
        get { return competencesRequises; }
        set { competencesRequises = value; }
    }

    private string nom;

    public string Nom
    {
        get { return nom; }
        set { nom = value; }
    }



    public Choix() : this("")
    {

    }

    public Choix(string nom)
    {
        this.nom = nom;
        this.competencesRequises = new List<Competence>();
        this.caracteristiquesImpliquees = new List<Caracteristique>();
        this.difficulte = 0;
    }

    

    public bool essaiPossible(Personnage p)
    {
        bool possible = true;
        int i = 0;
        while (possible && i < competencesRequises.Count)
        {
            possible = p.aCompetence(competencesRequises[i]);
            i++;
        }
        return possible;
    }

    

    public int testerChoix(Personnage p)
    {
        int result = 0;
        if(essaiPossible(p)){
            int x = rand.Next(0, maxRandValue);
            int offsetPersonnageCaracteristiques = calculerMoyenneCaracteristiquesImpliqueesPersonnage(p);
            int offsetPersonnageCompetences = calculerMoyenneCompetencesImpliquees();
            int valeurRequise = this.difficulte;
            result = (offsetPersonnageCaracteristiques + offsetPersonnageCompetences + x) - valeurRequise;
        }   
        return result;
    }


    private int calculerMoyenneCaracteristiquesImpliqueesPersonnage(Personnage p)
    {
        int moyenne = 0;
        int somme = 0;
        foreach (Caracteristique c in caracteristiquesImpliquees)
        {
            somme += p.recupererNiveauCaracteristique(c);
        }
        if(caracteristiquesImpliquees.Count>0)
            moyenne = somme / caracteristiquesImpliquees.Count;
        return moyenne;
    }


    private int calculerMoyenneCompetencesImpliquees()
    {
        int somme = 0;
        int moyenne = 0;
        if (competencesRequises != null)
        {
            foreach (Competence c in competencesRequises)
            {
                somme += c.InUsePoints;
            }
            moyenne = somme / competencesRequises.Count;
        }
        return moyenne;
        
    }
}

