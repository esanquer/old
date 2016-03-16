using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.moteur.services
{
    class ServiceCategorie : ServicesRPG
    {

        public static Categorie CreerCategorie(Dictionary<string, string> datas, Univers u)
        {
            Categorie newCat = new Categorie();
            newCat.NomCategorie = datas["categorie.nomCategorie"];
            newCat.Description = datas["categorie.description"];

            IEnumerable<KeyValuePair<string, string>> cat = datas.Where(i => i.Key.StartsWith("caracteristique"));

            foreach (KeyValuePair<string, string> kvp in cat)
            {
                int caracteristicId =int.Parse( kvp.Key.Split('.')[1]);
                InstanceCaracteristique instanceCaracteristique = new InstanceCaracteristique();
                instanceCaracteristique.Caracteristique = ServiceCaracteristique.findById(u,caracteristicId);
                instanceCaracteristique.Niveau = kvp.Value;
                newCat.InstancesCaracteristiques.Add(instanceCaracteristique);
            }
            newCat.PointsDeVie = 100;

            List<Competence> c = new List<Competence>();
            string idCompetencesRequises = datas["categorie.competences"];
            if (idCompetencesRequises != null)
            {
                string[] tabIds = idCompetencesRequises.Split('.');
                foreach (string id in tabIds)
                {
                    c.Add(ServiceCompetence.findById(univers, int.Parse(id)));
                }
            }
            newCat.Competences = c;

            


            return newCat;
        }

        public static void ModifierCategorie(Dictionary<string, string> datas,Univers u, Categorie c)
        {
            c.NomCategorie = datas["categorie.nomCategorie"];
            c.Description = datas["categorie.description"];
            c.InstancesCaracteristiques = new List<InstanceCaracteristique>();
            IEnumerable<KeyValuePair<string, string>> cat = datas.Where(i => i.Key.StartsWith("caracteristique"));

            foreach (KeyValuePair<string, string> kvp in cat)
            {
                int caracteristicId = int.Parse(kvp.Key.Split('.')[1]);
                InstanceCaracteristique instanceCaracteristique = new InstanceCaracteristique();
                instanceCaracteristique.Caracteristique = ServiceCaracteristique.findById(u, caracteristicId);
                instanceCaracteristique.Niveau = kvp.Value;
                c.InstancesCaracteristiques.Add(instanceCaracteristique);
            }

            List<Competence> comp = new List<Competence>();
            string idCompetencesRequises = datas["categorie.competences"];
            if (idCompetencesRequises != null)
            {
                string[] tabIds = idCompetencesRequises.Split('.');
                foreach (string id in tabIds)
                {
                    comp.Add(ServiceCompetence.findById(univers, int.Parse(id)));
                }
            }
            c.Competences = comp;
        }

        public static Categorie findById(Univers u, long id)
        {
            Categorie c = null;
            bool trouve = false;
            int i = 0;
            while (!trouve && i < u.Categories.Count)
            {
                if (u.Categories[i].Id == id)
                {
                    trouve = true;
                    c = u.Categories[i];
                }
                i++;
            }
            return c;
        }
    }
}
