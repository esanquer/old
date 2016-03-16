using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using rpgengine.vue;
using rpgengine.moteur.services;
namespace rpgengine.moteur
{
    class Moteur
    {
        
        protected static Univers univers;
        protected static VueConsole vue;
        protected static ConsoleKeyListener consoleKeyListner;
        protected static Stack<List<Widget>> historique = new Stack<List<Widget>>();
        
        public Moteur()
        {
            consoleKeyListner = new ConsoleKeyListener(this.getVue);
            consoleKeyListner.start();
        }

       

        public void quitter(List<Object> parameters) {
        }

        public void AllerAGestionUnivers(List<Object> parameters)
        {
            GestionUnivers gestionUnivers = new GestionUnivers();
            gestionUnivers.AfficherGestionUnivers(parameters);
        }

        public void AllerAJouer(List<Object> parameters)
        {
            JeuPartie jeu = new JeuPartie();
            jeu.AfficherMenuAccueil(parameters);
        }

        public void play(List<Object> parameters)
        {
            vue = new VueConsole();
            MenuWidget menuPrincipal = new MenuWidget(45,15,24,4);
            menuPrincipal.addOption("Jouer", this.AllerAJouer, null);
            menuPrincipal.addOption("Gérer univers", this.AllerAGestionUnivers, null);
            menuPrincipal.addOption("Quitter", quitter,null);
            vue.addWidget(menuPrincipal);
            vue.setReady();
            vue.draw();
        }


        public Object getVue()
        {
            return vue;
        }
    }
}
