using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;

namespace rpgengine.vue
{
    class ConsoleKeyListener
    {

        DelegateMethods.getObject getVue;

        internal DelegateMethods.getObject GetVue
        {
            get { return getVue; }
            set { getVue = value; }
        }

        private Thread listner;

        public Thread Listner
        {
            get { return listner; }
            set { listner = value; }
        }

        public ConsoleKeyListener(DelegateMethods.getObject gv)
        {
            this.getVue = gv;
        }

        public void start() 
        {
            listner = new Thread(new ThreadStart(this.run));
            listner.Start();
        }
        public void run()
        {
            while (true)
            {
                int posxcursor = Console.CursorLeft;
                int posycursor = Console.CursorTop;
                ConsoleKeyInfo cki = Console.ReadKey(false);
                Console.SetCursorPosition(posxcursor, posycursor);
                VueConsole vc = this.getVue() as VueConsole;
                if (vc != null)
                {
                    vc.handlePressedKey(cki);
                }
            }
        }
    }
}
