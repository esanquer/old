using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class DialogueSituationWidget : SituationWidget
    {

        public DialogueSituationWidget(int x, int y) : base(x,y)
        {
            this.typeDescriptor = "Situation de dialogue";
        }

        public override void draw()
        {
            base.draw();
            if (isAccessible)
            {
                Console.SetCursorPosition(this.X + 1, this.y + 1);
                Console.ForegroundColor = ConsoleColor.Green;
                Console.Write("██");
                Console.BackgroundColor = ConsoleColor.Black;
                Console.ForegroundColor = ConsoleColor.Gray;
                Console.SetCursorPosition(0, Console.BufferHeight - 1);
            }
        }
    }
}
