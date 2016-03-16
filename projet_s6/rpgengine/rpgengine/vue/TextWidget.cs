using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class TextWidget : Widget
    {

        private string text;

        public string Text
        {
            get { return text; }
            set { text = value; }
        }
         public TextWidget(int x, int y, int width, int height, string text)
            : base(x, y, width, height)
        {
            this.text = text;
            this.focusable = false;
             this.border='\\';
        }

        public override void draw()
        {
            base.draw();
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            int nbCols = this.width - 2;
            int nbRows = this.height - 2;
            string[] mots = this.text.Split(' ');
            int index = 0;
            for (int i = 0; i < nbRows; i++)
            {
                Console.SetCursorPosition(this.x + 1, this.y + 1 + i);
                bool finLigne = false;
                string ligne = "";
                while (!finLigne && index < mots.Length)
                {
                    if (ligne.Length + mots[index].Length < nbCols)
                    {
                        ligne += mots[index]+" ";
                        index++;
                    }
                    else
                    {
                        finLigne = true;
                    }
                }
                Console.Write(ligne);
            }

            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }
    }
}
