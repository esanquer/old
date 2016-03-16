using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class SliderWidget : FormEntryWidget
    {
        private int maxValue;

        public int MaxValue
        {
            get { return maxValue; }
            set { maxValue = value; }
        }

        private int minValue;

        public int MinValue
        {
            get { return minValue; }
            set { minValue = value; }
        }

        private int step;

        public int Step
        {
            get { return step; }
            set { step = value; }
        }

        private string label;

        public string Label
        {
            get { return label; }
            set { label = value; }
        }

        public SliderWidget(int x, int y, int width, string propertieName, string label, int minValue, int maxValue) : base(x,y,width,2,propertieName) {
            this.label = label;
            this.value = minValue.ToString();
            this.minValue = minValue;
            this.maxValue = maxValue;
            this.step = 1;
            this.HasBorder = false;
        }


        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            int val = int.Parse(this.value);
            if (cki.Key == ConsoleKey.RightArrow && val<this.maxValue)
            {
                val+=this.step;
                this.value=val.ToString();
            }
            if (cki.Key == ConsoleKey.LeftArrow && val > this.minValue)
            {
                val -= this.step;
                this.value = val.ToString();
            }
            if (cki.Key == ConsoleKey.Enter)
            {
                parent.childFinished(this);
            }
            drawForeground();
        }


        private void drawBackground()
        {
            base.draw();
            Console.SetCursorPosition(this.x + 1, this.y + 1);
            if (focused)
                Console.ForegroundColor = ConsoleColor.Cyan;
            else
                Console.ForegroundColor = ConsoleColor.Gray;
            Console.Write("{0}:", this.label);
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }

        private void drawForeground()
        {
            int slideWidth = this.width - this.label.Length - 3 - this.value.Length;
            int cursorPosition = (int)((double.Parse(this.value) / this.maxValue) * slideWidth) ;

            Console.SetCursorPosition(this.x + 1 + this.label.Length, this.y + 1);
            if (this.focused)
            {
                Console.BackgroundColor = ConsoleColor.Black;
                Console.ForegroundColor = ConsoleColor.Cyan;
            }
            else
            {
                Console.BackgroundColor = ConsoleColor.Black;
                Console.ForegroundColor = ConsoleColor.Gray;
            }
            Console.Write("[");
            for (int i = 0; i < slideWidth; i++)
            {
                if (i < cursorPosition)
                {
                    Console.Write("█");
                }
                else
                {
                    Console.Write(" ");
                }
            }
            Console.Write("]");
            Console.Write(this.value);
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }

        public override void draw()
        {
            drawBackground();
            drawForeground();
        }
    }
}
