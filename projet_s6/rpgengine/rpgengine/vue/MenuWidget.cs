using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
namespace rpgengine.vue
{
    class MenuWidget : Widget
    {

        private int choix;
        private Dictionary<string, Dictionary<DelegateMethods.ActionHandlerMethod, List<Object>>> options;


        private bool isCenteredText;

        public bool IsCenteredText
        {
            get { return isCenteredText; }
            set { isCenteredText = value; }
        }
        
        public MenuWidget(int x, int y, int width, int height) : base(x,y,width,height)
        {
            this.options = new Dictionary<string, Dictionary<DelegateMethods.ActionHandlerMethod,List<Object>>>();
            this.Border = '/';
            this.choix = 0;
            this.focusable = true;
            isCenteredText = true;
        }

        public void addOption(string text, DelegateMethods.ActionHandlerMethod choicehandler, List<Object> parameters)
        {
            Dictionary<DelegateMethods.ActionHandlerMethod, List<Object>> corresp = new Dictionary<DelegateMethods.ActionHandlerMethod, List<Object>>();
            corresp.Add(choicehandler, parameters);
            this.options.Add(text, corresp);
        }

        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            if (cki.Key == ConsoleKey.DownArrow && choix < options.Count - 1)
            {
                this.choix++;
                this.draw();
            }
            if (cki.Key == ConsoleKey.UpArrow && choix > 0)
            {
                this.choix--;
                this.draw();
            }
            
            if (cki.Key == ConsoleKey.Enter)
            {
                if (options.Count > 0)
                {
                    Dictionary<DelegateMethods.ActionHandlerMethod, List<Object>> correspMethodHandler = options.ElementAt(choix).Value;
                    correspMethodHandler.ElementAt(0).Key(correspMethodHandler.ElementAt(0).Value);
                }
             }
        }


        public override void draw()
        {
           base.draw();
           Console.SetCursorPosition(this.x + 1, this.y + 1);
           for(int i = 0 ; i < options.Count;i++)
           {
               int offsetCenter = 1;
               if (this.isCenteredText)
               {
                   int writingLength = this.width - 2;
                   int center = (writingLength / 2);
                   offsetCenter = center - (this.options.ElementAt(i).Key.Length / 2);
               }

               Console.SetCursorPosition(this.x + 1 + offsetCenter, this.y + i + 1);
                if(this.choix==i){
                    if (this.focused)
                    {
                        Console.BackgroundColor = ConsoleColor.Cyan;
                        Console.ForegroundColor = ConsoleColor.Black;
                    }
                    else
                    {
                        Console.BackgroundColor = ConsoleColor.Gray;
                        Console.ForegroundColor = ConsoleColor.Black;
                    }
                }


                string aAfficher = options.ElementAt(i).Key;
                if(aAfficher.Length>this.width-2){
                    aAfficher=aAfficher.Substring(0,this.width-3)+"..";
                }
               Console.Write(aAfficher);
               Console.BackgroundColor=ConsoleColor.Black;
               Console.ForegroundColor = ConsoleColor.Gray;
           }

           Console.BackgroundColor = ConsoleColor.Black;
           Console.ForegroundColor = ConsoleColor.Gray;
           Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }
    }
}
