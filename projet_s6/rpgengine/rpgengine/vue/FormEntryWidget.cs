using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class FormEntryWidget : Widget
    {

        protected FormWidget parent;

        public FormWidget Parent
        {
            get { return parent; }
            set { parent = value; }
        }

        protected string propertieName;

        public string PropertieName
        {
            get { return propertieName; }
            set { propertieName = value; }
        }

        protected string value;

        public string Value
        {
            get { return this.value; }
            set { this.value = value; }
        }

        public FormEntryWidget(int x, int y, int width, int height, string propertieName)
            : base(x, y, width, height)
        {
            this.propertieName = propertieName;
            this.focusable = true;
            this.HasBorder = false;
        }
    }
}
