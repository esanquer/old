using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace rpgengine.moteur.services
{
    class ServicePartie
    {

        public static void SauverPartie(Partie p)
        {
            if (!Directory.Exists(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_SAUVEGARDES))
                Directory.CreateDirectory(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_SAUVEGARDES);

            XmlSerializer xs = new XmlSerializer(typeof(Partie));
            using (StreamWriter wr = new StreamWriter(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_SAUVEGARDES + p.NomPartie + ".xml"))
            {
                xs.Serialize(wr, p);
            }
        }


        public static Partie ChargerPartie(string nom)
        {
            XmlSerializer xs = new XmlSerializer(typeof(Partie));
            Partie p = null;
            using (StreamReader rd = new StreamReader(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_SAUVEGARDES + nom + ".xml"))
            {
                p = xs.Deserialize(rd) as Partie;
            }
            return p;
        }



        public static List<string> listerParties()
        {
            String[] files = Directory.GetFiles(AppDomain.CurrentDomain.BaseDirectory + Constantes.DOSSIER_SAUVEGARDES);
            List<string> parties = new List<string>();
            foreach (string f in files)
            {
                if (f.EndsWith(".xml") || f.EndsWith(".XML"))
                {
                    string s = f.Remove(f.Length - 4);
                    string[] elements = s.Split('\\');
                    parties.Add(elements[elements.Length - 1]);
                }
            }
            return parties;
        }
    }
}
