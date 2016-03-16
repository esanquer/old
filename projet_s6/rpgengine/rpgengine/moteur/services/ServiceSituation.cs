using rpgengine.univers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.moteur.services
{
    class ServiceSituation : ServicesRPG
    {


        public static Situation CreerSituation(Dictionary<string, string> datas)
        {
            Situation s = null;

            switch (datas["situation.type"])
            {
                case "vide":
                    s = new SituationVide();
                    break;
                case "epreuve":
                    s = CreerEpreuve(datas);
                    break;
                case "combat":
                    s = CreerCombat(datas);
                    break;
                case "dialogue":
                    s = new Dialogue(datas["dialogue.message"]);
                    break;
            }
            s.IdSituationRequises = creerListeSituationsRequises(datas);
            s.Nom = datas["situation.nom"];
            s.Description = datas["situation.description"];
            s.Position_x = int.Parse(datas["situation.position_x"]);
            s.Position_y = int.Parse(datas["situation.position_y"]);
            return s;
        }

        private static List<int> creerListeSituationsRequises(Dictionary<string, string> datas)
        {
            List<int> situationsRequises = new List<int>();
            if (datas.ContainsKey("situation.situationsrequises"))
            {
                string idRequises = datas["situation.situationsrequises"];
                if (idRequises != null)
                {
                    string[] ids = idRequises.Split('.');
                    foreach (string id in ids)
                    {
                        situationsRequises.Add(int.Parse(id));
                    }
                }
            }
            return situationsRequises;
        }

        private static Epreuve CreerEpreuve(Dictionary<string, string> datas)
        {
            Epreuve e = new Epreuve();
            Choix c = new Choix("Vaincre");
            e.Difficulte = int.Parse(datas["epreuve.difficulte"]);
            c.Difficulte = e.Difficulte;
            c.CompetencesRequises = ConstruireCompetencesRequises(datas, "epreuve.competencesrequises");
            c.CaracteristiquesImpliquees = ConstruireCaracteristiquesImpliquees(datas);
            e.ChoixPossibles.Add(c);
            return e;
        }


        public static List<Caracteristique> ConstruireCaracteristiquesImpliquees(Dictionary<string, string> datas)
        {
            List<Caracteristique> c = new List<Caracteristique>();
            string idCaracteristiquesImpliquees = datas["epreuve.caracteristiquesimpliquees"];
            if (idCaracteristiquesImpliquees != null)
            {

                string[] tabIds = idCaracteristiquesImpliquees.Split('.');
                foreach (string id in tabIds)
                {
                    c.Add(ServiceCaracteristique.findById(univers, int.Parse(id)));
                }
            }
            return c;
        }


        public static List<Competence> ConstruireCompetencesRequises(Dictionary<string, string> datas,string property)
        {
            List<Competence> c = new List<Competence>();
            string idCompetencesRequises = datas[property];
            if (idCompetencesRequises != null)
            {
                string[] tabIds = idCompetencesRequises.Split('.');
                foreach (string id in tabIds)
                {
                    c.Add(ServiceCompetence.findById(univers, int.Parse(id)));
                }
            }
            return c;
        }


        public static Combat CreerCombat(Dictionary<string, string> datas)
        {
            Combat c = new Combat();
            Categorie pnj = new Categorie();
            string nomPnj = datas["combat.categorie.nomCategorie"];
            string descriptionPnj = datas["combat.categorie.description"];
            IEnumerable<KeyValuePair<string, string>> cat = datas.Where(i => i.Key.StartsWith("combat.caracteristique"));
            foreach (KeyValuePair<string, string> kvp in cat)
            {
                int caracteristicId = int.Parse(kvp.Key.Split('.')[2]);
                InstanceCaracteristique instanceCaracteristique = new InstanceCaracteristique();
                instanceCaracteristique.Caracteristique = ServiceCaracteristique.findById(univers, caracteristicId);
                instanceCaracteristique.Niveau = kvp.Value;
                pnj.InstancesCaracteristiques.Add(instanceCaracteristique);
            }
            pnj.PointsDeVie = 100;
            pnj.NomCategorie = nomPnj;
            pnj.Description = descriptionPnj;
            pnj.Competences = ConstruireCompetencesRequises(datas,"personnage.competences");
            c.Pnj = pnj;

            c.CompetencesPossibles = ConstruireCompetencesRequises(datas, "combat.competences");
            
            return c;
        }



        public static Situation findById(int id, Univers u)
        {
            Situation s = null;
            bool trouve = false;
            int i = 0;
            while (!trouve && i < u.Situations.Count)
            {
                if (u.Situations[i].Id == id)
                {
                    trouve = true;
                    s = u.Situations[i];
                }
                i++;
            }
            return s;
        }


        public static bool estAccessible(Situation s, Univers u)
        {
            bool accessible = true;
            foreach (int id in s.IdSituationRequises)
            {
                accessible = accessible && (findById(id,u)).Terminee;
            }
            return accessible;
        }



        public static List<Choix> ConstruireListeChoixPourCombat(Combat c, Personnage p)
        {
            List<Choix> choix = new List<Choix>();
            foreach (Competence comp in c.CompetencesPossibles)
            {
                if (p.aCompetence(comp))
                {
                    Competence competencePerso = p.getCompetenceById(comp);
                    Choix newChoix = new Choix(competencePerso.Nom);
                    newChoix.CompetencesRequises.Add(competencePerso);
                    choix.Add(newChoix);
                }
            }
            return choix;
        }
    }
}
