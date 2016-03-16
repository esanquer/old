using rpgengine.univers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class PlateauCreatorWidget : Widget
    {

        private DelegateMethods.ActionHandlerMethod createSituationAction;

        public DelegateMethods.ActionHandlerMethod CreateSituationAction
        {
            get { return createSituationAction; }
            set { createSituationAction = value; }
        }



        private PlateauWidget plateau;

        internal PlateauWidget Plateau
        {
            get { return plateau; }
            set { plateau = value; }
        }

        private SituationWidget currentSituation;

        private LabelWidget title;

        private bool typeSituationSelection;

        private int selectedTypeSituation;

        private List<SituationWidget> typesSituations;
        private List<LabelWidget> labels;
        public PlateauCreatorWidget(int x, int y, int cols, int rows): base(x,y,cols*4+20,rows*3+5)
        {
            this.title = new LabelWidget(x+1, y, 30,1,"Ecriture de l'histoire");

            this.plateau = new PlateauWidget(x+1, y+4, cols,rows );
            this.currentSituation = new SituationWidget(x+2, y+5);
            this.currentSituation.IsAccessible=true;
            this.selectedTypeSituation = 0;

            this.typeSituationSelection = false;

            typesSituations = new List<SituationWidget>();
            this.labels = new List<LabelWidget>();
          
            typesSituations.Add(new VoidSituationWidget(this.x + this.width - 15,this.y+4));
            LabelWidget labVoid = new LabelWidget(this.x + this.width - 10, this.y + 4, 9, 2, "Vide");
            labVoid.HasBorder = false;
            labels.Add(labVoid);
            typesSituations.Add(new CombatSituationWidget(this.x + this.width - 15, this.y + 8));
            LabelWidget labCombat = new LabelWidget(this.x + this.width - 10, this.y + 8, 9, 2, "Combat");
            labCombat.HasBorder = false;
            labels.Add(labCombat);
            typesSituations.Add(new DialogueSituationWidget(this.x + this.width - 15, this.y + 12));
            LabelWidget labDialogue = new LabelWidget(this.x + this.width - 10, this.y +12, 9, 2, "Dialogue");
            labDialogue.HasBorder = false;
            labels.Add(labDialogue);
            typesSituations.Add(new EpreuveSituationWidget(this.x + this.width - 15, this.y + 16));
            LabelWidget labEpreuve= new LabelWidget(this.x + this.width - 10, this.y + 16, 9, 2, "Epreuve");
            labEpreuve.HasBorder = false;
            labels.Add(labEpreuve);
            foreach(SituationWidget s in typesSituations)
            {
                s.IsAccessible=true;
            }
            this.focusable = true;
        }


        public override void keypresshandler(ConsoleKeyInfo cki)
        {
            if (typeSituationSelection)
            {
                switch (cki.Key)
                {
                    case ConsoleKey.UpArrow:
                        typesSituations[selectedTypeSituation].Focused = false;
                        typesSituations[selectedTypeSituation].draw();
                        selectedTypeSituation = (selectedTypeSituation > 0) ? selectedTypeSituation - 1 : selectedTypeSituation;
                        typesSituations[selectedTypeSituation].Focused = true;
                        typesSituations[selectedTypeSituation].draw();
                        draw();
                        break;
                    case ConsoleKey.DownArrow:
                        typesSituations[selectedTypeSituation].Focused = false;
                        typesSituations[selectedTypeSituation].draw();
                        selectedTypeSituation = (selectedTypeSituation < typesSituations.Count-1) ? selectedTypeSituation + 1 : selectedTypeSituation;
                        typesSituations[selectedTypeSituation].Focused = true;
                        typesSituations[selectedTypeSituation].draw();
                        draw();
                        break;
                    case ConsoleKey.Enter:
                        List<Object> p = new List<Object>();
                        p.Add(typesSituations[selectedTypeSituation]);
                        p.Add((currentSituation.X-plateau.X-1)/currentSituation.Width);
                        p.Add((currentSituation.Y - plateau.Y-1) / currentSituation.Height);
                        createSituationAction(p);
                        break;
                }
            }
            else
            {
                switch (cki.Key)
                {
                    case ConsoleKey.RightArrow:
                        if (currentSituation.X + currentSituation.Width < plateau.X + plateau.Width) currentSituation.X += currentSituation.Width;
                        break;
                    case ConsoleKey.LeftArrow:
                        if (currentSituation.X > plateau.X + currentSituation.Width) currentSituation.X -= currentSituation.Width;
                        break;
                    case ConsoleKey.UpArrow:
                        if (currentSituation.Y > plateau.Y + currentSituation.Height) currentSituation.Y -= currentSituation.Height;
                        break;
                    case ConsoleKey.DownArrow:
                        if (currentSituation.Y + currentSituation.Height < plateau.Y + plateau.Height) currentSituation.Y += currentSituation.Height;
                        break;
                    case ConsoleKey.Enter:
                        if (plateau.findSituationByPosition(currentSituation.X, currentSituation.Y) == null)
                        {
                            typeSituationSelection = true;
                            typesSituations[selectedTypeSituation].Focused = true;
                        }
                        break;
                }
                this.plateau.draw();
                currentSituation.draw();
            }
           
        }

        private void validateCurrentSituation()
        {
            plateau.addSituation(currentSituation);
            currentSituation = new SituationWidget(plateau.X+1, plateau.Y+1);
            currentSituation.IsAccessible = true;
        }


        public override void draw()
        {
            base.draw();
            this.title.draw();
            this.plateau.draw();
            this.currentSituation.draw();
            foreach (SituationWidget s in typesSituations)
            {
                s.draw();
            }
            foreach(LabelWidget l in labels)
            {
                l.draw();
            }
        }

        

        
        
    }
}
