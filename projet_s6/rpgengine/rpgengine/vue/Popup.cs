using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class Popup : Widget
    {
        private string title;

        public string Title
        {
            get { return title; }
            set { title = value; }
        }

        private List<ButtonWidget> buttons;

        public List<ButtonWidget> Buttons
        {
            get { return buttons; }
            set { buttons = value; }
        }

        private string message;

        public string Message
        {
            get { return message; }
            set { message = value; }
        }
        private int currentButtonFocused;
        public Popup(int x, int y, int width, int height, string title, string message)
            : base(x, y, width, height)
        {
            this.message = message;
            this.title = title;
            this.buttons = new List<ButtonWidget>();
            this.border = '\\';
        }

        public void drawBackground()
        {
            for (int i = 0; i < this.height; i++)
            {
                String line = new String(' ', this.width);
                Console.SetCursorPosition(this.x, this.y+i);
                Console.Write(line);
            }
        }
        public override void draw()
        {
            base.draw();
            Console.SetCursorPosition(this.x + 1, this.y);
            Console.Write(this.title);
            int nbCols = this.width - 2;
            int nbRows = this.height - 4;
            string[] mots = this.message.Split(' ');
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
                        ligne += mots[index] + " ";
                        index++;
                    }
                    else
                    {
                        finLigne = true;
                    }

                }
                Console.Write(ligne);
            }

            foreach (ButtonWidget b in buttons)
            {
                b.draw();
            }
        }


        public void addButton(ButtonWidget b)
        {
            b.Width = b.Text.Length + 2;
            if (this.buttons.Count > 0)
                b.X = this.buttons.Last().X - b.Width-1;
            else
                b.X = this.x + this.width - b.Width-1;
            b.Y = this.height + this.y - b.Height-1;
            
            this.buttons.Add(b);
        }

        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            switch (cki.Key)
            {
                case ConsoleKey.LeftArrow:
                    if (currentButtonFocused < buttons.Count - 1)
                    {
                        buttons.ElementAt(currentButtonFocused).Focused = false;
                        currentButtonFocused ++;
                        buttons.ElementAt(currentButtonFocused).Focused = true;
                    }
                    draw();
                    break;
                case ConsoleKey.RightArrow:
                    if (currentButtonFocused > 0)
                    {
                        buttons.ElementAt(currentButtonFocused).Focused = false;
                        currentButtonFocused--;
                        buttons.ElementAt(currentButtonFocused).Focused = true;
                    }
                    draw();
                    break;
                case ConsoleKey.Enter:
                    buttons.ElementAt(currentButtonFocused).keypresshandler(cki);
                    break;
            }
        }
    }
}
