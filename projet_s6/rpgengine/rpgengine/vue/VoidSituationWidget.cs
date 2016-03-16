using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class VoidSituationWidget : SituationWidget
    {

        public VoidSituationWidget(int x, int y)
            : base(x, y)
        {
            this.typeDescriptor = "Situation vide";
        }

        
    }
}
