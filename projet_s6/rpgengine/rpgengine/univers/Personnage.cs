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

public class Personnage
{
    protected static long nextId = 0;

    protected long id;

    public long Id
    {
        get { return id; }
        set { id = value; }
    }
    private string nom;

    protected string Nom
    {
        get { return nom; }
        set { nom = value; }
    }

    protected int pointsDeVie;
    public int PointsDeVie
    {
        get { return pointsDeVie; }
        set { pointsDeVie = value; }
    }

    protected List<Competence> competences;
    public List<Competence> Competences
    {
        get { return competences; }
        set { competences = value; }
    }


    protected List<InstanceCaracteristique> instancesCaracteristiques;

    public List<InstanceCaracteristique> InstancesCaracteristiques
    {
        get { return instancesCaracteristiques; }
        set { instancesCaracteristiques = value; }
    }

    protected List<Equipement> equipement;

    public List<Equipement> Equipement
    {
        get { return equipement; }
        set { equipement = value; }
    }

    public Personnage()
    {
        this.id = nextId;
        nextId++;

        this.competences = new List<Competence>();
        this.equipement = new List<Equipement>();
        this.instancesCaracteristiques = new List<InstanceCaracteristique>();

    }



    public bool aCompetence(Competence c)
    {
        bool found = false;
        int i = 0;
        while (!found && i < competences.Count)
        {
            found = (c.Id == competences[i].Id);
            i++;
        }
        return found;
    }

    public Competence getCompetenceById(Competence c)
    {
        Competence comp = null;
        if (aCompetence(c))
        {
            bool found = false;
            int i = 0;
            while (!found && i < competences.Count)
            {
                found = (c.Id == competences[i].Id);
                if(found)
                    comp = competences[i];
                i++;
            }
        }
        return comp;
    }

    public int recupererNiveauCaracteristique(Caracteristique c){
        int niveau = 0 ;
        foreach(InstanceCaracteristique i in instancesCaracteristiques)
        {
            if(c.Id == i.Caracteristique.Id)
            {
                niveau = int.Parse( i.Niveau);
                break;
            }
        }
        return niveau;
    }
}

