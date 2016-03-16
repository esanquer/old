using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using rpgengine.vue;
using System.Xml.Serialization;
using System.IO;
namespace rpgengine.moteur.services
{
    class ServiceUnivers : ServicesRPG
    {

        public static Univers CreerUnivers(Dictionary<string, string> datas)
        {
            Univers nouveauUnivers = new Univers();
            nouveauUnivers.Nom = datas["univers.nom"];
            nouveauUnivers.Description = datas["univers.description"];
            return nouveauUnivers;
        }



        public static void SauverUnivers(Univers u)
        {
            if (!Directory.Exists(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_UNIVERS))
                Directory.CreateDirectory(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_UNIVERS);


            XmlSerializer xs = new XmlSerializer(typeof(Univers));

            using (StreamWriter wr = new StreamWriter(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_UNIVERS + u.Nom + ".xml"))
            {
                xs.Serialize(wr, u);
            }
        }

        public static List<string> listerUnivers()
        {
            String[] files = Directory.GetFiles(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_UNIVERS);
            List<string> univers = new List<string>();
            foreach (string f in files)
            {
                if(f.EndsWith(".xml") || f.EndsWith(".XML")){
                    string s = f.Remove(f.Length - 4);
                    string[] elements = s.Split('\\');
                    univers.Add(elements[elements.Length-1]);
                }
            }
            return univers;
        }

        public static Univers ChargerUnivers(string nom)
        {
            XmlSerializer xs = new XmlSerializer(typeof(Univers));
            Univers u = null;
            using (StreamReader rd = new StreamReader(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_UNIVERS + nom + ".xml"))
            {
               u = xs.Deserialize(rd) as Univers;
            }
            return u;
        }
    }
}
