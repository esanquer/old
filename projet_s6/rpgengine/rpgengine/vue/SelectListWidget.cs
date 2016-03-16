using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class SelectListWidget : FormEntryWidget
    {

        private Dictionary<String, int> options;

        public Dictionary<String, int> Options
        {
            get { return options; }
            set { options = value; }
        }

        private List<bool> selectedOptions;

        public List<bool> SelectedOptions
        {
            get { return selectedOptions; }
            set { selectedOptions = value; this.buildValue(); }
        }

        private string label;

        public string Label
        {
          get { return label; }
          set { label = value; }
        }

        private int cursor;

        public int Cursor
        {
            get { return cursor; }
            set { cursor = value; }
        }



        public SelectListWidget(string label, string propertieName)
            : base(0, 0, label.Length+3, 2, propertieName)
        {
            this.label = label;
            this.options = new Dictionary<string, int>();
            this.selectedOptions = new List<bool>();
        }

        public void addOption(string name, int val)
        {
            this.options.Add(name, val);
            this.selectedOptions.Add(false);

            this.height++;
            if (this.label.Length + name.Length + 2 > this.width)
                this.width = this.label.Length + name.Length + 3;
        }

        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            if (cki.Key == ConsoleKey.DownArrow && cursor < options.Count - 1)
            {
                this.cursor++;
                this.draw();
            }
            if (cki.Key == ConsoleKey.UpArrow && cursor > 0)
            {
                this.cursor--;
                this.draw();
            }
            if (cki.Key == ConsoleKey.Enter)
            {
                selectedOptions[cursor] = !selectedOptions[cursor];
                buildValue();
            }
            if (cki.Key == ConsoleKey.Backspace)
            {
                parent.childFinished(this);
            }
        }


        public void buildValue()
        {
            this.value = "";
            for (int i = 0; i < options.Count; i++)
            {
                if (selectedOptions[i])
                {
                    if (value.Length > 0)
                    {
                        value += ".";
                    }
                    value += options.ElementAt(i).Value;
                }
            }
        }

        public override void draw()
        {
            base.draw();
            int ligne = this.y + 1;
            Console.SetCursorPosition(this.x + 1, this.y + 1);
            if (focused)
                Console.ForegroundColor = ConsoleColor.Cyan;
            else
                Console.ForegroundColor = ConsoleColor.Gray;
            Console.Write(this.label+":");

            for (int i = 0; i < options.Count; i++)
            {
                Console.SetCursorPosition(this.x+this.label.Length + 3, ligne);
                if (selectedOptions[i])
                {
                    Console.BackgroundColor = ConsoleColor.Gray;
                    Console.ForegroundColor = ConsoleColor.Black;
                }
                if (cursor == i && this.focused)
                {
                    Console.BackgroundColor = ConsoleColor.Cyan;
                    Console.ForegroundColor = ConsoleColor.Black;
                }
                Console.Write(options.ElementAt(i).Key);
                Console.BackgroundColor = ConsoleColor.Black;
                Console.ForegroundColor = ConsoleColor.Gray;
                ligne++;
            }
        }
    }
}
