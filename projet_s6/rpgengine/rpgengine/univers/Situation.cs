﻿using rpgengine.univers;
//------------------------------------------------------------------------------
// <auto-generated>
//     Ce code a été généré par un outil
//     Les modifications apportées à ce fichier seront perdues si le code est régénéré.
// </auto-generated>
//------------------------------------------------------------------------------
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;


[XmlInclude(typeof(SituationVide))]
[XmlInclude(typeof(Dialogue))]
[XmlInclude(typeof(Epreuve))]
[XmlInclude(typeof(Combat))]

public class Situation
{
    private static int nextId = 0;
    protected int id;

    public int Id
    {
        get { return id; }
        set { id = value; }
    }

    protected string nom;

    public string Nom
    {
        get { return nom; }
        set { nom = value; }
    }
    protected string description;

    public string Description
    {
        get { return description; }
        set { description = value; }
    }

    protected int position_x;

    public int Position_x
    {
        get { return position_x; }
        set { position_x = value; }
    }

    protected int position_y;

    public int Position_y
    {
        get { return position_y; }
        set { position_y = value; }
    }

    private int difficulte;

    public int Difficulte
    {
        get { return difficulte; }
        set { difficulte = value; }
    }

    private List<Choix> choixPossibles;

    public List<Choix> ChoixPossibles
    {
        get { return choixPossibles; }
        set { choixPossibles = value; }
    }
    private bool terminee;

    public bool Terminee
    {
        get { return terminee; }
        set { terminee = value; }
    }

    private List<int> idSituationRequises;

    public List<int> IdSituationRequises
    {
        get { return idSituationRequises; }
        set { idSituationRequises = value; }
    }

    public Situation()
    {
        this.id = nextId;
        nextId++;
        this.choixPossibles = new List<Choix>();
        this.description = "";
        this.difficulte = 0;
        this.idSituationRequises = new List<int>();
    }

    


}

