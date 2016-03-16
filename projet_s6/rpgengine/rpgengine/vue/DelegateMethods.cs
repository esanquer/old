using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace rpgengine.vue
{
    class DelegateMethods
    {

        public delegate void voidMethod();
        public delegate Object getObject();
        public delegate void ActionHandlerMethod(List<Object> parameters);
        public delegate void ActionForward(List<Object> parameters);
        public delegate void FormUser(Dictionary<string, string> datas, ActionForward forward);
 

    }
}
