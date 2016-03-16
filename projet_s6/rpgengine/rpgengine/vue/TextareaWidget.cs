using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class TextareaWidget : FormEntryWidget
    {
        private int length;

        public int Length
        {
            get { return length; }
            set { length = value; }
        }

        private string label;

        public string Label
        {
            get { return label; }
            set { label = value; }
        }

        private int cols;

        public int Cols
        {
            get { return cols; }
            set { cols = value; }
        }

        private int rows;

        public int Rows
        {
            get { return rows; }
            set { rows = value; }
        }

        public TextareaWidget(int length, string label, int cols, int rows, string propertieName) : this(0, 0, length, label, cols, rows, propertieName) { }

        public TextareaWidget(int x, int y, int length, string label, int cols, int rows, string propertieName)
            : base(x, y, cols + label.Length + 3, rows + 1, propertieName) 
        {
            this.length = length;
            this.Border = '.';
            this.value = "";
            this.label = label;
            this.rows = rows;
            this.cols = cols;
            this.Focusable = true;
        }


        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            if (cki.Key != ConsoleKey.Enter && cki.Key!=ConsoleKey.Backspace && this.value.Length < this.length)
            {
                this.value += cki.KeyChar;
                drawForeground();
            }
            if (cki.Key == ConsoleKey.Backspace && this.value.Length>0)
            {
                this.value = value.Substring(0, value.Length - 1);
                drawBackground();
                drawForeground();
            }
            if (cki.Key == ConsoleKey.Enter)
            {
                parent.childFinished(this);
            }
            
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
            int offsetTop = this.y + 1;
            int offsetLeft = this.x + 1 + this.label.Length + 1;
            Console.SetCursorPosition(offsetLeft, offsetTop);
            for (int i = 0; i < this.cols * this.rows; i++)
            {
                if (i % this.cols == 0 && i != 0)
                {
                    offsetTop++;
                    Console.SetCursorPosition(offsetLeft, offsetTop);
                }
                Console.Write(" ");
            }
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }

        private void drawForeground()
        {
            int offsetTop = this.y + 1;
            int offsetLeft = this.x + 1 + this.label.Length + 1;
            Console.SetCursorPosition(offsetLeft, offsetTop);
            if (focused)
            {
                Console.BackgroundColor = ConsoleColor.White;
                Console.ForegroundColor = ConsoleColor.Black;
            }
            for (int i = 0; i < this.value.Length; i++)
            {
                if (i % this.cols == 0 && i != 0)
                {
                    offsetTop++;
                    Console.SetCursorPosition(offsetLeft, offsetTop);
                }
                Console.Write(value[i]);
            }
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }

        public override void draw()
        {
            base.draw();
            drawBackground();
            drawForeground();
        }
    }
}
