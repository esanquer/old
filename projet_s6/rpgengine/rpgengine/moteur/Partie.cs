using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.moteur
{
    public class Partie
    {
        private Categorie persoJoueur;

        public Categorie PersoJoueur
        {
            get { return persoJoueur; }
            set { persoJoueur = value; }
        }

        private Univers universJeu;

        public Univers UniversJeu
        {
            get { return universJeu; }
            set { universJeu = value; }
        }

        private string nomPartie;

        public string NomPartie
        {
            get { return nomPartie; }
            set { nomPartie = value; }
        }


        private int colonneSituationCourrante;

        public int ColonneSituationCourrante
        {
            get { return colonneSituationCourrante; }
            set { colonneSituationCourrante = value; }
        }
        private int ligneSituationCourrante;

        public int LigneSituationCourrante
        {
            get { return ligneSituationCourrante; }
            set { ligneSituationCourrante = value; }
        }

        private int positionDansSituation_x;

        public int PositionDansSituation_x
        {
            get { return positionDansSituation_x; }
            set { positionDansSituation_x = value; }
        }
        private int positionDansSituation_y;

        public int PositionDansSituation_y
        {
            get { return positionDansSituation_y; }
            set { positionDansSituation_y = value; }
        }

    }
}
