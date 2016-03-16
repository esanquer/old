using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.moteur.services
{
    class ServiceCompetence : ServicesRPG
    {

        public static Competence CreerCompetence(Dictionary<string, string> datas)
        {
            Competence nouvelleCompetence = new Competence();
            nouvelleCompetence.Description = datas["competence.description"];
            nouvelleCompetence.Nom = datas["competence.nom"];
            nouvelleCompetence.InUsePoints = int.Parse(datas["competences.points"]);
            return nouvelleCompetence;
        }
        public static void ModifierCompetence(Dictionary<string, string> datas, Competence c)
        {
            c.Description = datas["competence.description"];
            c.Nom = datas["competence.nom"];
            c.InUsePoints = int.Parse(datas["competences.points"]);
        }

        public static Competence findById(Univers u, int id)
        {
            Competence c = null;
            bool trouve = false;
            int i = 0;
            while (!trouve && i < u.Competences.Count)
            {
                if (u.Competences[i].Id == id)
                {
                    trouve = true;
                    c = u.Competences[i];
                }
                i++;
            }
            return c;
        }
    }
}
