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

public class Equipement
{
    private static long nextId = 0;

    private long id;

    public long Id
    {
        get { return id; }
        set { id = value; }
    }
	protected virtual object nom
	{
		get;
		set;
	}

}

