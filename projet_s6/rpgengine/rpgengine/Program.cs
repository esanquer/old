using rpgengine.vue;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using rpgengine.moteur;
namespace rpgengine
{
    class Program
    {
        
        static void Main(string[] args)
        {
            Moteur moteur = new Moteur();
            moteur.play(null);
        }
    }
}
