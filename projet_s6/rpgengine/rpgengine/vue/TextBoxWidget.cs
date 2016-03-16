using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class TextBoxWidget : FormEntryWidget
    {
        
        private string label;


        public string Label
        {
            get { return label; }
            set { label = value; }
        }

        private int length;

        public int Length
        {
            get { return length; }
            set { length = value; }
        }

        public TextBoxWidget(int length, string label, string propertieName) : this(0, 0, length, label, propertieName) { }

        public TextBoxWidget(int x, int y, int length, string label, string propertieName) : base(x, y, length+label.Length+4, 2, propertieName) 
        {
            this.length = length;
            this.Border = '.';
            this.value = "";
            this.label = label;
            this.Focusable = true;
        }

        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            if (cki.Key != ConsoleKey.Enter && cki.Key != ConsoleKey.Backspace && this.value.Length < this.length)
            {
                this.value += cki.KeyChar;
            }
            if (cki.Key == ConsoleKey.Backspace)
            {
                if (this.value.Length > 0) 
                    this.value = value.Substring(0, value.Length - 1);
            }
            if (cki.Key == ConsoleKey.Enter)
            {
                parent.childFinished(this);
            }
            draw();
        }
        private void drawBackground()
        {
            base.draw();
            Console.BackgroundColor = ConsoleColor.Black;
            if (focused)
                Console.ForegroundColor = ConsoleColor.Cyan;
            else
                Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(this.x + 1, this.y + 1);
            Console.Write("{0}:", this.label);
            Console.BackgroundColor = ConsoleColor.White;
            Console.ForegroundColor = ConsoleColor.Black;
            for (int i = 0; i < this.Length; i++)
            {
                Console.Write(" ");
            }
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }

        public override void draw()
        {
            drawBackground();
            Console.BackgroundColor = ConsoleColor.White;
            Console.ForegroundColor = ConsoleColor.Black;
            Console.SetCursorPosition(this.x + 1+this.label.Length+1, this.y + 1);
            Console.Write(this.value);
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }

    }
}
