using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace rpgengine.vue
{
    class Widget
    {
        private bool hasBorder;

        public bool HasBorder
        {
            get { return hasBorder; }
            set { hasBorder = value; }
        }

        protected bool focusable;

        public bool Focusable
        {
            get { return focusable; }
            set { focusable = value; }
        }

        protected bool focused;
        public bool Focused
        {
            get { return focused; }
            set { focused = value;
            draw();
            }
        }

        protected char border;
        public char Border
        {
            get { return border; }
            set { border = value; }
        }

        protected int x;
        public int X
        {
            get { return x; }
            set { x = value; }
        }

        protected int y;
        public int Y
        {
            get { return y; }
            set { y = value; }
        }
        protected int width;

        public int Width
        {
            get { return width; }
            set { width = value; }
        }

        protected int height;
        public int Height
        {
            get { return height; }
            set { height = value; }
        }

        public Widget()
        {
            this.border ='#';
        }

        public Widget(int x, int y, int width, int height) : this() 
        {
            this.x = x;
            this.y = y;
            this.height = height;
            this.width = width;
            this.hasBorder = true;
        }
        public virtual void draw()
        {
            if (hasBorder)
            {
                if (focused)
                {

                    Console.ForegroundColor = ConsoleColor.Cyan;
                }
                Console.SetCursorPosition(this.x, this.y);
                for (int h_i = 0; h_i <= this.height; h_i++)
                {
                    for (int w_i = 0; w_i <= this.width; w_i++)
                    {

                        if (h_i % this.height == 0 || w_i % this.width == 0)
                        {
                            Console.SetCursorPosition(x + w_i, y + h_i);
                            Console.Write(this.border);
                        }
                    }
                }
                Console.BackgroundColor = ConsoleColor.Black;
                Console.ForegroundColor = ConsoleColor.Gray;
                Console.SetCursorPosition(0, Console.BufferHeight-1);
            }
        }

        public virtual void keypresshandler(ConsoleKeyInfo cki)
        {
        }
    }
}
