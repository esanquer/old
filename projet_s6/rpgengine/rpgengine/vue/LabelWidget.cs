using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class LabelWidget : Widget
    {



        private string text;

        public string Text
        {
            get { return text; }
            set { text = value; }
        }

        public LabelWidget(int x, int y, int width, int height, string text)
            : base(x, y, width, height)
        {
            this.text = text;
            this.focusable = false;
        }
        public override void draw()
        {
            base.draw();
            Console.SetCursorPosition(this.x + 1, this.y + 1);
            Console.Write(this.text);
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }
    }
}
