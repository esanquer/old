using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.moteur.services
{
    class ServiceCaracteristique
    {
        public static Caracteristique CreerCaracteristique(Dictionary<string, string> datas)
        {
            Caracteristique nouvelleCaracteristique = new Caracteristique();
            nouvelleCaracteristique.Description = datas["caracteristique.description"];
            nouvelleCaracteristique.Nom = datas["caracteristique.nom"];
            return nouvelleCaracteristique;
        }
        public static void ModifierCaracteristique(Dictionary<string, string> datas, Caracteristique c)
        {
            c.Description = datas["caracteristique.description"];
            c.Nom = datas["caracteristique.nom"];

        }

        public static Caracteristique findById(Univers u, int id)
        {
            Caracteristique c = null;
            bool trouve = false;
            int i = 0;
            while(!trouve && i < u.Caracteristiques.Count)
            {
                if (u.Caracteristiques[i].Id == id)
                {
                    trouve = true;
                    c = u.Caracteristiques[i];
                }
                i++;
            }
            return c;
        }
    }
}
