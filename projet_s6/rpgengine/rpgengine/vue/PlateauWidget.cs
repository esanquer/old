using rpgengine.univers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    enum Orientation
    {
        North=0,East,South,West
    }
    class PlateauWidget : Widget
    {

        protected List<SituationWidget> situations;

        public List<SituationWidget> Situations
        {
            get { return situations; }
            set { situations = value; }
        }


        private DelegateMethods.ActionForward onPlayerMove;

        internal DelegateMethods.ActionForward OnPlayerMove
        {
            get { return onPlayerMove; }
            set { onPlayerMove = value; }
        }



        protected SituationWidget currentSituation;

        public SituationWidget CurrentSituation
        {
            get { return currentSituation; }
            set { currentSituation = value; }
        }

        public PlateauWidget(int x, int y, int nbCols, int nbRows)
            : base(x, y, nbCols * 4, nbRows * 3)
        {
            this.situations = new List<SituationWidget>();
        }

        public void goToNeighboor(Orientation o)
        {
            SituationWidget neighboor = currentSituation.getNeighboor(o);
            if (neighboor != null)
            {
                currentSituation.Focused = false;
                InternalPosition position = currentSituation.PlayerPosition;
                currentSituation = neighboor;
                currentSituation.Focused = true;
                currentSituation.Entrance((Orientation)(((int)o + 2) % 4), position);
                currentSituation.draw();
            }
        }

        public void addSituation(SituationWidget s)
        {
            int x = s.X;
            int y = s.Y;
            SituationWidget neighboorWest = findSituationByPosition(x - 1, y);
            SituationWidget neighboorNorth = findSituationByPosition(x, y-1);
            SituationWidget neighboorEast = findSituationByPosition(x +s.Width, y);
            SituationWidget neighboorSouth = findSituationByPosition(x, y+s.Height);

            if (neighboorEast != null)
            {
                s.EastNeighboor = neighboorEast;
                neighboorEast.WestNeighboor = s;
            }
            if (neighboorNorth != null)
            {
                s.NorthNeighboor = neighboorNorth;
                neighboorNorth.SouthNeighboor = s;
            }
            if (neighboorSouth != null)
            {
                s.SouthNeighboor = neighboorSouth;
                neighboorSouth.NorthNeighboor = s;
            }
            if (neighboorWest != null)
            {
                s.WestNeighboor = neighboorWest;
                neighboorWest.EastNeighboor = s;
            }
            s.Plateau = this;
            this.situations.Add(s);
        }

        public SituationWidget findSituationByPosition(int posx, int posy)
        {
            int i = 0;
            SituationWidget result = null;
            if (posx > this.x && posy > this.y && posx < this.x + this.width && posy < this.y + this.height)
            {
                while (result==null && i < situations.Count)
                {
                    SituationWidget cur=situations.ElementAt(i);
                    if (posx >= cur.X && posx <= cur.X + cur.Width-1 && posy >= cur.Y && posy <= cur.Y + cur.Height-1)
                    {
                        result = cur;
                    }
                    i++;
                }
            }
            return result;
        }


        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            this.currentSituation.keypresshandler(cki);

            if (onPlayerMove != null)
            {
                List<Object> parameters = new List<Object>();
                parameters.Add(currentSituation);
                onPlayerMove(parameters);
            }
        }

        public void drawBackground()
        {
            int posx = this.x + 1;
            int posy = this.y + 1;
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(posx, posy);

            while (posy < this.y + this.height)
            {
                Console.SetCursorPosition(posx, posy);
                string s = new string(' ', this.width - 2);
                Console.Write(s);
                posy++;
            }
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
            
        }
        public override void draw()
        {
            base.draw();
            drawBackground();
            foreach (SituationWidget s in situations)
            {
                s.draw();
            }
        }

        public void loadSituations(List<Situation> situations)
        {
            foreach (Situation s in situations)
            {
                SituationVide svide = s as SituationVide;
                Combat scombat = s as Combat;
                Epreuve sepreuve = s as Epreuve;
                Dialogue sdialogue = s as Dialogue;
                int px = s.Position_x * 4 + this.X + 1;
                int py = s.Position_y * 3 + this.Y + 1;
                
                if (svide != null)
                {
                    VoidSituationWidget vs = new VoidSituationWidget(px, py);
                    vs.IdSituation = s.Id;
                    this.addSituation(vs);
                }
                else if (scombat != null)
                {
                    CombatSituationWidget cs = new CombatSituationWidget(px, py);
                    cs.IdSituation = s.Id;
                    this.addSituation(cs);
                }
                if (sepreuve != null)
                {
                    EpreuveSituationWidget es = new EpreuveSituationWidget(px, py);
                   es.IdSituation = s.Id;
                    this.addSituation(es);
                }
                if (sdialogue != null)
                {
                    DialogueSituationWidget ds = new DialogueSituationWidget(px, py);
                    ds.IdSituation = s.Id;
                    this.addSituation(ds);
                }

            }
        }

        public void DefineCurrentSituationByCoord(int col, int line)
        {
            int x = col * 4 + this.x+1;
            int y = line * 3 + this.y+1;
            this.currentSituation = findSituationByPosition(x, y);
            this.currentSituation.Focused = true;
        }
        public void PlacePlayerInCurrentSituation(int x, int y)
        {
            this.currentSituation.setPlayerPosition(x, y);
        }


    }
}
