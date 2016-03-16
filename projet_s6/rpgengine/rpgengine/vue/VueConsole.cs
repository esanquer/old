using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace rpgengine.vue
{
    class VueConsole
    {

        public bool historizable;
        private bool keyprocessing;
        private List<Widget> widgets;

        internal List<Widget> Widgets
        {
            get { return widgets; }
            set { widgets = value; }
        }
        private int cursorElementFocused = 0;


        public void addWidget(Widget w)
        {
            widgets.Add(w);
            if (w.Y + w.Height > Console.BufferHeight)
            {
                Console.BufferHeight=w.Y + w.Height +1;
                Console.WindowHeight =58;
            }
        }

        public VueConsole()
        {
            Console.SetBufferSize(130, 51);
            Console.SetWindowSize(130, 51);
            widgets = new List<Widget>();
            keyprocessing = false;
            historizable = true;

        }

        public void draw()
        {
            Console.Clear();
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            foreach (Widget w in widgets)
            {
                w.draw();
            }
        }

        public void handlePressedKey(ConsoleKeyInfo cki)
        {
            if (!keyprocessing)
            {
                keyprocessing = true;
                if (widgets.Count > 0)
                {
                    switch (cki.Key)
                    {
                        case ConsoleKey.Tab:
                            doTab(cki);
                            break;
                        case ConsoleKey.Escape:
                            break;
                        default:
                            widgets.ElementAt(cursorElementFocused).keypresshandler(cki);
                            break;
                    }
                }
                keyprocessing = false;
            }
        }


        private void doTab(ConsoleKeyInfo cki)
        {
            FormWidget form = widgets.ElementAt(cursorElementFocused) as FormWidget;
            if (form != null)
            {
                if (form.insideMovePossible(cki))
                {
                    widgets.ElementAt(cursorElementFocused).keypresshandler(cki);
                }
                else
                {
                    widgets.ElementAt(cursorElementFocused).Focused = false;
                    do
                    {
                        cursorElementFocused = ((cki.Modifiers & ConsoleModifiers.Shift) != 0 && cursorElementFocused > 0) ? (cursorElementFocused - 1) % widgets.Count : (cursorElementFocused + 1) % widgets.Count;
                    } while (widgets.ElementAt(cursorElementFocused).Focusable == false);
                    widgets.ElementAt(cursorElementFocused).Focused = true;
                }
            }
            else
            {
                widgets.ElementAt(cursorElementFocused).Focused = false;
                do
                {
                    cursorElementFocused = ((cki.Modifiers & ConsoleModifiers.Shift) != 0 && cursorElementFocused>0) ? (cursorElementFocused - 1) % widgets.Count : (cursorElementFocused + 1) % widgets.Count;
                } while (widgets.ElementAt(cursorElementFocused).Focusable == false);
                widgets.ElementAt(cursorElementFocused).Focused = true;
            }
        }
        public bool setReady()
        {
            bool focusSetted = false;
            int i = 0;
            while (!focusSetted && i < widgets.Count)
            {
                Widget w = widgets[i];
                Popup p = widgets.ElementAt(cursorElementFocused) as Popup;
                if (p != null)
                {
                    focusSetted = true;
                    p.Focused = true;
                    cursorElementFocused = i;
                }
                else if (w.Focusable)
                {
                     
                     FormWidget form = widgets.ElementAt(cursorElementFocused) as FormWidget;
                     if (form != null)
                     {
                         focusSetted = form.focusFirst();
                         if (focusSetted)
                         {
                             form.Focused = true;
                             cursorElementFocused = i;
                         }
                     }
                     else
                     {
                         focusSetted=true;
                         w.Focused = true;
                         cursorElementFocused = i;
                     }
                }
                i++;
            }
            return focusSetted;
        }
        public void reset()
        {
            widgets = new List<Widget>();
            cursorElementFocused = 0;
        }
    }
}
