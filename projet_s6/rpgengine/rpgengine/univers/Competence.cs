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

public class Competence
{
    private static int nextId = 0;
    private string nom;

    public string Nom
    {
        get { return nom; }
        set { nom = value; }
    }

    private string description;

    public string Description
    {
        get { return description; }
        set { description = value; }
    }

    private int id;

    public int Id
    {
        get { return id; }
        set { id = value; }
    }


    private int inUsePoints;

    public int InUsePoints
    {
        get { return inUsePoints; }
        set { inUsePoints = value; }
    }

    public Competence()
    {
        this.id = nextId;
        nextId++;
    }

    
}

