using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class FormWidget : Widget
    {


        private List<Widget> children;
        private int curendChildFocused;
        private Dictionary<string, string> datas;

        public Dictionary<string, string> Datas
        {
            get { return datas; }
            set { datas = value; }
        }

        public delegate void ActionForward();

        public delegate void FormUser(Dictionary<string, string> datas, ActionForward forward);
        

        private DelegateMethods.FormUser formUserMethod;

        public DelegateMethods.FormUser FormUserMethod
        {
            get { return formUserMethod; }
            set { formUserMethod = value; }
        }

        private DelegateMethods.ActionForward forwardAction;

        internal DelegateMethods.ActionForward ForwardAction
        {
            get { return forwardAction; }
            set { forwardAction = value;}
        }

        private string title;
        public string Title
        {
            get { return title; }
            set { title = value; }
        }

        public FormWidget(int x, int y, string title)
            : base(x, y, title.Length+2, 3)
        {
            this.border = '#';
            this.title = title;
            children = new List<Widget>();
            this.datas = new Dictionary<string,string>();
            this.Focusable = true;
        }

        public void addWidget(Widget w)
        {
            w.X = this.x + 1;
            if (children.Count > 0)
                w.Y = children.Last().Y + children.Last().Height;
            else
                w.Y = this.y + 1;
            children.Add(w);
            
            
            this.height += w.Height;
            if (w.Width + 2 > this.width)
            {
                this.width = w.Width + 2;
            }
            FormEntryWidget fw = w as FormEntryWidget;
            if (fw != null)
            {
                fw.Parent = this;
            }
        }

        public override void draw()
        {
            base.draw();
            Console.SetCursorPosition(this.x + 1, this.y);
            Console.Write(this.title);
            Console.SetCursorPosition(0, 50);
            foreach (Widget child in children)
            {
                child.draw();
            }
        }

        public bool insideMovePossible(ConsoleKeyInfo cki)
        {
            if (children.Count > 0)
            {
                int cursor = ((cki.Key==ConsoleKey.DownArrow)||(cki.Modifiers & ConsoleModifiers.Shift) != 0) ? (curendChildFocused - 1) : (curendChildFocused + 1);
                return cursor < children.Count && cursor >= 0 && children[cursor].Focusable;
            }
            return false;
        }

        public override void keypresshandler(ConsoleKeyInfo cki) 
        {
            if (children.Count > 0)
            {
                if (cki.Key == ConsoleKey.Tab && insideMovePossible(cki))
                {
                    children.ElementAt(curendChildFocused).Focused = false;
                    curendChildFocused = ((cki.Modifiers & ConsoleModifiers.Shift) != 0) ? (curendChildFocused - 1) : (curendChildFocused + 1);
                    children.ElementAt(curendChildFocused).Focused = true;
                    draw();
                }
                else
                {
                    children.ElementAt(curendChildFocused).keypresshandler(cki);
                }
            }
        }

        public void childFinished(Widget child)
        {
            if (curendChildFocused < children.Count-1)
            {
                children.ElementAt(curendChildFocused).Focused = false;
                curendChildFocused++;
                children.ElementAt(curendChildFocused).Focused = true;
                draw();
            }
        }

        public void endForm(List<Object>parameters)
        {
            foreach (Widget child in children)
            {
                 FormEntryWidget formElement = child as FormEntryWidget;
                 if (formElement != null)
                 {
                     this.datas.Add(formElement.PropertieName, formElement.Value);
                 }
            }
            formUserMethod(this.datas,this.forwardAction);
        }

        public bool focusFirst()
        {
            bool focusedChild = false;
            int i = 0;
            while (!focusedChild && i < children.Count)
            {
                Widget w = children[i];
                FormEntryWidget formElement = w as FormEntryWidget;
                if (formElement != null && formElement.Focusable)
                {
                    formElement.Focused = true;
                    focusedChild = true;
                }
                ButtonWidget bw = w as ButtonWidget;
                if(bw!=null && bw.Focusable)
                {
                    bw.Focused = true;
                    focusedChild = true;
                }
                i++;
                
            }
            curendChildFocused = i-1;
            return focusedChild;
        }
    }
}
