using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class ButtonWidget : Widget
    {




        private DelegateMethods.ActionHandlerMethod actionHandler;

        public DelegateMethods.ActionHandlerMethod ActionHandler
        {
            get { return actionHandler; }
            set { actionHandler = value; }
        }
        private List<Object> actionHandlerParams;

        public List<Object> ActionHandlerParams
        {
            get { return actionHandlerParams; }
            set { actionHandlerParams = value; }
        }


        private string text;

        public string Text
        {
            get { return text; }
            set { text = value; }
        }


        public ButtonWidget(int x, int y, int width, string text)
            : base(x, y, width, 2)
        {
            this.text = text;
            this.Focusable = true;
            this.actionHandlerParams = new List<Object>();
            this.border = ' ';
        }


        private void drawBackground()
        {
            base.draw();
            Console.SetCursorPosition(this.x + 1, this.y + 1);
            if (focused)
            {
                Console.BackgroundColor = ConsoleColor.Cyan;
            }
            else{
                Console.BackgroundColor = ConsoleColor.Black;
            }
            for (int i = 0; i < this.width-1; i++)
            {
                Console.Write(" ");
            }

            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }
        private void drawForeground()
        {
            int writingLength = this.width - 2;
            int center = (writingLength / 2);
            int offsetCenter = center - (this.text.Length / 2);

            Console.SetCursorPosition(this.x + 1 + offsetCenter, this.y + 1);
            if (focused)
            {
                Console.ForegroundColor = ConsoleColor.Black;
                Console.BackgroundColor = ConsoleColor.Cyan;
            }
            Console.Write(this.text);
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

        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            if (cki.Key == ConsoleKey.Enter)
            {
                this.ActionHandler(actionHandlerParams);
            }
        }
    }
}
