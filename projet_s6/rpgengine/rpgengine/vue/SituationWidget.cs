using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{


    public struct InternalPosition
    {
        public int x;
        public int y;
        public InternalPosition(int _x, int _y)
        {
            x = _x;
            y = _y;
        }
    }

    class SituationWidget : Widget
    {


        protected string typeDescriptor;

        public string TypeDescriptor
        {
            get { return typeDescriptor; }
            set { typeDescriptor = value; }
        }

        private int idSituation;

        public int IdSituation
        {
            get { return idSituation; }
            set { idSituation = value; }
        }

        protected PlateauWidget plateau;

        public PlateauWidget Plateau
        {
            get { return plateau; }
            set { plateau = value; }
        }
        protected InternalPosition playerPosition;

        public InternalPosition PlayerPosition
        {
            get { return playerPosition; }
            set { playerPosition = value; }
        }

        protected SituationWidget northNeighboor;

        public SituationWidget NorthNeighboor
        {
            get { return northNeighboor; }
            set { northNeighboor = value; }
        }
        protected SituationWidget southNeighboor;

        public SituationWidget SouthNeighboor
        {
            get { return southNeighboor; }
            set { southNeighboor = value; }
        }
        protected SituationWidget eastNeighboor;

        public SituationWidget EastNeighboor
        {
            get { return eastNeighboor; }
            set { eastNeighboor = value; }
        }
        protected SituationWidget westNeighboor;

        public SituationWidget WestNeighboor
        {
            get { return westNeighboor; }
            set { westNeighboor = value; }
        }

        protected bool isAccessible;

        public bool IsAccessible
        {
            get { return isAccessible; }
            set { isAccessible = value; }
        }

        public SituationWidget(int x, int y): base(x,y,4,3)
        {
            this.HasBorder = false;
            this.playerPosition = new InternalPosition(0, 0);
        }

        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            switch (cki.Key)
            {
                case ConsoleKey.RightArrow:
                    if (playerPosition.x < 3)
                    {
                        playerPosition.x++;
                        draw();
                    }
                    else
                    {
                        plateau.goToNeighboor(Orientation.East);
                    }
                    break;
                case ConsoleKey.LeftArrow:
                    if (playerPosition.x > 0)
                    {
                        playerPosition.x--;
                        draw();
                    }
                    else
                    {
                        plateau.goToNeighboor(Orientation.West);
                    }
                    break;
                case ConsoleKey.UpArrow:
                    if (playerPosition.y > 0)
                    {
                        playerPosition.y--;
                        draw();
                    }
                    else
                    {
                        plateau.goToNeighboor(Orientation.North);
                    }
                    break;
                case ConsoleKey.DownArrow:
                    if (playerPosition.y < 2)
                    {
                        playerPosition.y++;
                        draw();
                    }
                    else
                    {
                        plateau.goToNeighboor(Orientation.South);
                    }
                    break;
            } 
        }

        public virtual void Entrance(Orientation o, InternalPosition position){
            switch (o)
            {
                case Orientation.East:
                    playerPosition = new InternalPosition(3, position.y);
                    break;
                case Orientation.North:
                    playerPosition = new InternalPosition(position.x, 0);
                    break;
                case Orientation.West:
                    playerPosition = new InternalPosition(0, position.y);
                    break;
                case Orientation.South:
                    playerPosition = new InternalPosition(position.x, 2);
                    break;
            }
        }
        public virtual SituationWidget getNeighboor(Orientation o)
        {
            SituationWidget s = null;
            switch (o)
            {
                case Orientation.East:
                    s= eastNeighboor;
                    break;
                case Orientation.North:
                    s= northNeighboor;
                    break;
                case Orientation.West:
                    s = westNeighboor;
                    break;
                case Orientation.South:
                    s = southNeighboor;
                    break;
            }
            return s;
        }

        private void drawBackground()
        {
            if (focused)
            {
                Console.BackgroundColor = ConsoleColor.Gray;
            }
            else
            {
                Console.BackgroundColor = ConsoleColor.DarkGray;
            }
            Console.SetCursorPosition(this.x, this.y);
            Console.Write("    ");
            Console.SetCursorPosition(this.x, this.y + 1);
            Console.Write("    ");
            Console.SetCursorPosition(this.x, this.y + 2);
            Console.Write("    ");
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }

        public override void draw()
        {
            drawBackground();
            if (this.focused)
            {
                Console.SetCursorPosition(this.x + this.playerPosition.x, this.y + this.playerPosition.y);
                Console.ForegroundColor = ConsoleColor.DarkCyan;
                Console.BackgroundColor = ConsoleColor.Gray;
                Console.Write('\u263a');
            }
            Console.BackgroundColor = ConsoleColor.Black;
            Console.ForegroundColor = ConsoleColor.Gray;
            Console.SetCursorPosition(0, Console.BufferHeight - 1);
        }


        public void setPlayerPosition(int x, int y)
        {
            this.playerPosition.x = x;
            this.playerPosition.y = y;
        }

        public bool IsPLayerCentered()
        {
            return this.playerPosition.x >= 1 && this.playerPosition.x <= 2 && this.playerPosition.y == 1;
        }



    }
}
